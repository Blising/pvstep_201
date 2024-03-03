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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycle_search);
        searchView = findViewById(R.id.search_B);
        listView = findViewById(R.id.list_itemB);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(RecycleSearchActivity.this, "Your Click - " + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RecycleSearchActivity.this.arrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RecycleSearchActivity.this.arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }
}