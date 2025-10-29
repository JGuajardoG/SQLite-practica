package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Actividad que muestra una pantalla de confirmación para eliminar un alimento.
 * Muestra los detalles del alimento que se va a borrar para evitar errores.
 */
public class DeleteFoodActivity extends AppCompatActivity {
    // Botones para confirmar la eliminación o para cancelar la operación.
    Button btnConfirmar, btnCancelar;
    // Helper para interactuar con la base de datos.
    DBHelper dbHelper;
    // ID del alimento que se va a eliminar.
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        // Configura la Toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa las vistas y el helper de la base de datos.
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);
        dbHelper = new DBHelper(this);

        // Obtiene el ID del alimento del Intent y carga sus datos para mostrarlos.
        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            loadFoodData();
        }

        // Define la lógica para los clics en los botones.
        btnConfirmar.setOnClickListener(v -> deleteFood());
        btnCancelar.setOnClickListener(v -> finish()); // Cierra la actividad.
    }

    /**
     * Carga los datos del alimento a eliminar y los muestra en una vista de item.
     */
    private void loadFoodData() {
        Cursor c = dbHelper.getFood(foodId);
        if (c != null && c.moveToFirst()) {
            // Infla la vista del item de comida para mostrar los detalles.
            View foodItemView = findViewById(R.id.food_item_to_delete);
            TextView tvNombre = foodItemView.findViewById(R.id.text1);
            TextView tvDescripcion = foodItemView.findViewById(R.id.text2);

            tvNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
            tvDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
            c.close();
        }
    }

    /**
     * Elimina el alimento de la base de datos y cierra la actividad.
     */
    private void deleteFood() {
        int deleted = dbHelper.deleteFood(foodId);
        if (deleted > 0) {
            Toast.makeText(this, "Comida eliminada", Toast.LENGTH_SHORT).show();
            finish(); // Regresa a la pantalla principal.
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
