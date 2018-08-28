package com.example.rahi.schedulemanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class EmployeeInfoActivity extends AppCompatActivity implements View.OnClickListener {

    EmployeeInfoDataManager employeeInfoDataManager;

    ImageView vImageViewId;
    EditText vEmpNameId, vEmailId, vUserNameId, vPasswordId, vConfirmPassword;
    Button vSaveButtonId, vShowButtonId;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("New Employee");

        employeeInfoDataManager = new EmployeeInfoDataManager(this);

        vImageViewId = findViewById(R.id.selectEmpImage);
        vEmpNameId = findViewById(R.id.employeeName);
        vEmailId = findViewById(R.id.emailAddress);
        vUserNameId = findViewById(R.id.userName);
        vPasswordId = findViewById(R.id.passWord);
        vConfirmPassword = findViewById(R.id.confirmPassWord);

        vSaveButtonId = findViewById(R.id.saveButton);
        // vShowButtonId = findViewById(R.id.showButton);

        vImageViewId.setOnClickListener(this);
        vSaveButtonId.setOnClickListener(this);
        // vShowButtonId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String vName = vEmpNameId.getText().toString();
        String vEmail = vEmailId.getText().toString();
        String vUserName = vUserNameId.getText().toString();
        String vPass = vPasswordId.getText().toString();
        String vConPass = vConfirmPassword.getText().toString();

        if (view.getId() == R.id.selectEmpImage) {
            // After Adding Library (in app) & Permission (In Manifest) Select Image by ImageView Click
            ActivityCompat.requestPermissions(
                    EmployeeInfoActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
        } else if (view.getId() == R.id.saveButton) {
            if ((vName.equals("")) || (vEmail.equals("")) || (vUserName.equals("")) || (vPass.equals("")) || (vConPass.equals(""))) {
                Toast.makeText(getApplicationContext(), "Please Give All Data.", Toast.LENGTH_SHORT).show();
            } else {
                //For Checked Existing Username
                Boolean result = employeeInfoDataManager.findExistingUserName(vUserName);
                // check if user is not exist in db then insert
                if (result == false) {
                    // check password is match with confirm pass
                    if (vPass.trim().equals(vConPass.trim())) {

                        try {
                            employeeInfoDataManager.insertEployeeData(
                                    vEmpNameId.getText().toString().trim(),
                                    vEmailId.getText().toString().trim(),
                                    vUserNameId.getText().toString().trim(),
                                    vPasswordId.getText().toString().trim(),
                                    imageViewToByte(vImageViewId)
                            );

                            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();

                            // For Reset All
                            vImageViewId.setImageResource(R.drawable.addphoto);
                            vEmpNameId.setText("");
                            vEmailId.setText("");
                            vUserNameId.setText("");
                            vPasswordId.setText("");
                            vConfirmPassword.setText("");

                            //Explicit Intent - Open Dash Board
                            Intent intent = new Intent(this, DashBoardActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(this, "Password Not Matched", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "User Name is already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //        else if (view.getId() == R.id.showButton) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, DashBoardActivity.class);
        //            startActivity(intent);
        //        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Gallery Intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // After Adding CropImageActivity in Manifest
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)  // Enable Image Guidelines
                    .setAspectRatio(1, 1) // Image Will be Square
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                // Set Image Choosed from Gallery to ImageView
                vImageViewId.setImageURI(resultUri);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
