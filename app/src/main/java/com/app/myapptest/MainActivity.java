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

/**
 * Actividad principal que muestra una lista de alimentos.
 * Permite agregar, actualizar, eliminar y buscar alimentos.
 */
public class MainActivity extends AppCompatActivity {
    // Ayudante de la base de datos
    DBHelper dbHelper;
    // Vista de lista para mostrar los alimentos
    ListView listView;
    // Botón para agregar un nuevo alimento
    Button btnAdd;
    // Campo de texto para buscar alimentos
    EditText etBuscar;
    // Adaptador para la vista de lista
    SimpleCursorAdapter adapter;
    // Cursor para los datos de la base de datos
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización del ayudante de la base de datos y las vistas
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewFoods);
        btnAdd = findViewById(R.id.btnAddFood);
        etBuscar = findViewById(R.id.etBuscar);

        // Configurar el listener del botón para agregar un alimento, que abre AddFoodActivity
        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddFoodActivity.class)));

        // Configurar el listener de clic en un item de la lista para abrir UpdateFoodActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, UpdateFoodActivity.class);
            i.putExtra("food_id", id); // Pasa el ID del alimento a la actividad de actualización
            startActivity(i);
        });

        // Configurar el listener de clic largo en un item de la lista para abrir DeleteFoodActivity
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Intent i = new Intent(this, DeleteFoodActivity.class);
            i.putExtra("food_id", id); // Pasa el ID del alimento a la actividad de eliminación
            startActivity(i);
            return true; // Indica que el evento ha sido consumido
        });

        // Configurar un listener para el campo de búsqueda para filtrar la lista en tiempo real
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Llama al método para filtrar la lista basado en el texto introducido
                filtrarLista(s.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Carga los datos en la lista cada vez que la actividad se reanuda
        loadData();
    }

    /**
     * Carga los datos de la base de datos en el ListView.
     */
    private void loadData() {
        // Obtiene todos los alimentos de la base de datos
        cursor = dbHelper.getAllFoods();
        // Define las columnas a mostrar
        String[] from = {FoodContract.FoodEntry.COLUMN_NAME, FoodContract.FoodEntry.COLUMN_DESC};
        // Define los IDs de las vistas donde se mostrarán los datos
        int[] to = {android.R.id.text1, android.R.id.text2};
        // Crea un adaptador para el ListView
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        // Establece el adaptador en el ListView
        listView.setAdapter(adapter);
    }

    /**
     * Filtra la lista de alimentos.
     * Nota: La implementación actual vuelve a cargar todos los alimentos.
     * Para una funcionalidad de filtrado real, se debería modificar la consulta a la base de datos.
     * @param texto El texto para filtrar.
     */
    private void filtrarLista(String texto) {
        // En una implementación real, aquí se haría una consulta a la BD con el texto de búsqueda.
        // Por ejemplo: cursor = dbHelper.getFoodsByName(texto);
        Cursor filtrado = dbHelper.getAllFoods();
        // Actualiza el cursor del adaptador con los nuevos datos (filtrados)
        adapter.changeCursor(filtrado);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cierra el cursor cuando la actividad se destruye para liberar recursos
        if (cursor != null) cursor.close();
    }
}
