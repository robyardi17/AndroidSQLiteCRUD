package com.example.loginform.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.loginform.Model.ModelUser;
import com.example.loginform.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tes1";
    public static final int DATABASE_VERSION = 1;
    /** table **/
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PRODUCT = "product";

    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_NOMOR = "nomor";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ALAMAT = "alamat";

    public static final String KEY_ID_PRODUCT = "id_product";
    public static final String KEY_PRODUCT = "nama_product";

//    SELECT a.`id_penjualan`,b.username,c.produk,c.harga, a.`qty_penjualan`,(c.harga*a.`qty_penjualan`) total FROM `penjualan` a join user b on a.id_user=b.id_user join table_produk c on a.id_produk=c.id_produk

    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_ALAMAT + " TEXT,"
            + KEY_NOMOR + " TEXT"
            + " ) ";


    public static final String SQL_TABLE_PRODUCT = " CREATE TABLE " + TABLE_PRODUCT
            + " ( "
            + KEY_ID_PRODUCT + " INTEGER PRIMARY KEY, "
            + KEY_PRODUCT + " TEXT "
            + " ) ";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_PRODUCT);
    }

    public void addUser(ModelUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NOMOR, user.getNomor());
        values.put(KEY_ALAMAT, user.getAlamat());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<ModelUser> getAllUser() {
        String[] columns = {
                KEY_ID,
                KEY_USER_NAME,
                KEY_PASSWORD,
                KEY_ALAMAT,
                KEY_NOMOR
        };

        String sortOrder = KEY_USER_NAME + " ASC";
        List<ModelUser> userList = new ArrayList<ModelUser>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        if (cursor.moveToFirst()) {
            do {
                ModelUser user = new ModelUser();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_ID))));
                user.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                user.setAlamat(cursor.getString(cursor.getColumnIndex(KEY_ALAMAT)));
                user.setNomor(cursor.getString(cursor.getColumnIndex(KEY_NOMOR)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void updateUser(ModelUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_NOMOR, user.getNomor());
        values.put(KEY_ALAMAT, user.getAlamat());
        // updating row
        db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteUser(ModelUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USERS, KEY_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public boolean checkUser(String username) {
        String[] columns = {KEY_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_USER_NAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USERS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {KEY_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = KEY_USER_NAME + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USERS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /** Product **/
    public void addProduct(ProductModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_PRODUCT, model.getId_product());
        values.put(KEY_PRODUCT, model.getProduct());
        db.insert(TABLE_PRODUCT, null, values);
        db.close();
    }
}