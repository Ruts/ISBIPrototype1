package com.prototype.isbi.isbiprototype1.databaseHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prototype.isbi.isbiprototype1.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MRuto on 2/7/2017.
 */

public class SoldAssetsHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ISBI";

    // Contacts table name
    private static String TABLE_NAME = "SoldAssets";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TYPE = "type";
    private static final String KEY_INFO = "info";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_PAYMENT = "payment";
    private static final String KEY_DATE = "date";
    private static final String KEY_USEFULL = "usefull";
    private static final String KEY_SCRAP = "scrap";
    private static final String KEY_SALE = "sale";
    public static final String KEY_SALE_DATE = "saledate";
    public static final String KEY_FOREIGN_KEY = "foreignKey";

    public SoldAssetsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT," + KEY_INFO + " TEXT,"
                + KEY_TOTAL + " INTEGER," + KEY_PAYMENT + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_USEFULL + " INTEGER," + KEY_SCRAP + " INTEGER," + KEY_SALE + " INTEGER,"
                + KEY_SALE_DATE + " TEXT," + KEY_FOREIGN_KEY + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        //db.close();
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void dropTable() {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getWritableDatabase();

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new person
    public void addAssets(SoldAssets assets) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, assets.getType()); // Name
        values.put(KEY_INFO, assets.getInfo()); // ID/PP
        values.put(KEY_TOTAL, assets.getTotal()); // ID/PP
        values.put(KEY_PAYMENT, assets.getPayment()); // Pin
        values.put(KEY_DATE, assets.getDate()); // Pin
        values.put(KEY_USEFULL, assets.getUsefull()); // Pin
        values.put(KEY_SCRAP, assets.getScrsp()); // Pin
        values.put(KEY_SALE, assets.getSale()); // Pin
        values.put(KEY_SALE_DATE, assets.getSaleDate()); // Pin
        values.put(KEY_FOREIGN_KEY, assets.gettForeignKey()); // Pin

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single person
    public SoldAssets getAssets(int id) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getReadableDatabase();

        SoldAssets assets = new SoldAssets();

        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                        KEY_TYPE, KEY_INFO, KEY_TOTAL, KEY_PAYMENT, KEY_DATE,
                KEY_USEFULL, KEY_SCRAP, KEY_SALE, KEY_SALE_DATE, KEY_FOREIGN_KEY}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            assets = new SoldAssets(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)),
                    cursor.getString(9), Integer.parseInt(cursor.getString(10)));
        }

        cursor.close();
        db.close();
        // return person
        return assets;
    }

    //get using foreign key
    public List<SoldAssets> getByForeignKey(int foreignKey){
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        List<SoldAssets> soldAssetsesList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_FOREIGN_KEY + " = " + foreignKey;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all rows and add to list
        if(cursor.moveToFirst()){
            do{
                SoldAssets assets = new SoldAssets();
                assets.setID(Integer.parseInt(cursor.getString(0)));
                assets.setType(cursor.getString(1));
                assets.setInfo(cursor.getString(2));
                assets.setTotal(Integer.parseInt(cursor.getString(3)));
                assets.setPayment(cursor.getString(4));
                assets.setDate(cursor.getString(5));
                assets.setUsefull(Integer.parseInt(cursor.getString(6)));
                assets.setScrap(Integer.parseInt(cursor.getString(7)));
                assets.setSale(Integer.parseInt(cursor.getString(8)));
                assets.setSaleDate(cursor.getString(9));
                assets.setForeignKay(Integer.parseInt(cursor.getString(10)));
                //add entry to list
                soldAssetsesList.add(assets);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return soldAssetsesList;
    }

    // Getting All PersonData
    public List<SoldAssets> getAllAssets() {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        List<SoldAssets> assetsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SoldAssets assets = new SoldAssets();
                assets.setID(Integer.parseInt(cursor.getString(0)));
                assets.setType(cursor.getString(1));
                assets.setInfo(cursor.getString(2));
                assets.setTotal(Integer.parseInt(cursor.getString(3)));
                assets.setPayment(cursor.getString(4));
                assets.setDate(cursor.getString(5));
                assets.setUsefull(Integer.parseInt(cursor.getString(6)));
                assets.setScrap(Integer.parseInt(cursor.getString(7)));
                assets.setSale(Integer.parseInt(cursor.getString(8)));
                assets.setSaleDate(cursor.getString(9));
                assets.setForeignKay(Integer.parseInt(cursor.getString(10)));
                // Adding person to list
                assetsList.add(assets);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return person list
        return assetsList;
    }

    // Updating single person
    public int updateAssets(SoldAssets assets) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, assets.getType());
        values.put(KEY_INFO, assets.getInfo());
        values.put(KEY_TOTAL, assets.getTotal());
        values.put(KEY_PAYMENT, assets.getPayment());
        values.put(KEY_DATE, assets.getDate());
        values.put(KEY_USEFULL, assets.getUsefull()); // Pin
        values.put(KEY_SCRAP, assets.getScrsp()); // Pin
        values.put(KEY_SALE, assets.getSale()); // Pin
        values.put(KEY_SALE_DATE, assets.getSaleDate()); // Pin
        values.put(KEY_FOREIGN_KEY, assets.gettForeignKey()); // Pin

        int ret = db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(assets.getID()) });

        db.close();
        // updating row
        return ret;
    }

    // Deleting single person
    public void deleteAssets(SoldAssets assets) {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[] { String.valueOf(assets.getID()) });
        db.close();
    }

    // Getting person Count
    public int getAssetsCount() {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int ret = cursor.getCount();

        cursor.close();
        db.close();

        return ret;
    }

    public void isTableExists() {
        TABLE_NAME = "SoldAssets" + LoginActivity.database;
        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT," + KEY_INFO + " TEXT,"
                + KEY_TOTAL + " INTEGER," + KEY_PAYMENT + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_USEFULL + " INTEGER," + KEY_SCRAP + " INTEGER," + KEY_SALE + " INTEGER,"
                + KEY_SALE_DATE + " TEXT," + KEY_FOREIGN_KEY + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

        db.close();
    }

}
