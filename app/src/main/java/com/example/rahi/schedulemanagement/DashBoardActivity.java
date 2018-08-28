package com.example.rahi.schedulemanagement;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.adapter.EmployeeDetailsAdapter;
import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.example.rahi.schedulemanagement.model.EmployeeDetailsModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {
    SearchView vSearchEmployee;
    Button vAddNewEmployeeBtn;
    TextView vMsgInfo;
    ListView vUserListViewId;
    ArrayList<EmployeeDetailsModel> mList;
    EmployeeDetailsAdapter mAdapter = null;

    // From employee_info_update_dialog for Update Dialog
    ImageView vImageViewId;
    EditText vEmpNameId, vEmailId, vPasswordId;
    TextView vUserNameId;
    Button vUpdateButtonId, vCancelButtonId;

    EmployeeInfoDataManager employeeInfoDataManager;

    final int REQUEST_CODE_GALLERY = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("Employee List");

        employeeInfoDataManager = new EmployeeInfoDataManager(this);
        // vSearchEmployee = findViewById(R.id.searchEmployee);
        vAddNewEmployeeBtn = findViewById(R.id.addNewEmployeeButton);
        vMsgInfo = findViewById(R.id.msgInfo);
        vUserListViewId = findViewById(R.id.userLV);

        mList = new ArrayList<>();
        mAdapter = new EmployeeDetailsAdapter(this, R.layout.employee_view, mList);
        vUserListViewId.setAdapter(mAdapter);

        //        //For Search in ListView
        //        vSearchEmployee.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        //            @Override
        //            public boolean onQueryTextSubmit(String query) {
        //                return false;
        //            }
        //
        //            @Override
        //            public boolean onQueryTextChange(String newText) {
        //                mAdapter.getFilter().filter(newText);
        //                return false;
        //            }
        //        });

        //For Load EmployeeInfoActivity

        vAddNewEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Explicit Intent
                Intent intent = new Intent(DashBoardActivity.this, EmployeeInfoActivity.class);
                startActivity(intent);
            }
        });

        //Get All Record
        Cursor cursor = employeeInfoDataManager.getEmployeeData("SELECT * FROM employeeinfo WHERE id <> '0' ORDER BY id");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            //Add to List
            mList.add(new EmployeeDetailsModel(id, name, email, username, password, image));
        }

        mAdapter.notifyDataSetChanged();

        if (mList.size() == 0) {
            vMsgInfo.setText("");
            Toast.makeText(this, "No Record Found.", Toast.LENGTH_SHORT).show();
        } else {
            vMsgInfo.setText("Employee Details Info");
        }

        //For Alert Dialog
        vUserListViewId.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                //Alert Dialog to display option of Update & Delete
                final CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(DashBoardActivity.this);
                dialog.setTitle("Choose an Option");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            //Update
                            Cursor c = employeeInfoDataManager.getEmployeeData("SELECT id FROM employeeinfo WHERE id <> '0' ORDER BY id");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }

                            // Show Update Dialog - employee_info_update_dialog
                            showUpdateDialog(DashBoardActivity.this, arrID.get(position));
                        }

                        if (i == 1) {
                            //Delete
                            Cursor c = employeeInfoDataManager.getEmployeeData("SELECT id FROM employeeinfo WHERE id <> '0' ORDER BY id");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }

                            // Show Update Dialog - employee_info_delete_dialog
                            showDeleteDialog(arrID.get(position));
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });
    }

    private void showUpdateDialog(Activity activity, final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.employee_info_update_dialog);
        dialog.setTitle("Update Employee Info");

        // From employee_info_update_dialog for Update Dialog
        vImageViewId = dialog.findViewById(R.id.editEmpImage);
        vEmpNameId = dialog.findViewById(R.id.editEmpName);
        vEmailId = dialog.findViewById(R.id.editEmail);
        vUserNameId = dialog.findViewById(R.id.editUserName);
        vPasswordId = dialog.findViewById(R.id.editPassword);

        vUpdateButtonId = dialog.findViewById(R.id.updateButton);
        vCancelButtonId = dialog.findViewById(R.id.cancelButton);

        // Get data of Row Clicked from SQLite
        Cursor cursor = employeeInfoDataManager.showIndEmployeeData(position);
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            vEmpNameId.setText(name);

            String email = cursor.getString(2);
            vEmailId.setText(email);

            String username = cursor.getString(3);
            vUserNameId.setText(username);

            String password = cursor.getString(4);
            vPasswordId.setText(password);

            byte[] image = cursor.getBlob(5);
            vImageViewId.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));

            //Add to List
            mList.add(new EmployeeDetailsModel(id, name, email, username, password, image));
        }

        // mAdapter.notifyDataSetChanged();

        //Set Width & Height of Dialog Box
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);

        dialog.getWindow().setLayout(width, height);
        dialog.show();

        vImageViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check External Storage Permission
                ActivityCompat.requestPermissions(
                        DashBoardActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        vUpdateButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String vUserName = vUserNameId.getText().toString();

                ////For Checked Existing record of this Date
                //Boolean result = employeeInfoDataManager.findExistingUserName(vUserName);

                //if (result == true) {
                //Toast.makeText(DashBoardActivity.this, "User Name is already exists.", Toast.LENGTH_SHORT).show();
                //} else {
                try {
                    employeeInfoDataManager.updateEployeeData(
                            vEmpNameId.getText().toString().trim(),
                            vEmailId.getText().toString().trim(),
                            vUserNameId.getText().toString().trim(),
                            vPasswordId.getText().toString().trim(),
                            EmployeeInfoActivity.imageViewToByte(vImageViewId),
                            position
                    );

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                    // For Reset All
                    vImageViewId.setImageResource(R.drawable.addphoto);
                    vEmpNameId.setText("");
                    vEmailId.setText("");
                    vUserNameId.setText("");
                    vPasswordId.setText("");
                } catch (Exception error) {
                    Log.e("Update Error", error.getMessage());
                }

                //Get All Record
                updateRecordList();
                //}
            }
        });

        vCancelButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void updateRecordList() {
        // Get all data from SQLite
        Cursor cursor = employeeInfoDataManager.getEmployeeData("SELECT * FROM employeeinfo WHERE id <> '0' ORDER BY id");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            //Add to List
            mList.add(new EmployeeDetailsModel(id, name, email, username, password, image));
        }

        mAdapter.notifyDataSetChanged();
    }

    private void showDeleteDialog(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(DashBoardActivity.this);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are you sure to Delete !!!");

        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    employeeInfoDataManager.deleteEployeeData(idRecord);
                    Toast.makeText(DashBoardActivity.this, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }

                //Get All Record
                updateRecordList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogDelete.show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userinfo_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //        if (item.getItemId() == R.id.dashboardMenuId) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, DashBoardActivity.class);
        //            startActivity(intent);
        //        } else if (item.getItemId() == R.id.empInfoMenuId) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, EmployeeInfoActivity.class);
        //            startActivity(intent);
        //        } else

        if (item.getItemId() == R.id.scheduleInfoMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, ScheduleInfoActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.scheduleAssignMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, ScheduleAssignActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.reportMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.viewScheduleInfoMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, ScheduleInfoViewActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.viewScheduleAssignMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, ScheduleAssignViewActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.logoutMenuId) {
            //Explicit Intent
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //For Stop Hardware Back Button

        // Toast.makeText(this, "Back Button is Disabled", Toast.LENGTH_SHORT).show();
    }
}
