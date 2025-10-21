package com.app.myapptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Ayudante para la gestión de la base de datos SQLite.
 * Se encarga de la creación, actualización y operaciones CRUD en la tabla de alimentos.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "comidas.db";
    private static final int DATABASE_VERSION = 2;

    // Sentencia SQL para crear la tabla de alimentos
    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " (" +
                    FoodContract.FoodEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FoodContract.FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                    FoodContract.FoodEntry.COLUMN_DESC + " TEXT)";

    // Sentencia SQL para eliminar la tabla de alimentos
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME;

    /**
     * Constructor de la clase DBHelper.
     * @param context El contexto de la aplicación.
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crea la tabla de alimentos cuando se crea la base de datos por primera vez
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina la tabla existente y la vuelve a crear en una actualización de la base de datos
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    /**
     * Inserta un nuevo alimento en la base de datos.
     * @param nombre El nombre del alimento.
     * @param descripcion La descripción del alimento.
     * @return El ID del nuevo alimento insertado, o -1 si hubo un error.
     */
    public long insertFood(String nombre, String descripcion) {
        long id = -1;
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
            values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
            id = db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
        } finally {
            if (db != null) db.close();
        }
        return id;
    }

    /**
     * Obtiene todos los alimentos de la base de datos, ordenados por nombre.
     * @return Un Cursor con todos los alimentos.
     */
    public Cursor getAllFoods() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodContract.FoodEntry.COLUMN_ID,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        return db.query(FoodContract.FoodEntry.TABLE_NAME, projection, null, null, null, null, FoodContract.FoodEntry.COLUMN_NAME + " ASC");
    }

    /**
     * Obtiene un alimento específico por su ID.
     * @param id El ID del alimento a obtener.
     * @return Un Cursor con los datos del alimento.
     */
    public Cursor getFood(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                FoodContract.FoodEntry.COLUMN_ID,
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        String selection = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] selArgs = { String.valueOf(id) };
        return db.query(FoodContract.FoodEntry.TABLE_NAME, projection, selection, selArgs, null, null, null);
    }

    /**
     * Actualiza los datos de un alimento existente.
     * @param id El ID del alimento a actualizar.
     * @param nombre El nuevo nombre del alimento.
     * @param descripcion La nueva descripción del alimento.
     * @return El número de filas afectadas.
     */
    public int updateFood(long id, String nombre, String descripcion) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
        String where = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        int rows = db.update(FoodContract.FoodEntry.TABLE_NAME, values, where, whereArgs);
        db.close();
        return rows;
    }

    /**
     * Elimina un alimento de la base de datos.
     * @param id El ID del alimento a eliminar.
     * @return El número de filas eliminadas.
     */
    public int deleteFood(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        int deleted = db.delete(FoodContract.FoodEntry.TABLE_NAME, where, whereArgs);
        db.close();
        return deleted;
    }
}
