package com.app.myapptest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteFoodActivity extends AppCompatActivity {
    TextView tvMensaje;
    Button btnConfirmar, btnCancelar;
    DBHelper dbHelper;
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        tvMensaje = findViewById(R.id.tvMensaje);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);
        dbHelper = new DBHelper(this);

        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
        }

        btnConfirmar.setOnClickListener(v -> {
            int deleted = dbHelper.deleteFood(foodId);
            if (deleted > 0) {
                Toast.makeText(this, "Comida eliminada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}

