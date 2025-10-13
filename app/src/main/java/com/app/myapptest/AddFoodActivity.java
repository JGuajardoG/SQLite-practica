package com.app.myapptest;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {
    EditText etNombre, etDescripcion;
    Button btnGuardar, btnVolver;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            // Validación
            if (TextUtils.isEmpty(nombre)) {
                etNombre.setError("Ingrese un nombre");
                return;
            }

            long id = dbHelper.insertFood(nombre, descripcion);
            if (id != -1) {
                Toast.makeText(this, "Comida guardada correctamente", Toast.LENGTH_SHORT).show();
                etNombre.setText("");
                etDescripcion.setText("");
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(v -> finish());
    }
}
