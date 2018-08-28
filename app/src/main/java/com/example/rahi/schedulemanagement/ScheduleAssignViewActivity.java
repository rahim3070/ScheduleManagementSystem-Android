package com.example.rahi.schedulemanagement;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.adapter.EmployeeSpinnerAdapter;
import com.example.rahi.schedulemanagement.adapter.ScheduleAssignViewAdapter;
import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.example.rahi.schedulemanagement.database.ScheduleAssignDataManager;
import com.example.rahi.schedulemanagement.model.EmployeeSpinnerModel;
import com.example.rahi.schedulemanagement.model.ScheduleAssignModel;
import com.example.rahi.schedulemanagement.model.ScheduleAssignViewModel;

import java.util.ArrayList;

public class ScheduleAssignViewActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner vEmployeeSpinner;
    TextView vFromDate, vToDate, vMsgInfo;
    LinearLayout vAssinedSchInfo;
    Button vShowButtonId, vAddNewScheduleBtn;
    ListView vScheduleListViewId;
    DatePickerDialog vDatePickerDialog;

    int vEmpId;

    ArrayList<ScheduleAssignViewModel> mList;
    ArrayList<ScheduleAssignModel> mList2;
    ScheduleAssignViewAdapter mAdapter = null;

    //    // From schedule_info_update_dialog for Update Dialog
    //    EditText vEmployeeSpinner, vDate, vShift1, vShift2, vShift3, vShift4;
    //    Button vUpdateButtonId;
    //
    //    int vEmpId, vS1Checked = 0, vS2Checked = 0, vS3Checked = 0, vS4Checked = 0;
    //
    //    DatePickerDialog vDatePickerDialog;
    //    String AmPm;

    EmployeeInfoDataManager employeeInfoDataManager;
    ScheduleAssignDataManager scheduleAssignDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_assign_view);

        employeeInfoDataManager = new EmployeeInfoDataManager(this);
        scheduleAssignDataManager = new ScheduleAssignDataManager(this);

        vEmployeeSpinner = findViewById(R.id.employeeSpinner);
        vFromDate = findViewById(R.id.selectFromDate);
        vToDate = findViewById(R.id.selectToDate);
        vMsgInfo = findViewById(R.id.msgInfo);

        vAssinedSchInfo = findViewById(R.id.assinedSchInfo);

        vShowButtonId = findViewById(R.id.showButton);
        vAddNewScheduleBtn = findViewById(R.id.assignNewScheduleButton);
        vScheduleListViewId = findViewById(R.id.scheduleAssignLV);

        vFromDate.setOnClickListener(this);
        vToDate.setOnClickListener(this);
        vShowButtonId.setOnClickListener(this);
        vAddNewScheduleBtn.setOnClickListener(this);

        // load Employee spinner data
        loadEmployeeSpinnerData();

        // spinner onItemSelected value
        vEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get data of Row Clicked from SQLite
                Cursor cursor = employeeInfoDataManager.getEmployeeData("SELECT id FROM employeeinfo");
                ArrayList<Integer> arrID = new ArrayList<Integer>();
                while (cursor.moveToNext()) {
                    arrID.add(cursor.getInt(0));
                }

                // vEmpId = vEmployeeSpinner.getSelectedItemPosition();
                vEmpId = arrID.get(position);
                // Toast.makeText(ScheduleAssignViewActivity.this, "" + vEmpId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mList = new ArrayList<>();
        mList2 = new ArrayList<>();
        mAdapter = new ScheduleAssignViewAdapter(this, R.layout.scheduleassign_view, mList);
        vScheduleListViewId.setAdapter(mAdapter);

        //For Alert Dialog
        vScheduleListViewId.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                //Alert Dialog to display option of Update & Delete
                final CharSequence[] items = {"Assign Schedule", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ScheduleAssignViewActivity.this);
                dialog.setTitle("Choose an Option");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            //New Record Insert
                            //Explicit Intent
                            Intent intent = new Intent(ScheduleAssignViewActivity.this, ScheduleAssignActivity.class);
                            startActivity(intent);

                            //                            //Update
                            //                            Cursor c = scheduleAssignDataManager.getScheduleAssignData("SELECT id FROM scheduleassign");
                            //                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            //                            while (c.moveToNext()) {
                            //                                arrID.add(c.getInt(0));
                            //                            }
                            //
                            //                            // Show Update Dialog - employee_info_update_dialog
                            //                            showUpdateDialog(ScheduleAssignViewActivity.this, arrID.get(position));
                        }

                        if (i == 1) {
                            //Delete
                            Cursor c = scheduleAssignDataManager.getScheduleAssignData("SELECT id FROM scheduleassign");
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

    @Override
    public void onClick(View view) {
        DatePicker datePicker = new DatePicker(this);
        int currentDay = datePicker.getDayOfMonth();
        int currentMonth = (datePicker.getMonth());
        int currentYear = datePicker.getYear();

        if (view.getId() == R.id.selectFromDate) {
            vDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    vFromDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, currentYear, currentMonth, currentDay);

            vDatePickerDialog.show();
        } else if (view.getId() == R.id.selectToDate) {
            vDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    vToDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, currentYear, currentMonth, currentDay);

            vDatePickerDialog.show();
        } else if (view.getId() == R.id.showButton) {
            if (vEmpId == 0) {
                Toast.makeText(getApplicationContext(), "Please Select an Employee.", Toast.LENGTH_SHORT).show();
            } else {
                String fromDate = vFromDate.getText().toString();
                String toDate = vToDate.getText().toString();
                Cursor cursor;

                if (fromDate.equals("") || toDate.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Give Date.", Toast.LENGTH_SHORT).show();
                } else {
                    //Get All Record
                    cursor = scheduleAssignDataManager.getScheduleAssignData("SELECT * FROM scheduleassign WHERE EmpId = '" + vEmpId + "' and ShiftDate BETWEEN '" + fromDate + "' and '" + toDate + "' ORDER BY EmpId,ShiftDate");
                    mList.clear();
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String EmpId = cursor.getString(1);
                        String SDate = cursor.getString(2);
                        String shift1 = cursor.getString(3);
                        String shift2 = cursor.getString(4);
                        String shift3 = cursor.getString(5);
                        String shift4 = cursor.getString(6);

                        //Add to List
                        mList.add(new ScheduleAssignViewModel(id, EmpId, SDate, shift1, shift2, shift3, shift4));
                    }

                    mAdapter.notifyDataSetChanged();

                    if (mList.size() == 0) {
                        vMsgInfo.setText("");
                        vAssinedSchInfo.setVisibility(View.INVISIBLE);
                        Toast.makeText(this, "No Record Found.", Toast.LENGTH_SHORT).show();
                    } else {
                        vMsgInfo.setText("Assigned Schedule");
                        vAssinedSchInfo.setVisibility(View.VISIBLE);
                    }
                }
            }
        } else if (view.getId() == R.id.assignNewScheduleButton) {
            //Explicit Intent
            Intent intent = new Intent(ScheduleAssignViewActivity.this, ScheduleAssignActivity.class);
            startActivity(intent);
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

    //    private void showUpdateDialog(Activity activity, final int position) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.schedule_assign_update_dialog);
//        dialog.setTitle("Update Assigned Schedule");
//
//        // From schedule_assign_update_dialog for Update Dialog
////        vEmployeeSpinner = dialog.findViewById(R.id.editEmployeeSpinner);
//        vDate = dialog.findViewById(R.id.editSelectDate);
//        vShift1 = dialog.findViewById(R.id.editS1CheckBox);
//        vShift2 = dialog.findViewById(R.id.editS2CheckBox);
//        vShift3 = dialog.findViewById(R.id.editS3CheckBox);
//        vShift4 = dialog.findViewById(R.id.editS4CheckBox);
//
//        vUpdateButtonId = dialog.findViewById(R.id.scheduleAssignUpdate);
//
////        // load Employee spinner data
////        loadEmployeeSpinnerData();
//
////        // spinner onItemSelected value
////        vEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                // Get data of Row Clicked from SQLite
////                Cursor cursor = employeeInfoDataManager.getEmployeeData("SELECT id FROM employeeinfo");
////                ArrayList<Integer> arrID = new ArrayList<Integer>();
////                while (cursor.moveToNext()) {
////                    arrID.add(cursor.getInt(0));
////                }
////
////                vEmpId = arrID.get(position);
////
//////                Toast.makeText(ScheduleAssignActivity.this, "" + vEmpId, Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });
//
//        //For Date & Time Picker Dialog
//        DatePicker datePicker = new DatePicker(this);
//        final int currentDay = datePicker.getDayOfMonth();
//        final int currentMonth = (datePicker.getMonth());
//        final int currentYear = datePicker.getYear();
//
//        vDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                vDatePickerDialog = new DatePickerDialog(ScheduleAssignViewActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        vDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                    }
//                }, currentYear, currentMonth, currentDay);
//
//                vDatePickerDialog.show();
//            }
//        });
//
////        // CheckBox Control
////        vShift1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (vShift1.isC) {
////                    vS1Checked = 1;
////
////                    unCheckedCheckBox();
////                } else {
////                    vS1Checked = 0;
////                }
////            }
////        });
////
////        vShift2.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
////
////        vShift3.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
////
////        vShift4.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//        // Get data of Row Clicked from SQLite
//        Cursor cursor = scheduleAssignDataManager.showIndScheduleAssignData(position);
//        mList.clear();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//
//            int vEmployeeId = cursor.getInt(1);
//            vEmployeeSpinner.setText(vEmployeeId);
//
//            String scheduleDate = cursor.getString(2);
//            vDate.setText(scheduleDate);
//
//            int vS1Check = cursor.getInt(3);
//            vShift1.setText(vS1Check);
//
//            int vS2Check = cursor.getInt(4);
//            vShift2.setText(vS2Check);
//
//            int vS3Check = cursor.getInt(5);
//            vShift3.setText(vS3Check);
//
//            int vS4Check = cursor.getInt(6);
//            vShift4.setText(vS4Check);
//
//            //Add to List
//            mList2.add(new ScheduleAssignModel(id, vEmployeeId, scheduleDate, vS1Check, vS2Check, vS3Check, vS4Check));
//        }
//
////        mAdapter.notifyDataSetChanged();
//
//        //Set Width & Height of Dialog Box
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);
//
//        dialog.getWindow().setLayout(width, height);
//        dialog.show();
//
//        vUpdateButtonId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    scheduleAssignDataManager.updateScheduleData(
//                            vEmployeeSpinner.getText().toString().trim(),
//                            vDate.getText().toString().trim(),
//                            vShift1.getText().toString().trim(),
//                            vShift2.getText().toString().trim(),
//                            vShift3.getText().toString().trim(),
//                            vShift4.getText().toString().trim(),
//                            position
//                    );
//
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                    // For Reset All
//                    vDate.setText("");
////                    vS1CheckBox.setChecked(false);
////                    vS2CheckBox.setChecked(false);
////                    vS3CheckBox.setChecked(false);
////                    vS4CheckBox.setChecked(false);
//                } catch (Exception error) {
//                    Log.e("Update Error", error.getMessage());
//                }
//
//                //Get All Record
//                updateRecordList();
//            }
//        });
//    }

