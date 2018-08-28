package com.example.rahi.schedulemanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.adapter.EmployeeSpinnerAdapter;
import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.example.rahi.schedulemanagement.database.ScheduleAssignDataManager;
import com.example.rahi.schedulemanagement.model.EmployeeSpinnerModel;
import com.example.rahi.schedulemanagement.model.ScheduleAssignModel;

import java.util.ArrayList;

public class ScheduleAssignActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner vEmployeeSpinner;
    TextView vSelectDate;
    CheckBox vS1CheckBox, vS2CheckBox, vS3CheckBox, vS4CheckBox;
    Button vScheduleAssignSave, vShowButtonId;
    DatePickerDialog vDatePickerDialog;

    int vEmpId, vS1Checked = 0, vS2Checked = 0, vS3Checked = 0, vS4Checked = 0;

    EmployeeInfoDataManager employeeInfoDataManager;
    ScheduleAssignDataManager scheduleAssignDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_assign);

        employeeInfoDataManager = new EmployeeInfoDataManager(this);
        scheduleAssignDataManager = new ScheduleAssignDataManager(this);

        vEmployeeSpinner = findViewById(R.id.employeeSpinner);
        vSelectDate = findViewById(R.id.selectDate);
        vS1CheckBox = findViewById(R.id.s1CheckBox);
        vS2CheckBox = findViewById(R.id.s2CheckBox);
        vS3CheckBox = findViewById(R.id.s3CheckBox);
        vS4CheckBox = findViewById(R.id.s4CheckBox);

        vScheduleAssignSave = findViewById(R.id.scheduleAssignSave);
        // vShowButtonId = findViewById(R.id.showButton);

        vS1CheckBox.setOnClickListener(this);
        vS2CheckBox.setOnClickListener(this);
        vS3CheckBox.setOnClickListener(this);
        vS4CheckBox.setOnClickListener(this);
        vScheduleAssignSave.setOnClickListener(this);
        // vShowButtonId.setOnClickListener(this);

        DatePicker datePicker = new DatePicker(this);
        final int currentDay = datePicker.getDayOfMonth();
        final int currentMonth = (datePicker.getMonth());
        final int currentYear = datePicker.getYear();

        vSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vDatePickerDialog = new DatePickerDialog(ScheduleAssignActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        vSelectDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);

                vDatePickerDialog.show();
            }
        });

        // load Employee spinner data
        loadEmployeeSpinnerData();

        // spinner onItemSelected value
        vEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /// Get data of Row Clicked from SQLite
                Cursor cursor = employeeInfoDataManager.getEmployeeData("SELECT id FROM employeeinfo");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (cursor.moveToNext()) {
                    arrID.add(cursor.getInt(0));
                }

                // vEmpId = vEmployeeSpinner.getSelectedItemPosition();
                vEmpId = arrID.get(position);
                // Toast.makeText(ScheduleAssignActivity.this, "" + vEmpId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.s1CheckBox) {
            if (vS1CheckBox.isChecked()) {
                vS1Checked = 1;

                unCheckedCheckBox();
            } else {
                vS1Checked = 0;
            }
        } else if (view.getId() == R.id.s2CheckBox) {
            if (vS2CheckBox.isChecked()) {
                vS2Checked = 1;

                unCheckedCheckBox();
            } else {
                vS2Checked = 0;
            }
        } else if (view.getId() == R.id.s3CheckBox) {
            if (vS3CheckBox.isChecked()) {
                vS3Checked = 1;

                unCheckedCheckBox();
            } else {
                vS3Checked = 0;
            }
        } else if (view.getId() == R.id.s4CheckBox) {
            if (vS4CheckBox.isChecked()) {
                // vS1Checked = 0;
                // vS2Checked = 0;
                // vS3Checked = 0;
                vS4Checked = 1;

                unCheckedCheckBox();
            } else {
                vS4Checked = 0;
            }
        } else if (view.getId() == R.id.scheduleAssignSave) {
            if (vEmpId == 0) {
                Toast.makeText(getApplicationContext(), "Please Select an Employee.", Toast.LENGTH_SHORT).show();
            } else {
                String selectDate = vSelectDate.getText().toString();

                if ((selectDate.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please Select a Date.", Toast.LENGTH_SHORT).show();
                } else {
                    ScheduleAssignModel scheduleAssignModel = new ScheduleAssignModel(vEmpId, selectDate, vS1Checked, vS2Checked, vS3Checked, vS4Checked);

                    //For Checked Existing record of this Date
                    Boolean result = scheduleAssignDataManager.findExistingRecord(vEmpId, selectDate);

                    if (result == true) {
                        Toast.makeText(this, "Record already exists for this date, Update your record.", Toast.LENGTH_SHORT).show();
                    } else {
                        //For CheckBox checked or not
                        if ((vS1Checked == 1) || (vS2Checked == 1) || (vS3Checked == 1) || (vS4Checked == 1)) {
                            long rowId = scheduleAssignDataManager.insertScheduleAssignData(scheduleAssignModel);

                            if (rowId == -1) {
                                Toast.makeText(getApplicationContext(), "Not inserted.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Row " + rowId + " is successfully inserted.", Toast.LENGTH_SHORT).show();

                                // //Explicit Intent
                                // Intent intent = new Intent(this, ScheduleAssignViewActivity.class);
                                // startActivity(intent);

                                // For Reset All
                                vS1Checked = 0;
                                vS2Checked = 0;
                                vS3Checked = 0;
                                vS4Checked = 0;

                                vSelectDate.setText("");
                                vS1CheckBox.setChecked(false);
                                vS2CheckBox.setChecked(false);
                                vS3CheckBox.setChecked(false);
                                vS4CheckBox.setChecked(false);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select a Shift.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
        //        else if (view.getId() == R.id.showButton) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, ScheduleAssignViewActivity.class);
        //            startActivity(intent);
        //        }

        // Toast.makeText(this, "" + vS1Checked + vS2Checked + vS3Checked + vS4Checked, Toast.LENGTH_SHORT).show();
    }

    //CheckBox Click Control
    public void unCheckedCheckBox() {
        if ((vS1Checked == 1) || (vS2Checked == 1) || (vS3Checked == 1)) {
            vS4CheckBox.setChecked(false);
        } else if ((vS4Checked == 1)) {
            vS1CheckBox.setChecked(false);
            vS2CheckBox.setChecked(false);
            vS3CheckBox.setChecked(false);
        }
    }

    // Function to load the spinner data from SQLite database
    private void loadEmployeeSpinnerData() {
        // Spinner Drop down elements
        ArrayList<EmployeeSpinnerModel> getEmployeeSpinnerModel = employeeInfoDataManager.getAllSpinnerEmployee();

        // Creating adapter for spinner
        EmployeeSpinnerAdapter employeeSpinnerAdapter = new EmployeeSpinnerAdapter(this, getEmployeeSpinnerModel);

        // attaching data adapter to spinner
        vEmployeeSpinner.setAdapter(employeeSpinnerAdapter);

        //        // Spinner Drop down elements
        //        ArrayList<EmployeeSpinnerModel> getEmployeeSpinnerModel = new ArrayList<>();
        //        getEmployeeSpinnerModel.add(new EmployeeSpinnerModel("ALL Employee"));
        //        getEmployeeSpinnerModel.addAll(employeeInfoDataManager.getAllSpinnerEmployee());
        //
        //        // Creating adapter for spinner
        //        EmployeeSpinnerAdapter employeeSpinnerAdapter = new EmployeeSpinnerAdapter(this, getEmployeeSpinnerModel);
        //
        //        // attaching data adapter to spinner
        //        vEmployeeSpinner.setAdapter(employeeSpinnerAdapter);
    }
}
