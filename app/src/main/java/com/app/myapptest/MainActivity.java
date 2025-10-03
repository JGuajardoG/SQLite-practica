package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    Button btnAdd;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        btnAdd = findViewById(R.id.btnAddFood);


        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(i);
        });


        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            Intent i = new Intent(MainActivity.this, AddFoodActivity.class);
            i.putExtra("food_id", id);
            startActivity(i);
        });


        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Eliminar")
                    .setMessage("¿Eliminar esta comida?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        int deleted = dbHelper.deleteFood(id);
                        if (deleted > 0) {
                            Toast.makeText(MainActivity.this, "Eliminado", Toast.LENGTH_SHORT).show();
                            loadData();
                        } else {
                            Toast.makeText(MainActivity.this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Cursor newCursor = dbHelper.getAllFoods();

        String[] fromColumns = {
                FoodContract.FoodEntry.COLUMN_NAME,
                FoodContract.FoodEntry.COLUMN_DESC
        };
        int[] toViews = { android.R.id.text1, android.R.id.text2 };

        if (adapter == null) {
            adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2,
                    newCursor,
                    fromColumns,
                    toViews,
                    0);
            listView.setAdapter(adapter);
        } else {
            Cursor old = adapter.getCursor();
            adapter.changeCursor(newCursor);
            if (old != null && !old.isClosed()) old.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null && adapter.getCursor() != null) {
            adapter.getCursor().close();
        }
        if (dbHelper != null) dbHelper.close();
    }
}
