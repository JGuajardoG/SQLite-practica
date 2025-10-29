package com.app.myapptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase de ayuda para gestionar la base de datos SQLite. Se encarga de la creación,
 * actualización y de las operaciones CRUD (Crear, Leer, Actualizar, Borrar) para los alimentos.
 */
public class DBHelper extends SQLiteOpenHelper {
    // Constantes para el nombre y la versión de la base de datos.
    private static final String DATABASE_NAME = "comidas.db";
    private static final int DATABASE_VERSION = 2;

    /**
     * Constructor de la clase.
     * @param context Contexto de la aplicación.
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Se llama cuando la base de datos se crea por primera vez.
     * Aquí se debe ejecutar la sentencia para crear las tablas.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + FoodContract.FoodEntry.TABLE_NAME + " (" +
                FoodContract.FoodEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodContract.FoodEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FoodContract.FoodEntry.COLUMN_DESC + " TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * Se llama cuando la base de datos necesita ser actualizada. Esto ocurre cuando
     * DATABASE_VERSION es mayor que la versión de la base de datos actual.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Política de actualización simple: borra la tabla y la vuelve a crear.
        db.execSQL("DROP TABLE IF EXISTS " + FoodContract.FoodEntry.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserta un nuevo alimento en la base de datos.
     * @param nombre El nombre del alimento.
     * @param descripcion La descripción del alimento.
     * @return El ID de la nueva fila insertada, o -1 si hubo un error.
     */
    public long insertFood(String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
        long id = db.insert(FoodContract.FoodEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Recupera todos los alimentos de la base de datos.
     * @return Un objeto Cursor que apunta a todos los registros encontrados.
     */
    public Cursor getAllFoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(FoodContract.FoodEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * Recupera un único alimento por su ID.
     * @param id El ID del alimento a buscar.
     * @return Un objeto Cursor que apunta al registro encontrado, o null si no se encontró.
     */
    public Cursor getFood(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        return db.query(FoodContract.FoodEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    /**
     * Actualiza un alimento existente en la base de datos.
     * @param id El ID del alimento a actualizar.
     * @param nombre El nuevo nombre del alimento.
     * @param descripcion La nueva descripción del alimento.
     * @return El número de filas afectadas en la operación.
     */
    public int updateFood(long id, String nombre, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodContract.FoodEntry.COLUMN_NAME, nombre);
        values.put(FoodContract.FoodEntry.COLUMN_DESC, descripcion);
        String whereClause = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        int rows = db.update(FoodContract.FoodEntry.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    /**
     * Elimina un alimento de la base de datos por su ID.
     * @param id El ID del alimento a eliminar.
     * @return El número de filas eliminadas.
     */
    public int deleteFood(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = FoodContract.FoodEntry.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(id) };
        int rows = db.delete(FoodContract.FoodEntry.TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rows;
    }
}
