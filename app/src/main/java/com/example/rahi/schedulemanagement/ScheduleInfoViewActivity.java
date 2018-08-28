package com.example.rahi.schedulemanagement;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.adapter.ScheduleInfoViewAdapter;
import com.example.rahi.schedulemanagement.database.ScheduleInfoDataManager;
import com.example.rahi.schedulemanagement.model.ScheduleInfoModel;
import com.example.rahi.schedulemanagement.model.ScheduleInfoViewModel;

import java.util.ArrayList;

public class ScheduleInfoViewActivity extends AppCompatActivity {
    Button vAddNewScheduleBtn;
    TextView vMsgInfo;
    ListView vScheduleListViewId;
    ArrayList<ScheduleInfoViewModel> mList;
    ArrayList<ScheduleInfoModel> mList2;
    ScheduleInfoViewAdapter mAdapter = null;

    // From schedule_info_update_dialog for Update Dialog
    TextView vFromDate, vToDate, vShiftFrom1, vShiftTo1, vShiftFrom2, vShiftTo2, vShiftFrom3, vShiftTo3;
    Button vUpdateButtonId, vCancelButtonId;

    DatePickerDialog vDatePickerDialog;
    TimePickerDialog vTimePickerDialog;
    String AmPm;

    ScheduleInfoDataManager scheduleInfoDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_info_view);

        scheduleInfoDataManager = new ScheduleInfoDataManager(this);
        vAddNewScheduleBtn = findViewById(R.id.addNewScheduleButton);
        vMsgInfo = findViewById(R.id.msgInfo);
        vScheduleListViewId = findViewById(R.id.scheduleInfoLV);

        mList = new ArrayList<>();
        mList2 = new ArrayList<>();
        mAdapter = new ScheduleInfoViewAdapter(this, R.layout.scheduleinfo_view, mList);
        vScheduleListViewId.setAdapter(mAdapter);

        //For Load ScheduleInfoActivity
        vAddNewScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Explicit Intent
                Intent intent = new Intent(ScheduleInfoViewActivity.this, ScheduleInfoActivity.class);
                startActivity(intent);
            }
        });

        //Get All Record
        Cursor cursor = scheduleInfoDataManager.showAllScheduleInfoData();
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String fromDate = cursor.getString(1);
            String toDate = cursor.getString(2);
            String shiftFrom1 = cursor.getString(3);
            String shiftTo1 = cursor.getString(4);
            String shiftFrom2 = cursor.getString(5);
            String shiftTo2 = cursor.getString(6);
            String shiftFrom3 = cursor.getString(7);
            String shiftTo3 = cursor.getString(8);

            String vScheduleDate = (fromDate + " - " + toDate);
            String vShift1 = (shiftFrom1 + " - " + shiftTo1);
            String vShift2 = (shiftFrom2 + " - " + shiftTo2);
            String vShift3 = (shiftFrom3 + " - " + shiftTo3);

            //Add to List
            mList.add(new ScheduleInfoViewModel(id, vScheduleDate, vShift1, vShift2, vShift3));
        }

        mAdapter.notifyDataSetChanged();

        if (mList.size() == 0) {
            vMsgInfo.setText("");
            Toast.makeText(this, "No Record Found.", Toast.LENGTH_SHORT).show();
        } else {
            vMsgInfo.setText("Schedule Info");
        }

        //For Alert Dialog
        vScheduleListViewId.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                //Alert Dialog to display option of Update & Delete
                final CharSequence[] items = {"Update", "Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(ScheduleInfoViewActivity.this);
                dialog.setTitle("Choose an Option");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i == 0) {
                            //Update
                            Cursor c = scheduleInfoDataManager.getScheduleInfoData("SELECT id FROM scheduleinfo");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }

                            // Show Update Dialog - employee_info_update_dialog
                            showUpdateDialog(ScheduleInfoViewActivity.this, arrID.get(position));
                        }

                        if (i == 1) {
                            //Delete
                            Cursor c = scheduleInfoDataManager.getScheduleInfoData("SELECT id FROM scheduleinfo");
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
        dialog.setContentView(R.layout.schedule_info_update_dialog);
        dialog.setTitle("Update Schedule Info");

        // From schedule_info_update_dialog for Update Dialog
        vFromDate = dialog.findViewById(R.id.editFromDate);
        vToDate = dialog.findViewById(R.id.editToDate);
        vShiftFrom1 = dialog.findViewById(R.id.editFromTime1);
        vShiftTo1 = dialog.findViewById(R.id.editToTime1);
        vShiftFrom2 = dialog.findViewById(R.id.editFromTime2);
        vShiftTo2 = dialog.findViewById(R.id.editToTime2);
        vShiftFrom3 = dialog.findViewById(R.id.editFromTime3);
        vShiftTo3 = dialog.findViewById(R.id.editToTime3);

        vUpdateButtonId = dialog.findViewById(R.id.updateButton);
        vCancelButtonId = dialog.findViewById(R.id.cancelButton);

        //For Date & Time Picker Dialog
        DatePicker datePicker = new DatePicker(this);
        final int currentDay = datePicker.getDayOfMonth();
        final int currentMonth = (datePicker.getMonth());
        final int currentYear = datePicker.getYear();

        TimePicker timePicker = new TimePicker(this);
        final int currentHour = timePicker.getCurrentHour();
        final int currentMinute = timePicker.getCurrentMinute();

        vFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vDatePickerDialog = new DatePickerDialog(ScheduleInfoViewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        vFromDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);

                vDatePickerDialog.show();
            }
        });

        vToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vDatePickerDialog = new DatePickerDialog(ScheduleInfoViewActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        vToDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, currentYear, currentMonth, currentDay);

                vDatePickerDialog.show();
            }
        });

        vShiftFrom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                        vShiftFrom1.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        vShiftTo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        vShiftTo1.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        vShiftFrom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        vShiftFrom2.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        vShiftTo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        vShiftTo2.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        vShiftFrom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        vShiftFrom3.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        vShiftTo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vTimePickerDialog = new TimePickerDialog(ScheduleInfoViewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        String time = hour + ":" + minute;
                        vShiftTo3.setText(time);
                    }
                }, currentHour, currentMinute, true);

                vTimePickerDialog.show();
            }
        });

        // Get data of Row Clicked from SQLite
        Cursor cursor = scheduleInfoDataManager.showIndScheduleInfoData(position);
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);

            String fromDate = cursor.getString(1);
            vFromDate.setText(fromDate);

            String toDate = cursor.getString(2);
            vToDate.setText(toDate);

            String shiftFrom1 = cursor.getString(3);
            vShiftFrom1.setText(shiftFrom1);

            String shiftTo1 = cursor.getString(4);
            vShiftTo1.setText(shiftTo1);

            String shiftFrom2 = cursor.getString(5);
            vShiftFrom2.setText(shiftFrom2);

            String shiftTo2 = cursor.getString(6);
            vShiftTo2.setText(shiftTo2);

            String shiftFrom3 = cursor.getString(7);
            vShiftFrom3.setText(shiftFrom3);

            String shiftTo3 = cursor.getString(8);
            vShiftTo3.setText(shiftTo3);

            //Add to List
            mList2.add(new ScheduleInfoModel(id, fromDate, toDate, shiftFrom1, shiftTo1, shiftFrom2, shiftTo2, shiftFrom3, shiftTo3));
        }

        // mAdapter.notifyDataSetChanged();

        //Set Width & Height of Dialog Box
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);

        dialog.getWindow().setLayout(width, height);
        dialog.show();

        vUpdateButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    scheduleInfoDataManager.updateScheduleData(
                            vFromDate.getText().toString().trim(),
                            vToDate.getText().toString().trim(),
                            vShiftFrom1.getText().toString().trim(),
                            vShiftTo1.getText().toString().trim(),
                            vShiftFrom2.getText().toString().trim(),
                            vShiftTo2.getText().toString().trim(),
                            vShiftFrom3.getText().toString().trim(),
                            vShiftTo3.getText().toString().trim(),
                            position
                    );

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                    // For Reset All
                    vFromDate.setText("");
                    vToDate.setText("");
                    vShiftFrom1.setText("");
                    vShiftTo1.setText("");
                    vShiftFrom2.setText("");
                    vShiftTo2.setText("");
                    vShiftFrom3.setText("");
                    vShiftTo3.setText("");
                } catch (Exception error) {
                    Log.e("Update Error", error.getMessage());
                }

                //Get All Record
                updateRecordList();
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
        Cursor cursor = scheduleInfoDataManager.getScheduleInfoData("SELECT * FROM scheduleinfo");
        mList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String fromDate = cursor.getString(1);
            String toDate = cursor.getString(2);
            String shiftFrom1 = cursor.getString(3);
            String shiftTo1 = cursor.getString(4);
            String shiftFrom2 = cursor.getString(5);
            String shiftTo2 = cursor.getString(6);
            String shiftFrom3 = cursor.getString(7);
            String shiftTo3 = cursor.getString(8);

            String vScheduleDate = (fromDate + " - " + toDate);
            String vShift1 = (shiftFrom1 + " - " + shiftTo1);
            String vShift2 = (shiftFrom2 + " - " + shiftTo2);
            String vShift3 = (shiftFrom3 + " - " + shiftTo3);

            //Add to List
            mList.add(new ScheduleInfoViewModel(id, vScheduleDate, vShift1, vShift2, vShift3));
        }

        mAdapter.notifyDataSetChanged();
    }

    private void showDeleteDialog(final int idRecord) {
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ScheduleInfoViewActivity.this);
        dialogDelete.setTitle("Warning");
        dialogDelete.setMessage("Are you sure to Delete !!!");

        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    scheduleInfoDataManager.deleteScheduleInfoData(idRecord);
                    Toast.makeText(ScheduleInfoViewActivity.this, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
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
    }}
