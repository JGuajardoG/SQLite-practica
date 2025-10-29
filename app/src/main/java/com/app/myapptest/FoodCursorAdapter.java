package com.app.myapptest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class FoodCursorAdapter extends CursorAdapter {

    public FoodCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_food, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Encuentra las vistas que queremos poblar
        TextView tvName = view.findViewById(R.id.text1);
        TextView tvDescription = view.findViewById(R.id.text2);
        ImageButton btnDelete = view.findViewById(R.id.btn_delete_item);

        // Extrae las propiedades del Cursor
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC));

        // Puebla las vistas con los datos extraídos
        tvName.setText(name);
        tvDescription.setText(description);

        // Configura el listener para el botón de eliminar
        btnDelete.setOnClickListener(v -> {
            Intent deleteIntent = new Intent(context, DeleteFoodActivity.class);
            deleteIntent.putExtra("food_id", id);
            context.startActivity(deleteIntent);
        });

        // Configura el listener para toda la tarjeta para abrir la pantalla de actualizar
        view.setOnClickListener(v -> {
            Intent updateIntent = new Intent(context, UpdateFoodActivity.class);
            updateIntent.putExtra("food_id", id);
            context.startActivity(updateIntent);
        });
    }
}
