package com.app.myapptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "comidas.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " (" +
                    FoodContract.FoodEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FoodContract.FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    FoodContract.FoodEntry.COLUMN_DESC + " TEXT)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public long insertFood(String nombre, String descripcion) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
        return db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllFoods() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodContract.FoodEntry.COLUMN_ID,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        return db.query(FoodContract.FoodEntry.TABLE_NAME,
                projection, null, null, null, null, null);
    }

    public Cursor getFood(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodContract.FoodEntry.COLUMN_ID,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        String selection = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] selArgs = { String.valueOf(id) };
        return db.query(FoodContract.FoodEntry.TABLE_NAME, projection,
                selection, selArgs, null, null, null);
    }

    public int updateFood(long id, String nombre, String descripcion) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
        String where = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.update(FoodContract.FoodEntry.TABLE_NAME, values, where, whereArgs);
    }

    public int deleteFood(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        return db.delete(FoodContract.FoodEntry.TABLE_NAME, where, whereArgs);
    }
}

