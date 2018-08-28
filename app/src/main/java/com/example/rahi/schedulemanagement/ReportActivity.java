package com.example.rahi.schedulemanagement;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahi.schedulemanagement.adapter.EmployeeSpinnerAdapter;
import com.example.rahi.schedulemanagement.database.EmployeeInfoDataManager;
import com.example.rahi.schedulemanagement.model.EmployeeSpinnerModel;
import com.example.rahi.schedulemanagement.utility.PDFTemplate;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner vEmployeeSpinner;
    TextView vFromDate, vToDate;
    Button imgBtnShowEmpDetail, imgBtnShiftAssign;
    DatePickerDialog vDatePickerDialog;
    PDFTemplate pdfTemplate;

    EmployeeInfoDataManager employeeInfoDataManager;

    int vEmpId;
    String fromDate, toDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        employeeInfoDataManager = new EmployeeInfoDataManager(this);

        // initialize here
        vEmployeeSpinner = findViewById(R.id.employeeSpinner);
        vFromDate = findViewById(R.id.selectFromDate);
        vToDate = findViewById(R.id.selectToDate);

        imgBtnShowEmpDetail = findViewById(R.id.imgBtnEmpDetail);
        imgBtnShiftAssign = findViewById(R.id.imgBtnShiftAssignPDF);

        pdfTemplate = new PDFTemplate(this);

        vFromDate.setOnClickListener(this);
        vToDate.setOnClickListener(this);
        imgBtnShowEmpDetail.setOnClickListener(this);
        imgBtnShiftAssign.setOnClickListener(this);

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
                // Toast.makeText(ReportActivity.this, "" + vEmpId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        } else if (view.getId() == R.id.imgBtnEmpDetail) {

            fromDate = vFromDate.getText().toString();
            toDate = vToDate.getText().toString();

            // create pdf
            try {
                pdfTemplate.createPdf(vEmpId);
                // Toast.makeText(this, "Employee Info Report Generated", Toast.LENGTH_SHORT).show();
                pdfTemplate.viewEmpPDF();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.imgBtnShiftAssignPDF) {
            fromDate = vFromDate.getText().toString();
            toDate = vToDate.getText().toString();
            // create pdf
            try {
                if (vEmpId == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select an Employee.", Toast.LENGTH_SHORT).show();
                } else {
                    if (fromDate.equals("") || toDate.equals("")) {
                        Toast.makeText(getApplicationContext(), "Please Give Date.", Toast.LENGTH_SHORT).show();
                    } else {
                        pdfTemplate.createAssignDataToPdf(vEmpId, fromDate, toDate);
                        // Toast.makeText(this, "Assigned Schedule Info Report Generated", Toast.LENGTH_SHORT).show();
                        pdfTemplate.viewPDF();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
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
