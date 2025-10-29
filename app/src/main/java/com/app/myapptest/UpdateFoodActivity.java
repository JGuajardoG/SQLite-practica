package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Actividad que presenta un formulario para actualizar un alimento existente.
 * Los campos se precargan con los datos actuales del alimento.
 */
public class UpdateFoodActivity extends AppCompatActivity {
    // Campos de texto para editar el nombre y la descripción.
    TextInputEditText etNombre, etDescripcion;
    // Botones para confirmar la actualización o para volver atrás.
    Button btnActualizar, btnVolver;
    // Helper para interactuar con la base de datos.
    DBHelper dbHelper;
    // ID del alimento que se está editando. Se recibe a través de un Intent.
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        // Configura la Toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa las vistas y el helper de la base de datos.
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
        dbHelper = new DBHelper(this);

        // Obtiene el ID del alimento del Intent y carga sus datos en los campos.
        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            loadFoodData();
        }

        // Define la lógica para los clics en los botones.
        btnActualizar.setOnClickListener(v -> updateFood());
        btnVolver.setOnClickListener(v -> finish()); // Cierra la actividad.
    }

    /**
     * Carga los datos del alimento desde la base de datos y los muestra en los EditText.
     */
    private void loadFoodData() {
        Cursor c = dbHelper.getFood(foodId);
        if (c != null && c.moveToFirst()) {
            etNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
            etDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
            c.close();
        }
    }

    /**
     * Valida los nuevos datos y actualiza el alimento en la base de datos.
     */
    private void updateFood() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        // Valida que el nombre no esté vacío.
        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese nombre");
            return;
        }

        // Actualiza el registro en la BD.
        int updated = dbHelper.updateFood(foodId, nombre, descripcion);
        if (updated > 0) {
            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Regresa a la pantalla anterior.
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }
}
