package com.app.myapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Actividad principal de la aplicación. Muestra la lista de todos los alimentos
 * almacenados en la base de datos y proporciona la navegación a otras pantallas.
 */
public class MainActivity extends AppCompatActivity {
    // Helper para interactuar con la base de datos.
    DBHelper dbHelper;
    // Vista que muestra la lista de alimentos.
    ListView listView;
    // Adaptador personalizado para vincular los datos del cursor a la ListView.
    FoodCursorAdapter adapter;
    // Cursor que contiene los datos de los alimentos.
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura la Toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inicializa las vistas y el helper de la base de datos.
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        FloatingActionButton btnAddFood = findViewById(R.id.btnAddFood);

        // El botón flotante abre la pantalla para agregar un nuevo alimento.
        btnAddFood.setOnClickListener(v -> startActivity(new Intent(this, AddFoodActivity.class)));

        // La lógica de clics para actualizar y eliminar se gestiona en FoodCursorAdapter.
    }

    /**
     * Se llama cuando la actividad vuelve a ser visible. Se usa para recargar los datos
     * y refrescar la lista por si hubo cambios en otras actividades.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * Carga los datos de los alimentos desde la base de datos y los establece en el adaptador
     * de la ListView para mostrarlos.
     */
    private void loadData() {
        cursor = dbHelper.getAllFoods();
        adapter = new FoodCursorAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    /**
     * Se llama cuando la actividad se destruye. Cierra el cursor para liberar recursos.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
    }
}
