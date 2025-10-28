package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateFoodActivity extends AppCompatActivity {
    TextInputEditText etNombre, etDescripcion;
    Button btnActualizar, btnVolver;
    DBHelper dbHelper;
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVolver = findViewById(R.id.btnVolver);
        dbHelper = new DBHelper(this);

        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            loadFoodData();
        }

        btnActualizar.setOnClickListener(v -> updateFood());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void loadFoodData() {
        Cursor c = dbHelper.getFood(foodId);
        if (c != null && c.moveToFirst()) {
            etNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
            etDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
            c.close();
        }
    }

    private void updateFood() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese nombre");
            return;
        }

        int updated = dbHelper.updateFood(foodId, nombre, descripcion);
        if (updated > 0) {
            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
