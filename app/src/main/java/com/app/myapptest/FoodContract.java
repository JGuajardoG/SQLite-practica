package com.app.myapptest;

import android.provider.BaseColumns;

/**
 * Define el esquema de la tabla de alimentos (contrato).
 * Contiene constantes para los nombres de la tabla y las columnas.
 */
public final class FoodContract {
    // Constructor privado para evitar que la clase sea instanciada.
    private FoodContract() {}

    /**
     * Define las constantes para la tabla de alimentos.
     * Implementa BaseColumns para incluir _ID y _COUNT.
     */
    public static class FoodEntry implements BaseColumns {
        // Nombre de la tabla en la base de datos
        public static final String TABLE_NAME = "comidas";
        // Nombre de la columna para el ID (necesario para el CursorAdapter)
        public static final String COLUMN_ID = "_id";
        // Nombre de la columna para el nombre del alimento
        public static final String COLUMN_NAME = "nombre";
        // Nombre de la columna para la descripción del alimento
        public static final String COLUMN_DESC = "descripcion";
    }
}
