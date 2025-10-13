package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    Button btnAdd;
    EditText etBuscar;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        btnAdd = findViewById(R.id.btnAddFood);
        etBuscar = findViewById(R.id.etBuscar);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddFoodActivity.class)));

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

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarLista(s.toString());
            }
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
        int[] to = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }

    private void filtrarLista(String texto) {
        Cursor filtrado = dbHelper.getAllFoods();
        adapter.changeCursor(filtrado);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}
