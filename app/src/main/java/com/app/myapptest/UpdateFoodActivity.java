package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateFoodActivity extends AppCompatActivity {
    EditText etNombre, etDescripcion;
    Button btnActualizar, btnVolver;
    DBHelper dbHelper;
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);

        dbHelper = new DBHelper(this);

        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            Cursor c = dbHelper.getFood(foodId);
            if (c != null && c.moveToFirst()) {
                etNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
                etDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
            }
            if (c != null) c.close();
        }

        btnActualizar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("Ingrese nombre");
                return;
            }

            int updated = dbHelper.updateFood(foodId, nombre, descripcion);
            if (updated > 0) {
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(v -> finish());
    }
}
