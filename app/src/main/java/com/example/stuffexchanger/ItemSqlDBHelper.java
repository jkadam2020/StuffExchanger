package com.example.stuffexchanger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jitu on 4/20/2017.
 */

public class ItemSqlDBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "MainDB";

    // Contacts table name
    private static final String TABLE_NAME = "items";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String USER_ID = "userid";
    private static final String ITEM_NAME = "itemname";
    private static final String USER_NAME = "username";
    private static final String ITEM_PRICE = "price";
    private static final String PH_NO = "phone_number";
    private static final String IMAGE = "image";
    private static final String DESCRIPTION = "description";
    private static final String PDATE = "pdate";
    private static final String EMAIL = "email";
    private static final String RATING = "rating";

    public ItemSqlDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +USER_ID + " INTEGER," +  ITEM_NAME + " TEXT,"
                +  USER_NAME + " TEXT,"+  ITEM_PRICE + " REAL,"+  PH_NO + " INTEGER,"+ IMAGE + " BLOB,"
                +DESCRIPTION + " TEXT," +PDATE + " TEXT," +EMAIL + " TEXT," +RATING + " REAL" + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long addItem(Item item)throws SQLiteException {
        Log.d("addItem", item.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(USER_ID, item.getUserID());
        values.put(ITEM_NAME, item.getItemname());
        values.put(USER_NAME, item.getUsername());
        values.put(ITEM_PRICE, item.getPrice());
        values.put(PH_NO, item.getContact());
        values.put(IMAGE,item.getImage());
        values.put(DESCRIPTION, item.getDescription());
        values.put(PDATE, item.getPdate());
        values.put(EMAIL, item.getEmail());
        values.put(RATING, item.getRating());
        // 3. insert
        long rowid=db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/values

        // 4. Close database
        db.close();
        return rowid;
    }
    public List<Item> getAllItems() {
        List<Item> items = new LinkedList<Item>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build item and add it to list
        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setID(cursor.getInt(0));
                item.setUserID(cursor.getInt(1));
                item.setItemname(cursor.getString(2));
                item.setUsername(cursor.getString(3));
                item.setPrice(cursor.getDouble(4));
                item.setContact(cursor.getLong(5));
                item.setImage(cursor.getBlob(6));
                item.setDescription(cursor.getString(7));
                item.setPdate(cursor.getString(8));
                item.setEmail(cursor.getString(9));
                item.setRating(cursor.getDouble(10));
                // Add item to items collection
                items.add(item);
            } while (cursor.moveToNext());
        }

        Log.d("getAllItems()", items.toString());
        // 4. close dbase
        db.close();
        return items;
    }

    public List<Item> getAllItemsOnCondition(String query)
    {
        List<Item> items = new LinkedList<Item>();

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build item and add it to list
        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = new Item();
                item.setID(cursor.getInt(0));
                item.setUserID(cursor.getInt(1));
                item.setItemname(cursor.getString(2));
                item.setUsername(cursor.getString(3));
                item.setPrice(cursor.getDouble(4));
                item.setContact(cursor.getLong(5));
                item.setImage(cursor.getBlob(6));
                item.setDescription(cursor.getString(7));
                item.setPdate(cursor.getString(8));
                item.setEmail(cursor.getString(9));
                item.setRating(cursor.getDouble(10));
                // Add item to items collection
                items.add(item);
            } while (cursor.moveToNext());
        }

        // 4. close dbase
        db.close();
        return items;
    }


    public void refreshRating(double rating,String rateUserEmail) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("rating", rating);

        // 3. updating row
        int rowid = db.update(TABLE_NAME, //table
                values, // column/value
                EMAIL + " = ?", // selections
                new String[]{rateUserEmail}); //selection args

      //   }
        // 4. close dbase
        db.close();


    }
}
