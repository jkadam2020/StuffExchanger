package com.example.stuffexchanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jitu on 4/8/2017.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MainDB";
    public static final String TABLE_NAME = "userdb";
    public static final String KEY_ID = "id";
    public static final String COLUMN_NAME_FIRSTNAME = "firstname";
    public static final String COLUMN_NAME_LASTNAME = "lastname";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PASSWORD = "password";
    public static final String COLUMN_NAME_RATING = "rating";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_FIRSTNAME + " TEXT," +
                    COLUMN_NAME_LASTNAME + " TEXT,"+
                    COLUMN_NAME_EMAIL + " TEXT," +
                    COLUMN_NAME_PASSWORD + " TEXT," +
                    COLUMN_NAME_RATING + " REAL )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // On upgrade discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addUser(User user)throws SQLiteException {
        Log.d("addUser", user.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_FIRSTNAME,user.getFirstname());
        values.put(COLUMN_NAME_LASTNAME, user.getLastname());
        values.put(COLUMN_NAME_EMAIL, user.getEmail());
        values.put(COLUMN_NAME_PASSWORD, user.getPassword());
        values.put(COLUMN_NAME_RATING, user.getRating());

        // 3. insert
        long rowid=db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close database
        db.close();
        return rowid;
    }
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build user and add it to list
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(cursor.getInt(0));
                user.setFirstname(cursor.getString(1));
                user.setLastname(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                user.setRating(cursor.getDouble(5));

                users.add(user);
            } while (cursor.moveToNext());
        }


        // 4. close dbase
        db.close();
        return users;
    }
    public int UserCheck(String query) {

        // 2. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return 0;
        }
        cursor.close();
        // 4. close dbase
        db.close();
        return 1;
    }
    public User getUserDetails(String query) {

        // 2. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build item and add it to list
        User user = new User();

        if (cursor.moveToFirst()) {
            do {
                user.setId(cursor.getInt(0));
                user.setFirstname(cursor.getString(1));
                user.setLastname(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                user.setRating(cursor.getDouble(5));
            } while (cursor.moveToNext());
        }

        // 4. close dbase
        db.close();

        return user;
    }


}
