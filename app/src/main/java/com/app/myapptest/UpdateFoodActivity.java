package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad para actualizar un alimento existente en la base de datos.
 */
public class UpdateFoodActivity extends AppCompatActivity {
    // Vistas de la interfaz de usuario
    EditText etNombre, etDescripcion;
    Button btnActualizar, btnVolver;
    // Ayudante de la base de datos
    DBHelper dbHelper;
    // ID del alimento a actualizar
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        // Inicialización de las vistas
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
        // Inicialización del ayudante de la base de datos
        dbHelper = new DBHelper(this);

        // Obtener el ID del alimento del Intent y cargar sus datos
        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            Cursor c = dbHelper.getFood(foodId);
            if (c != null && c.moveToFirst()) {
                // Rellenar los campos de texto con los datos del alimento
                etNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
                etDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
                c.close();
            }
        }

        // Configurar el listener del botón de actualizar
        btnActualizar.setOnClickListener(v -> {
            // Obtener el nombre y la descripción de los EditText
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            // Validación: Asegurarse de que el nombre no esté vacío
            if (nombre.isEmpty()) {
                etNombre.setError("Ingrese nombre");
                return;
            }

            // Actualizar el alimento en la base de datos
            int updated = dbHelper.updateFood(foodId, nombre, descripcion);
            if (updated > 0) {
                // Mostrar un mensaje de éxito y cerrar la actividad
                Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el listener del botón de volver, que cierra la actividad
        btnVolver.setOnClickListener(v -> finish());
    }
}
