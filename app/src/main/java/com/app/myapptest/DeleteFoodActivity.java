package com.app.myapptest;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DeleteFoodActivity extends AppCompatActivity {
    Button btnConfirmar, btnCancelar;
    DBHelper dbHelper;
    long foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);
        dbHelper = new DBHelper(this);

        if (getIntent() != null && getIntent().hasExtra("food_id")) {
            foodId = getIntent().getLongExtra("food_id", -1);
            loadFoodData();
        }

        btnConfirmar.setOnClickListener(v -> deleteFood());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void loadFoodData() {
        Cursor c = dbHelper.getFood(foodId);
        if (c != null && c.moveToFirst()) {
            View foodItemView = findViewById(R.id.food_item_to_delete);
            TextView tvNombre = foodItemView.findViewById(R.id.text1);
            TextView tvDescripcion = foodItemView.findViewById(R.id.text2);

            tvNombre.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_NAME)));
            tvDescripcion.setText(c.getString(c.getColumnIndexOrThrow(FoodContract.FoodEntry.COLUMN_DESC)));
            c.close();
        }
    }

    private void deleteFood() {
        int deleted = dbHelper.deleteFood(foodId);
        if (deleted > 0) {
            Toast.makeText(this, "Comida eliminada", Toast.LENGTH_SHORT).show();
            // Cierra esta actividad y vuelve a la lista principal, que se refrescará.
            finish();
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
