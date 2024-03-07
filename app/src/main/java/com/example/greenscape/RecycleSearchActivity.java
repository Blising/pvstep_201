package com.example.greenscape;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

public class RecycleSearchActivity extends AppCompatActivity {
    SearchView searchView;


    ListView listView;
    String[] nameList = {
            "Рожева троянда", "Квітка сонця", "Орхідея фаленопсис", "Лілія", "Хризантема",
            "Папороть домашня", "Жасмин", "Алое вера", "Мак", "Георгін",
            "Паприка", "Огірок", "Помідор", "Буряк", "Картопля",
            "Цибуля", "Часник", "Морква", "Бавовна", "Лля",
            "Кукурудза", "Пшениця", "Рис", "Індіго", "Чорниця",
            "Лаванда", "Базилік", "М'ята", "Ромашка", "Шавлія",
            "Кактус", "Суниця", "Брусниця", "Гречка", "Горох",
            "Багатоцвітна квітка", "Маргаритка", "Фіалка", "Тюльпан", "Анютино сонечко",
            "Медовіць", "Кукурудзяна качка", "Гіацинт", "Різандр", "Каркас",
            "Амаріліс", "Жовтий нарцис", "Тюльпан", "Астра", "Верба",
            "Дерево життя", "Аґава", "Коралове дерево", "Айва", "Авокадо",
            "Гранат", "Мангостан", "Ананас", "Карамбола", "Маракуйя",
            "Банан", "Лимон", "Апельсин", "Лайм", "Грейпфрут",
            "Мандарин", "Кокос", "Ківі", "Хурма", "Яблуко",
            "Груша", "Слива", "Персик", "Абрикос", "Вишня",
            "Чорниця", "Журавлина", "Черешня", "Ожина", "Малина",
            "Смородина", "Шипшина", "Глід", "Смерека", "Ялиця",
            "Сосна", "Бук", "Дуб", "Граб", "Липа",
            "Ясен", "Клен", "Верба", "Верболоз", "Папороть",
            "Мох", "Кукурудза", "Осока", "Водяниця", "Лоза"
    };
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_recycle_search); // Set the layout file for this activity


        // Initialize UI components
        searchView = findViewById(R.id.search_B);
        listView = findViewById(R.id.list_itemB);


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        // Set the adapter for the ListView
        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // Display a toast message when an item is clicked
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RecycleSearchActivity.this, "Your Click - " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Set query text listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Filter the list based on the query text when submit button is pressed
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecycleSearchActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }
            // Filter the list based on the query text as it changes
            @Override
            public boolean onQueryTextChange(String newText) {
                RecycleSearchActivity.this.arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }
}