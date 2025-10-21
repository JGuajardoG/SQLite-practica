package com.app.myapptest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Actividad para eliminar un alimento de la base de datos.
 */
public class DeleteFoodActivity extends AppCompatActivity {
    // Vistas de la interfaz de usuario
    TextView tvMensaje;
    Button btnConfirmar, btnCancelar;
    // Ayudante de la base de datos
    DBHelper dbHelper;
    // ID del alimento a eliminar
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        // Inicialización de las vistas
        tvMensaje = findViewById(R.id.tvMensaje);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);
        // Inicialización del ayudante de la base de datos
        dbHelper = new DBHelper(this);

        // Obtener el ID del alimento del Intent
        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
        }

        // Configurar el listener del botón de confirmar
        btnConfirmar.setOnClickListener(v -> {
            // Eliminar el alimento de la base de datos
            int deleted = dbHelper.deleteFood(foodId);
            if (deleted > 0) {
                // Mostrar un mensaje de éxito y cerrar la actividad
                Toast.makeText(this, "Comida eliminada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el listener del botón de cancelar, que cierra la actividad
        btnCancelar.setOnClickListener(v -> finish());
    }
}
