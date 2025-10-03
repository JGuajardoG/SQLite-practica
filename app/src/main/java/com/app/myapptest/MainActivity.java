package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    Button btnAdd;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        btnAdd = findViewById(R.id.btnAddFood);

        // Nuevo alimento
        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(i);
        });

        // Editar (click corto)
        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Intent i = new Intent(MainActivity.this, UpdateFoodActivity.class);
            i.putExtra("food_id", id);
            startActivity(i);
        });

        // Eliminar (click largo)
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Intent i = new Intent(MainActivity.this, DeleteFoodActivity.class);
            i.putExtra("food_id", id);
            startActivity(i);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Cursor cursor = dbHelper.getAllFoods();
        String[] fromColumns = {
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        int[] toViews = { android.R.id.text1, android.R.id.text2 };

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                fromColumns,
                toViews,
                0);

        listView.setAdapter(adapter);
    }
}
