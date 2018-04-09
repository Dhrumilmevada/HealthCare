package com.example.dhrumil.healthcare.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nilesh on 2/21/2018.
 */

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HealthCare.db";
    private static final int DATA_VERSION = 2;
    private static final String EMAIL_ID = "email_id";
    private static final String KEY_ID = "id";
    private static final String KEY_PASS = "password";
    private static final String KEY_PH_NO = "mobile_number";
    private static final String LOGIN_TABLE="login_tab";
    private static final String DOCTOR_TABLE = "doctor_tab";
    private static final String PATIENT_TABLE = "patient_table";
    private static final String KEY_NAME = "Full_name";
    private static final String CLINIC_NAME = "clinic_name";
    private static final String REGISTRATION_NO = "registration_no";
    private static final String HIGHER_QUAL = "higher_qual";
    private static final String PRACTICE_AS = "practice_as";
    private static final String CLINIC_ADDR = "clinic_address";
    private static final String DATE_OF_BIRTH = "date_of_birth";
    private static final String AGE = "age";
    private static final String ADDR = "addr";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String USER_TYPE = "user_type";

    public Database(Context context) {

        super(context,DATABASE_NAME,null,DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + LOGIN_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_NAME+ " TEXT," + EMAIL_ID + " TEXT,"+ KEY_PASS + " TEXT" + ")";
        String CREATE_DOCTOR_TABLE = "CREATE TABLE " + DOCTOR_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_ID + " TEXT,"+ REGISTRATION_NO + " TEXT,"
                + KEY_PH_NO + " TEXT,"+ CLINIC_NAME + "TEXT," + CLINIC_ADDR + "TEXT," + HIGHER_QUAL +"TEXT," + PRACTICE_AS + "TEXT" + ")";
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + PATIENT_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_ID + " TEXT,"+ DATE_OF_BIRTH + " DATE,"
                + KEY_PH_NO + " TEXT,"+ AGE + "INTEGER," + ADDR + "TEXT," + HEIGHT +"INTEGER," + WEIGHT + "INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_DOCTOR_TABLE);
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PATIENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DOCTOR_TABLE);
        if(i < i1) {
            db.execSQL("ALTER TABLE " +LOGIN_TABLE+ " ADD COLUMN" + USER_TYPE + "TEXT");
        }

        onCreate(db);

    }

    public void addUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL_ID, user.getEmail());
        values.put(KEY_PASS, user.getPassword());
        values.put(KEY_NAME, user.getName());
        values.put(USER_TYPE, user.getusertype());
        db.insert(LOGIN_TABLE, null, values);

        db.close();
    }

    public void addDoctor(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL_ID, user.getEmail());
        values.put(REGISTRATION_NO, user.getRegistrationId());
        values.put(KEY_PH_NO, user.getMobile());
        values.put(CLINIC_ADDR, user.getClinicAddr());
        values.put(CLINIC_NAME, user.getClinicName());
        values.put(HIGHER_QUAL, user.getHigher());
        values.put(PRACTICE_AS, user.getPractice());
        db.insert(DOCTOR_TABLE, null, values);
        db.close();
    }

    public void addPatient(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMAIL_ID, user.getEmail());
        values.put(DATE_OF_BIRTH, user.getBirthDate());
        values.put(KEY_PH_NO, user.getMobile());
        values.put(ADDR, user.getAddr());
        values.put(HEIGHT, user.getHeight());
        values.put(WEIGHT, user.getWeight());
        values.put(AGE, user.getAge());
        db.insert(PATIENT_TABLE, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        String[] columns = {KEY_ID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMAIL_ID + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(LOGIN_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUse(String mob){
        String[] columns = {KEY_ID};
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_PH_NO + " = ?";
        String[] selectionArgs = { mob };

        Cursor cursor = db.query(DOCTOR_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUse_patient(String mob){
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = KEY_PH_NO + " = ?";
        String[] selectionArgs = { mob };

        Cursor cursor = db.query(PATIENT_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }


    public boolean checkUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {KEY_ID};

        String selection = EMAIL_ID + " = ?" + " AND " + KEY_PASS + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(LOGIN_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }
    public String getUsertype(){
        SQLiteDatabase db = this.getWritableDatabase();
        String usertype = "SELECT "+ USER_TYPE +" FROM " + LOGIN_TABLE;
        Cursor cursor = db.rawQuery(usertype,null);
        String data = null;
        if (cursor.moveToFirst()) {
            do {
               data = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return  data;
    }
}
