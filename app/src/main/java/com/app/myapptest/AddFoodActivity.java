package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {
    EditText etNombre, etDescripcion;
    Button btnGuardar, btnEliminar, btnVolver;
    DBHelper dbHelper;
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnVolver = findViewById(R.id.btnVolver); // 👈 Nuevo botón

        dbHelper = new DBHelper(this);

        // Revisar si la Activity recibió un ID (modo edición)
        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
        }

        if (foodId != -1) {
            Cursor c = dbHelper.getFood(foodId);
            if (c != null && c.moveToFirst()) {
                etNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
                etDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
                btnGuardar.setText("Actualizar");
                btnEliminar.setVisibility(android.view.View.VISIBLE);
            }
            if (c != null) c.close();
        } else {
            btnEliminar.setVisibility(android.view.View.GONE);
        }

        // Guardar o actualizar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("Ingrese nombre");
                return;
            }

            if (foodId == -1) {
                long id = dbHelper.insertFood(nombre, descripcion);
                if (id != -1) {
                    Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            } else {
                int updated = dbHelper.updateFood(foodId, nombre, descripcion);
                if (updated > 0) {
                    Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar")
                    .setMessage("¿Desea eliminar esta comida?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        int deleted = dbHelper.deleteFood(foodId);
                        if (deleted > 0) {
                            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Volver sin cambios
        btnVolver.setOnClickListener(v -> finish());
    }
}
