package com.app.myapptest;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddFoodActivity extends AppCompatActivity {
    TextInputEditText etNombre, etDescripcion;
    Button btnGuardar, btnVolver;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(v -> saveFood());
        btnVolver.setOnClickListener(v -> finish());
    }

    private void saveFood() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese un nombre");
            return;
        }

        long id = dbHelper.insertFood(nombre, descripcion);
        if (id != -1) {
            Toast.makeText(this, "Comida guardada correctamente", Toast.LENGTH_SHORT).show();
            // Cierra la actividad para volver a la lista principal
            finish();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}
