package com.app.myapptest;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Actividad que presenta un formulario para agregar un nuevo alimento a la base de datos.
 */
public class AddFoodActivity extends AppCompatActivity {
    // Campos de texto para el nombre y la descripción del alimento.
    TextInputEditText etNombre, etDescripcion;
    // Botones para guardar el nuevo alimento o para volver a la pantalla principal.
    Button btnGuardar, btnVolver;
    // Helper para interactuar con la base de datos.
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Configura la Toolbar como la barra de acción de la actividad.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa las vistas y el helper de la base de datos.
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        dbHelper = new DBHelper(this);

        // Define la lógica para los clics en los botones.
        btnGuardar.setOnClickListener(v -> saveFood());
        btnVolver.setOnClickListener(v -> finish()); // Cierra la actividad actual.
    }

    /**
     * Valida los datos de entrada y guarda el nuevo alimento en la base de datos.
     */
    private void saveFood() {
        String nombre = etNombre.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        // Valida que el nombre no esté vacío.
        if (TextUtils.isEmpty(nombre)) {
            etNombre.setError("Ingrese un nombre");
            return;
        }

        // Inserta el alimento en la BD.
        long id = dbHelper.insertFood(nombre, descripcion);
        if (id != -1) {
            Toast.makeText(this, "Comida guardada correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Regresa a la pantalla anterior.
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}
