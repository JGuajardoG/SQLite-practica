package com.app.myapptest;

import android.provider.BaseColumns;

/**
 * Define el "contrato" de la base de datos, que es un conjunto de constantes
 * que especifican los nombres de tablas, columnas, etc. Usar un contrato
 * ayuda a evitar errores tipográficos y facilita la modificación de la estructura de la BD.
 */
public final class FoodContract {
    // Se crea un constructor privado para asegurar que esta clase no pueda ser instanciada.
    private FoodContract() {}

    /**
     * Define las constantes para la tabla de alimentos.
     * Al implementar BaseColumns, hereda automáticamente las columnas _ID y _COUNT,
     * que son muy útiles para trabajar con adaptadores de Android.
     */
    public static class FoodEntry implements BaseColumns {
        /** El nombre de la tabla en la base de datos. */
        public static final String TABLE_NAME = "comidas";

        /** Nombre de la columna para el ID único del alimento. Requerido por CursorAdapter. */
        public static final String COLUMN_ID = "_id";

        /** Nombre de la columna para el nombre del alimento. Es de tipo TEXT y no puede ser nulo. */
        public static final String COLUMN_NAME = "nombre";

        /** Nombre de la columna para la descripción del alimento. Es de tipo TEXT. */
        public static final String COLUMN_DESC = "descripcion";
    }
}
