package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Actividad principal que muestra una lista de alimentos con un diseño moderno.
 */
public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    SimpleCursorAdapter adapter;
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

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, UpdateFoodActivity.class);
            i.putExtra("food_id", id);
            startActivity(i);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DeleteFoodActivity.class);
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
        cursor = dbHelper.getAllFoods();
        String[] from = {FoodContract.FoodEntry.COLUMN_NAME, FoodContract.FoodEntry.COLUMN_DESC};
        int[] to = {R.id.text1, R.id.text2};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item_food, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}
