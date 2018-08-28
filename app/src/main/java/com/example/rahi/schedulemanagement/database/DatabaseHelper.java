package com.example.rahi.schedulemanagement.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "ScheduleMgt";
    static final int DATABASE_VERSION = 23;

    // employeeinfo Table
   public static final String TABLE_EMPLOYEE_INFO = "employeeinfo";

    static final String COLUMN_EMPLOYEE_ID = "id";
    static final String COLUMN_EMPLOYEE_NAME = "name";
    static final String COLUMN_EMPLOYEE_EMAIL = "email";
    static final String COLUMN_EMPLOYEE_USERNAME = "username";
    static final String COLUMN_EMPLOYEE_PASSWORD = "password";
    static final String COLUMN_EMPLOYEE_IMAGE = "image";

    private static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE "
            + TABLE_EMPLOYEE_INFO + "("
            + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMPLOYEE_NAME + " VARCHAR(255),"
            + COLUMN_EMPLOYEE_EMAIL + " TEXT,"
            + COLUMN_EMPLOYEE_USERNAME + " TEXT,"
            + COLUMN_EMPLOYEE_PASSWORD + " EXT,"
            + COLUMN_EMPLOYEE_IMAGE + " BLOB"
            + ")";

    private static final String DROP_TABLE_EMPLOYEE_INFO = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEE_INFO;
//    private static final String SELECT_ALL_DATA_USER_DETAILS = "SELECT * FROM " + TABLE_USER_DETAILS;

    // scheduleinfo Table
    static final String TABLE_SCHEDULE_INFO = "scheduleinfo";

    static final String COLUMN_SCHEDULE_ID = "id";
    static final String COLUMN_SCHEDULE_FromDate = "FromDate";
    static final String COLUMN_SCHEDULE_ToDate = "ToDate";
    static final String COLUMN_SCHEDULE_ShiftFrom1 = "ShiftFrom1";
    static final String COLUMN_SCHEDULE_ShiftTo1 = "ShiftTo1";
    static final String COLUMN_SCHEDULE_ShiftFrom2 = "ShiftFrom2";
    static final String COLUMN_SCHEDULE_ShiftTo2 = "ShiftTo2";
    static final String COLUMN_SCHEDULE_ShiftFrom3 = "ShiftFrom3";
    static final String COLUMN_SCHEDULE_ShiftTo3 = "ShiftTo3";

    private static final String CREATE_TABLE_SCHEDULE_INFO = "CREATE TABLE "
            + TABLE_SCHEDULE_INFO + "("
            + COLUMN_SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SCHEDULE_FromDate + " TEXT,"
            + COLUMN_SCHEDULE_ToDate + " TEXT,"
            + COLUMN_SCHEDULE_ShiftFrom1 + " TEXT,"
            + COLUMN_SCHEDULE_ShiftTo1 + " TEXT,"
            + COLUMN_SCHEDULE_ShiftFrom2 + " TEXT,"
            + COLUMN_SCHEDULE_ShiftTo2 + " TEXT,"
            + COLUMN_SCHEDULE_ShiftFrom3 + " TEXT,"
            + COLUMN_SCHEDULE_ShiftTo3 + " TEXT"
            + ")";

    private static final String DROP_TABLE_SCHEDULE_INFO = "DROP TABLE IF EXISTS " + TABLE_SCHEDULE_INFO;

    // scheduleassign Table
   public static final String TABLE_SCHEDULE_ASSIGN = "scheduleassign";

    static final String COLUMN_SCHEDULE_ASSIGN_ID = "id";
    static final String COLUMN_SCHEDULE_ASSIGN_EmpId = "EmpId";
    static final String COLUMN_SCHEDULE_ASSIGN_ShiftDate = "ShiftDate";
    static final String COLUMN_SCHEDULE_ASSIGN_Shift1 = "Shift1";
    static final String COLUMN_SCHEDULE_ASSIGN_Shift2 = "Shift2";
    static final String COLUMN_SCHEDULE_ASSIGN_Shift3 = "Shift3";
    static final String COLUMN_SCHEDULE_ASSIGN_OffDay = "OffDay";

    private static final String CREATE_TABLE_SCHEDULE_ASSIGN = "CREATE TABLE "
            + TABLE_SCHEDULE_ASSIGN + "("
            + COLUMN_SCHEDULE_ASSIGN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SCHEDULE_ASSIGN_EmpId + " INTEGER,"
            + COLUMN_SCHEDULE_ASSIGN_ShiftDate + " TEXT,"
            + COLUMN_SCHEDULE_ASSIGN_Shift1 + " INTEGER,"
            + COLUMN_SCHEDULE_ASSIGN_Shift2 + " INTEGER,"
            + COLUMN_SCHEDULE_ASSIGN_Shift3 + " INTEGER,"
            + COLUMN_SCHEDULE_ASSIGN_OffDay + " INTEGER"
            + ")";

    private static final String DROP_TABLE_SCHEDULE_ASSIGN = "DROP TABLE IF EXISTS " + TABLE_SCHEDULE_ASSIGN;

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            Toast.makeText(context, "onCreate is Called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
            sqLiteDatabase.execSQL("INSERT INTO " + TABLE_EMPLOYEE_INFO + " VALUES (0,'All Employee','All Employee','arr','123','')");
            sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE_INFO);
            sqLiteDatabase.execSQL(CREATE_TABLE_SCHEDULE_ASSIGN);

        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_SHORT).show();

            sqLiteDatabase.execSQL(DROP_TABLE_EMPLOYEE_INFO);
            sqLiteDatabase.execSQL(DROP_TABLE_SCHEDULE_INFO);
            sqLiteDatabase.execSQL(DROP_TABLE_SCHEDULE_ASSIGN);
            onCreate(sqLiteDatabase);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    // For Login
    public Boolean findUsernamePass(String usrName, String pass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * From " + TABLE_EMPLOYEE_INFO, null);
        Boolean result = false;

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Record Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String userName = cursor.getString(3);
                String password = cursor.getString(4);

                if (userName.equals(usrName) && password.equals(pass)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
