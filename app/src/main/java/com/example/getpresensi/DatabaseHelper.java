package com.example.getpresensi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myapp.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " +
            TABLE_USERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FULL_NAME + " TEXT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(String fullName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {
                COLUMN_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public String getFullName(String username) {
        String fullName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_FULL_NAME};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            int fullNameIndex = cursor.getColumnIndex(COLUMN_FULL_NAME);
            if (fullNameIndex != -1) { // Pastikan indeks kolom ditemukan
                fullName = cursor.getString(fullNameIndex);
            }
            cursor.close();
        }
        db.close();
        return fullName;
    }
}
