package com.example.rahi.schedulemanagement.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.rahi.schedulemanagement.model.ScheduleInfoModel;

public class ScheduleInfoDataManager {
    Activity mContex;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public ScheduleInfoDataManager(Activity contex) {
        this.mContex = contex;
        databaseHelper = new DatabaseHelper(contex);
    }

    //For Insert Schedule Info Data
    public long insertScheduleInfoData(ScheduleInfoModel scheduleInfoModel) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_FromDate, scheduleInfoModel.getFromDate());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ToDate, scheduleInfoModel.getToDate());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftFrom1, scheduleInfoModel.getShiftFrom1());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftTo1, scheduleInfoModel.getShiftTo1());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftFrom2, scheduleInfoModel.getShiftFrom2());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftTo2, scheduleInfoModel.getShiftTo2());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftFrom3, scheduleInfoModel.getShiftFrom3());
        contentValues.put(databaseHelper.COLUMN_SCHEDULE_ShiftTo3, scheduleInfoModel.getShiftTo3());

        long rowId = sqLiteDatabase.insert(databaseHelper.TABLE_SCHEDULE_INFO, null, contentValues);

        return rowId;
    }

    // For Show Schedule Data
    public Cursor showAllScheduleInfoData() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        // return sqLiteDatabase.rawQuery(sql,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_SCHEDULE_INFO, null);
        return cursor;
    }

    // For Show Employee Data Common Query
    public Cursor getScheduleInfoData(String sql) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql, null);
    }

    //For Show Individual Schedule Data
    public Cursor showIndScheduleInfoData(int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        // return sqLiteDatabase.rawQuery(sql,null);

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + databaseHelper.TABLE_SCHEDULE_INFO + " WHERE id = " + id, null);
        return cursor;
    }

    //For Update Schedule Data
    public void updateScheduleData(String vFromDate, String vToDate, String vShiftFrom1, String vShiftTo1, String vShiftFrom2, String vShiftTo2, String vShiftFrom3, String vShiftTo3, int id) {

        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Update Query
        String sql = "UPDATE " + databaseHelper.TABLE_SCHEDULE_INFO + " SET FromDate=?,ToDate=?,ShiftFrom1=?,ShiftTo1=?,ShiftFrom2=?,ShiftTo2=?,ShiftFrom3=?,ShiftTo3=? WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);

        statement.bindString(1, vFromDate);
        statement.bindString(2, vToDate);
        statement.bindString(3, vShiftFrom1);
        statement.bindString(4, vShiftTo1);
        statement.bindString(5, vShiftFrom2);
        statement.bindString(6, vShiftTo2);
        statement.bindString(7, vShiftFrom3);
        statement.bindString(8, vShiftTo3);
        statement.bindDouble(9, (double) id);

        statement.execute();
        sqLiteDatabase.close();
    }

    // For Delete Schedule Data
    public void deleteScheduleInfoData(int id) {
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        // Delete Query
        String sql = "DELETE FROM " + databaseHelper.TABLE_SCHEDULE_INFO + " WHERE id=?";

        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        sqLiteDatabase.close();
    }
}
