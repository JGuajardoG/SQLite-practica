package com.app.myapptest;

import android.provider.BaseColumns;

// Clase que define la estructura de la tabla comidas
public final class FoodContract {
    private FoodContract() {}

    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "comidas";
        public static final String COLUMN_ID = "_id"; // necesario para CursorAdapter
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_DESC = "descripcion";
    }
}

