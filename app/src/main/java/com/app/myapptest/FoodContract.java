package com.app.myapptest;

public final class FoodContract {
    private FoodContract() {}

    public static class FoodEntry {
        public static final String TABLE_NAME = "comidas";
        public static final String COLUMN_ID = "_id"; // requerido para SimpleCursorAdapter
        public static final String COLUMN_NAME = "nombre";
        public static final String COLUMN_DESC = "descripcion";
    }
}
