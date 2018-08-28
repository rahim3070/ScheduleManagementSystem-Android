package com.example.rahi.schedulemanagement;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //    LinearLayout linearLayout;
    DatabaseHelper databaseHelper;
    EditText vUserNameEtId, vPasswordEtId;
    Button vSignInBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        linearLayout = findViewById(R.id.scrollViewId);
        //
        //        if (loadColor() != getResources().getColor(R.color.white)) {
        //            linearLayout.setBackgroundColor(loadColor());
        //        }

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        vUserNameEtId = findViewById(R.id.userNameET);
        vPasswordEtId = findViewById(R.id.passwordET);

        vSignInBtnId = findViewById(R.id.signInBtn);

        vSignInBtnId.setOnClickListener(this);
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.signin_menu_layout, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.grayMenuId) {
//            linearLayout.setBackgroundColor(getResources().getColor(R.color.DimGray));
//            storeColor(getResources().getColor(R.color.DimGray));
//        } else if (item.getItemId() == R.id.darkergrayMenuId) {
//            linearLayout.setBackgroundColor(getResources().getColor(R.color.darker_gray));
//            storeColor(getResources().getColor(R.color.darker_gray));
//        } else if (item.getItemId() == R.id.lightinverseMenuId) {
//            linearLayout.setBackgroundColor(getResources().getColor(R.color.dim_foreground_light_inverse));
//            storeColor(getResources().getColor(R.color.dim_foreground_light_inverse));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void storeColor(int color) {
//        SharedPreferences sharedPreferences = getSharedPreferences("BackgroundColor", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putInt("mycolor", color);
//        editor.commit();
//    }
//
//    private int loadColor() {
//        SharedPreferences sharedPreferences = getSharedPreferences("BackgroundColor", Context.MODE_PRIVATE);
//
//        int selectedColor = sharedPreferences.getInt("mycolor", getResources().getColor(R.color.white));
//        return selectedColor;
//    }

    @Override
    public void onClick(View view) {
        String usrName = vUserNameEtId.getText().toString();
        String pass = vPasswordEtId.getText().toString();

        if (view.getId() == R.id.signInBtn) {
            if ((usrName.equals("")) && (pass.equals(""))) {
                Toast.makeText(this, "Please Give Your Username & Password", Toast.LENGTH_SHORT).show();
            } else {
                Boolean result = databaseHelper.findUsernamePass(usrName, pass);

                if (result == true) {
                    //Explicit Intent
                    Intent intent = new Intent(this, DashBoardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Username & Password didn't match", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        //For Stop Hardware Back Button

        // Toast.makeText(this, "Back Button is Disabled", Toast.LENGTH_SHORT).show();
    }
}