//    // Function to load the spinner data from SQLite database
//    private void loadEmployeeSpinnerData() {
//        // Spinner Drop down elements
//        ArrayList<EmployeeSpinnerModel> getEmployeeSpinnerModel = employeeInfoDataManager.getAllSpinnerEmployee();
//
//        // Creating adapter for spinner
//        EmployeeSpinnerAdapter employeeSpinnerAdapter = new EmployeeSpinnerAdapter(this, getEmployeeSpinnerModel);
//
//        // attaching data adapter to spinner
//        vEmployeeSpinner.setAdapter(employeeSpinnerAdapter);
//    }
//
//    //CheckBox Click Control
//    public void unCheckedCheckBox() {
//        if ((vS1Checked == 1) || (vS2Checked == 1) || (vS3Checked == 1)) {
//            vS4CheckBox.setChecked(false);
//        } else if ((vS4Checked == 1)) {
//            vS1CheckBox.setChecked(false);
//            vS2CheckBox.setChecked(false);
//            vS3CheckBox.setChecked(false);
//        }
//    }

    private void updateRecordList() {
        // Get all data from SQLite
        Cursor cursor = scheduleAssignDataManager.getScheduleAssignData("SELECT * FROM scheduleassign");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String EmpId = cursor.getString(1);
            String SDate = cursor.getString(2);
            String shift1 = cursor.getString(3);
            String shift2 = cursor.getString(4);
            String shift3 = cursor.getString(5);
            String shift4 = cursor.getString(6);

            //Add to List
            mList.add(new ScheduleAssignViewModel(id, EmpId, SDate, shift1, shift2, shift3, shift4));
        }

        mAdapter.notifyDataSetChanged();
    }

    private void showDeleteDialog(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ScheduleAssignViewActivity.this);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are you sure to Delete !!!");

        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    scheduleAssignDataManager.deleteScheduleAssignData(idRecord);
                    Toast.makeText(ScheduleAssignViewActivity.this, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
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
}
