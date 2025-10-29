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

/**
 * Adaptador personalizado para la lista de alimentos. Este adaptador es responsable de tomar
 * los datos de un Cursor (proveniente de la base de datos) y vincularlos a un layout de item
 * específico (list_item_food.xml). También gestiona los eventos de clic en cada item.
 */
public class FoodCursorAdapter extends CursorAdapter {

    public FoodCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Crea una nueva vista de item de lista. Android llama a este método cuando necesita
     * crear una nueva fila en la lista. Simplemente infla el layout y lo devuelve.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_food, parent, false);
    }

    /**
     * Vincula los datos de una fila del Cursor a la vista del item de lista correspondiente.
     * Android llama a este método para cada item visible en la pantalla.
     * Aquí es donde se configuran los textos, imágenes y listeners de cada fila.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Encuentra las vistas dentro del layout del item.
        TextView tvName = view.findViewById(R.id.text1);
        TextView tvDescription = view.findViewById(R.id.text2);
        ImageButton btnDelete = view.findViewById(R.id.btn_delete_item);

        // Extrae los datos de la fila actual del Cursor.
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC));

        // Asigna los datos a las vistas.
        tvName.setText(name);
        tvDescription.setText(description);

        //--- Lógica de Interacción ---

        // 1. Botón de Eliminar: Al hacer clic, abre la actividad de eliminación.
        btnDelete.setOnClickListener(v -> {
            Intent deleteIntent = new Intent(context, DeleteFoodActivity.class);
            deleteIntent.putExtra("food_id", id);
            context.startActivity(deleteIntent);
        });

        // 2. Clic en la Tarjeta: Al hacer clic en cualquier otra parte de la fila, abre la actividad de actualización.
        view.setOnClickListener(v -> {
            Intent updateIntent = new Intent(context, UpdateFoodActivity.class);
            updateIntent.putExtra("food_id", id);
            context.startActivity(updateIntent);
        });
    }
}
