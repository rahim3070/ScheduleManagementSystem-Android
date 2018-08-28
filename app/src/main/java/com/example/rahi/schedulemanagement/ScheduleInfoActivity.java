package com.example.rahi.schedulemanagement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.database.ScheduleInfoDataManager;
import com.example.rahi.schedulemanagement.model.ScheduleInfoModel;

public class ScheduleInfoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView vFromDateET, vToDateET, vFT1ET, vTT1ET, vFT2ET, vTT2ET, vFT3ET, vTT3ET;
    Button vSiSaveBtn,vShowButtonId;
    DatePickerDialog vDatePickerDialog;
    TimePickerDialog vTimePickerDialog;
    ScheduleInfoDataManager scheduleInfoDataManager;
    String AmPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_info);

        vFromDateET = findViewById(R.id.fromDate);
        vToDateET = findViewById(R.id.toDate);
        vFT1ET = findViewById(R.id.fromTime1);
        vTT1ET = findViewById(R.id.toTime1);
        vFT2ET = findViewById(R.id.fromTime2);
        vTT2ET = findViewById(R.id.toTime2);
        vFT3ET = findViewById(R.id.fromTime3);
        vTT3ET = findViewById(R.id.toTime3);

        vSiSaveBtn = findViewById(R.id.siSave);
        // vShowButtonId = findViewById(R.id.showButton);

        scheduleInfoDataManager = new ScheduleInfoDataManager(this);

        vFromDateET.setOnClickListener(this);
        vToDateET.setOnClickListener(this);

        vFT1ET.setOnClickListener(this);
        vTT1ET.setOnClickListener(this);
        vFT2ET.setOnClickListener(this);
        vTT2ET.setOnClickListener(this);
        vFT3ET.setOnClickListener(this);
        vTT3ET.setOnClickListener(this);

        vSiSaveBtn.setOnClickListener(this);
        // vShowButtonId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DatePicker datePicker = new DatePicker(this);
        int currentDay = datePicker.getDayOfMonth();
        int currentMonth = (datePicker.getMonth());
        int currentYear = datePicker.getYear();

        TimePicker timePicker = new TimePicker(this);
        int currentHour = timePicker.getCurrentHour();
        int currentMinute = timePicker.getCurrentMinute();

        //From Date & To Date Picker
        if (view.getId() == R.id.fromDate) {
            vDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    vFromDateET.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, currentYear, currentMonth, currentDay);

            vDatePickerDialog.show();
        } else if (view.getId() == R.id.toDate) {
            vDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    vToDateET.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }, currentYear, currentMonth, currentDay);

            vDatePickerDialog.show();
        } else if (view.getId() == R.id.fromTime1) {                                                       //From Time & To Time Picker
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //                    if (hour >= 12) {
                    //                        AmPm = " PM";
                    //                    } else {
                    //                        AmPm = " AM";
                    //                    }
                    //
                    //                    String time = (hour + ":" + minute) + AmPm;

                    String time = hour + ":" + minute;

                    vFT1ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.toTime1) {
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = hour + ":" + minute;

                    vTT1ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.fromTime2) {
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = hour + ":" + minute;

                    vFT2ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.toTime2) {
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = hour + ":" + minute;

                    vTT2ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.fromTime3) {
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = hour + ":" + minute;

                    vFT3ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.toTime3) {
            vTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    String time = hour + ":" + minute;

                    vTT3ET.setText(time);
                }
            }, currentHour, currentMinute, true);

            vTimePickerDialog.show();
        } else if (view.getId() == R.id.siSave) {
            String fromDate = vFromDateET.getText().toString();
            String toDate = vToDateET.getText().toString();
            String shiftFrom1 = vFT1ET.getText().toString();
            String shiftTo1 = vTT1ET.getText().toString();
            String shiftFrom2 = vFT2ET.getText().toString();
            String shiftTo2 = vTT2ET.getText().toString();
            String shiftFrom3 = vFT3ET.getText().toString();
            String shiftTo3 = vTT3ET.getText().toString();

            if ((fromDate.equals("")) || (toDate.equals("")) || (shiftFrom1.equals("")) || (shiftTo1.equals("")) || (shiftFrom2.equals("")) || (shiftTo2.equals("")) || (shiftFrom3.equals("")) || (shiftTo3.equals(""))) {
                Toast.makeText(getApplicationContext(), "Please Give All Data.", Toast.LENGTH_SHORT).show();
            } else {

                ScheduleInfoModel scheduleInfoModel = new ScheduleInfoModel(fromDate, toDate, shiftFrom1, shiftTo1, shiftFrom2, shiftTo2, shiftFrom3, shiftTo3);

                long rowId = scheduleInfoDataManager.insertScheduleInfoData(scheduleInfoModel);

                if (rowId == -1) {
                    Toast.makeText(getApplicationContext(), "Not inserted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Row " + rowId + " is successfully inserted.", Toast.LENGTH_SHORT).show();

                    //Explicit Intent
                    Intent intent = new Intent(this, ScheduleInfoViewActivity.class);
                    startActivity(intent);

                    // For Reset All
                    vFromDateET.setText("");
                    vToDateET.setText("");
                    vFT1ET.setText("");
                    vTT1ET.setText("");
                    vFT2ET.setText("");
                    vTT2ET.setText("");
                    vFT3ET.setText("");
                    vTT3ET.setText("");
                }
            }
        }
        //        else if (view.getId() == R.id.showButton) {
        //            //Explicit Intent
        //            Intent intent = new Intent(this, ScheduleInfoViewActivity.class);
        //            startActivity(intent);
        //        }
    }
}
