package com.example.rahi.schedulemanagement.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rahi.schedulemanagement.model.ScheduleAssignModel;

public class ScheduleAssignDataManager {
    Activity mContex;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public ScheduleAssignDataManager(Activity contex) {
        this.mContex = contex;
        databaseHelper = new DatabaseHelper(contex);
    }

    public Boolean findExistingRecord(int vEmpId, String selectDate) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * From " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " Where " + databaseHelper.COLUMN_SCHEDULE_ASSIGN_EmpId + " = '" + vEmpId + "' and " + databaseHelper.COLUMN_SCHEDULE_ASSIGN_ShiftDate + " = '" + selectDate + "'", null);
        Boolean result = false;

        if (cursor.getCount() == 0) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    //For Insert Schedule Info Data
    public long insertScheduleAssignData(ScheduleAssignModel scheduleAssignModel) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_EmpId, scheduleAssignModel.getvEmpId());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_ShiftDate, scheduleAssignModel.getSelectDate());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_Shift1, scheduleAssignModel.getvS1Checked());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_Shift2, scheduleAssignModel.getvS2Checked());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_Shift3, scheduleAssignModel.getvS3Checked());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ASSIGN_OffDay, scheduleAssignModel.getvS4Checked());

        long rowId = sqLiteDatabase.insert(databaseHelper.TABLE_SCHEDULE_ASSIGN, null, contentValues);

        return rowId;
    }

    // For Show Schedule Assign Data
    //    public Cursor showScheduleAssignData(int vEmpId, String fromDate, String toDate) {
    //        sqLiteDatabase = databaseHelper.getWritableDatabase();
    //
    //        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " Where " + databaseHelper.COLUMN_SCHEDULE_ASSIGN_EmpId + " = '" + vEmpId + "' and " + databaseHelper.COLUMN_SCHEDULE_ASSIGN_ShiftDate + " BETWEEN " + "'" + fromDate + "' and " + "'" + toDate + "'", null);
    //        return cursor;
    //    }

    //    public Cursor showAllScheduleAssignData() {
    //        sqLiteDatabase = databaseHelper.getWritableDatabase();
    //
    //        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " ORDER BY EmpId,ShiftDate", null);
    //        return cursor;
    //    }

    // For Show Employee Data Common Query
    public Cursor getScheduleAssignData(String sql) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    // For Delete Schedule Data
    public void deleteScheduleAssignData(int idRecord) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Delete Query
        String sql = "DELETE FROM " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) idRecord);

        statement.execute();
        sqLiteDatabase.close();
    }

    //For Show Individual Schedule Data
    public Cursor showIndScheduleAssignData(int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        // return sqLiteDatabase.rawQuery(sql,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " WHERE id = " + id, null);
        return cursor;
    }

    //For Update Schedule Data
    public void updateScheduleData(String vEmpId, String vDate, String vShift1, String vShift2, String vShift3, String vShift4, int id) {

        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Update Query
        String sql = "UPDATE " + databaseHelper.TABLE_SCHEDULE_ASSIGN + " SET EmpId=?,ShiftDate=?,Shift1=?,Shift2=?,Shift3=?,OffDay=? WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);

        statement.bindString(1, vEmpId);
        statement.bindString(2, vDate);
        statement.bindString(3, vShift1);
        statement.bindString(4, vShift2);
        statement.bindString(5, vShift3);
        statement.bindString(6, vShift4);
        statement.bindDouble(7, (double) id);

        statement.execute();
        sqLiteDatabase.close();
    }
}
