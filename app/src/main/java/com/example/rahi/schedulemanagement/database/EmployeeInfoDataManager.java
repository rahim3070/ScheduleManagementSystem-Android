package com.example.rahi.schedulemanagement.database;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rahi.schedulemanagement.model.EmployeeSpinnerModel;

import java.util.ArrayList;

public class EmployeeInfoDataManager {
    Activity mContex;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public EmployeeInfoDataManager(Activity contex) {
        this.mContex = contex;
        databaseHelper = new DatabaseHelper(contex);
    }

    //For Insert Employee Data
    public void insertEployeeData(String name, String email, String username, String password, byte[] image) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        // Insert Query
        String sql = "INSERT INTO " + databaseHelper.TABLE_EMPLOYEE_INFO + " VALUES (NULL,?,?,?,?,?)";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, email);
        statement.bindString(3, username);
        statement.bindString(4, password);
        statement.bindBlob(5, image);

        statement.executeInsert();
    }

    //For Update Employee Data
    public void updateEployeeData(String name, String email, String username, String password, byte[] image, int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Update Query
        String sql = "UPDATE " + databaseHelper.TABLE_EMPLOYEE_INFO + " SET name=?,email=?,username=?,password=?,image=? WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, email);
        statement.bindString(3, username);
        statement.bindString(4, password);
        statement.bindBlob(5, image);
        statement.bindDouble(6, (double) id);

        statement.execute();
        sqLiteDatabase.close();
    }

    // For Delete Employee Data
    public void deleteEployeeData(int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Delete Query
        String sql = "DELETE FROM " + databaseHelper.TABLE_EMPLOYEE_INFO + " WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        sqLiteDatabase.close();
    }

    //    // For Show Employee Data
    //    public Cursor showAllEmployeeData() {
    //        sqLiteDatabase = databaseHelper.getWritableDatabase();
    //        // return sqLiteDatabase.rawQuery(sql,null);
    //
    //        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_EMPLOYEE_INFO, null);
    //        return cursor;
    //    }

    public Cursor showIndEmployeeData(int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        // return sqLiteDatabase.rawQuery(sql,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_EMPLOYEE_INFO + " WHERE id = " + id, null);
        return cursor;
    }

    // For Show Employee Data Common Query
    public Cursor getEmployeeData(String sql) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    public ArrayList<EmployeeSpinnerModel> getAllSpinnerEmployee() {
        ArrayList<EmployeeSpinnerModel> employeeSpinnerModelArrayList = new ArrayList<>();
        String selectQuery = "select * from " + databaseHelper.TABLE_EMPLOYEE_INFO;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int vEmpId = cursor.getInt(cursor.getColumnIndex(databaseHelper.COLUMN_EMPLOYEE_ID));
                    String vEmpName = cursor.getString(cursor.getColumnIndex(databaseHelper.COLUMN_EMPLOYEE_NAME));

                    EmployeeSpinnerModel employeeSpinnerModel = new EmployeeSpinnerModel(vEmpId, vEmpName);
                    employeeSpinnerModelArrayList.add(employeeSpinnerModel);
                } while (cursor.moveToNext());

            }
        }
        cursor.close();
        db.close();
        return employeeSpinnerModelArrayList;
    }

    public Boolean findExistingUserName(String vUserName) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * From " + databaseHelper.TABLE_EMPLOYEE_INFO + " Where " + databaseHelper.COLUMN_EMPLOYEE_USERNAME + " = '" + vUserName + "'", null);
        Boolean result = false;

        if (cursor.getCount() == 0) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    //    public Cursor getEmployeeId() {
    //        sqLiteDatabase = databaseHelper.getWritableDatabase();
    //        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM " + TABLE_EMPLOYEE_INFO, null);
    //        return cursor;
    //}
}
