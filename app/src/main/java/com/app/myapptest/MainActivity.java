package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Actividad principal que muestra una lista de alimentos con un diseño moderno.
 */
public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    FoodCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        FloatingActionButton btnAddFood = findViewById(R.id.btnAddFood);

        btnAddFood.setOnClickListener(v -> startActivity(new Intent(this, AddFoodActivity.class)));

        // Toda la lógica de clics (actualizar y eliminar) se maneja ahora
        // directamente en el FoodCursorAdapter para evitar conflictos.
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        cursor = dbHelper.getAllFoods();
        // Usamos nuestro nuevo adaptador personalizado.
        adapter = new FoodCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}
