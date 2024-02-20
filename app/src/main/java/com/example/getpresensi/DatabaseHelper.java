package com.example.getpresensi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRESENSI = "presensi";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULLNAME = "fullname"; // tambahkan kolom nama lengkap
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Perbarui skema tabel users di dalam metode onCreate() pada DatabaseHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "Creating tables...");
        // Tabel untuk manajemen pengguna
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_FULLNAME + " TEXT" + ")";
        db.execSQL(createUserTableQuery);

        // Tabel untuk manajemen presensi
        String createPresensiTableQuery = "CREATE TABLE " + TABLE_PRESENSI + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT" + ")";
        db.execSQL(createPresensiTableQuery);
        Log.d("DatabaseHelper", "Tables created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // Perbarui metode addUser() di dalam DatabaseHelper
    public boolean addUser(String username, String password, String fullname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FULLNAME, fullname); // Masukkan nilai fullname ke dalam ContentValues
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }


    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Metode untuk manajemen presensi
    public boolean addPresensi(double latitude, double longitude, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        long result = db.insert(TABLE_PRESENSI, null, values);
        db.close(); // Penting: pastikan untuk menutup database setelah pengoperasian selesai
        return result != -1; // Mengembalikan true jika penyisipan berhasil, dan false jika gagal
    }


    public Cursor getAllPresensi() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRESENSI, null);
    }
}