package com.app.myapptest;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad para agregar un nuevo alimento a la base de datos.
 */
public class AddFoodActivity extends AppCompatActivity {
    // Vistas de la interfaz de usuario
    EditText etNombre, etDescripcion;
    Button btnGuardar, btnVolver;
    // Ayudante de la base de datos
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Inicialización de las vistas
        etNombre = findViewById(R.id.etNombre);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        // Inicialización del ayudante de la base de datos
        dbHelper = new DBHelper(this);

        // Configurar el listener del botón de guardar
        btnGuardar.setOnClickListener(v -> {
            // Obtener el nombre y la descripción de los EditText
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            // Validación: Asegurarse de que el nombre no esté vacío
            if (TextUtils.isEmpty(nombre)) {
                etNombre.setError("Ingrese un nombre");
                return;
            }

            // Insertar el nuevo alimento en la base de datos
            long id = dbHelper.insertFood(nombre, descripcion);
            if (id != -1) {
                // Mostrar un mensaje de éxito y limpiar los campos de texto
                Toast.makeText(this, "Comida guardada correctamente", Toast.LENGTH_SHORT).show();
                etNombre.setText("");
                etDescripcion.setText("");
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el listener del botón de volver, que cierra la actividad
        btnVolver.setOnClickListener(v -> finish());
    }
}
