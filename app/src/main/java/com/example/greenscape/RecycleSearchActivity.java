package com.example.greenscape;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.HashMap;

public class RecycleSearchActivity extends AppCompatActivity {
    SearchView searchView;

    ListView listView;
    String[] nameList = {
            "Рожева троянда", "Квітка сонця", "Орхідея фаленопсис", "Лілія", "Хризантема",
            "Папороть домашня", "Жасмин", "Алое вера", "Мак", "Георгін",
            "Паприка", "Огірок", "Помідор", "Буряк", "Картопля",
            "Цибуля", "Часник", "Морква",
            "Кукурудза", "Пшениця", "Рис", "Сізіфус", "Чорниця",
            "Лаванда", "Базилік", "М'ята", "Ромашка", "Шавлія"
    };

    HashMap<String, String> nameUrlMap = new HashMap<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_recycle_search); // Set the layout file for this activity

        // Initialize UI components
        searchView = findViewById(R.id.search_B);
        listView = findViewById(R.id.list_itemB);

        // Populate the map with URLs
        populateUrlMap();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        // Set the adapter for the ListView
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // Handle item clicks
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String itemName = nameList[i];
                String itemUrl = nameUrlMap.get(itemName);
                if (itemUrl != null && !itemUrl.isEmpty()) {
                    // Open URL in a browser
                    openUrlInBrowser(itemUrl);
                } else {
                    Toast.makeText(RecycleSearchActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set query text listener for the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Filter the list based on the query text when submit button is pressed
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(query);
                return false;
            }

            // Filter the list based on the query text as it changes
            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    // Method to populate the map with URLs
    private void populateUrlMap() {

        nameUrlMap.put("Рожева троянда", "https://elsa.com.ua/all-news/kak-vyrastit-kustovuyu-rozu-v-gorshke-pravilnyj-uhod-v-domashnih-usloviyah/");
        nameUrlMap.put("Квітка сонця", "https://agroelita.info/kvitka-sontsya-abo-istoriya-rozvytku-sonyashnyku/");
        nameUrlMap.put("Орхідея фаленопсис", "https://rivne1.tv/news/53407-viroshchuvannya-orkhidei-v-domashnikh-umovakh");
        nameUrlMap.put("Лілія", "https://agro-market.net/ua/news/gardening/how_to_grow_tender_and_touching_lilies/");
        nameUrlMap.put("Хризантема", "https://agro-market.net/ua/news/gardening/how_to_grow_tender_and_touching_lilies/");
        nameUrlMap.put("Папороть домашня", "https://ukr.media/garden/384135/");
        nameUrlMap.put("Жасмин", "https://soncesad.com/statti/dekorativni/zhasmin/yak-viroshhuvati-zhasmin-vdoma.html");
        nameUrlMap.put("Алое вера", "https://example.com/sunflowers");
        nameUrlMap.put("Мак", "https://zaxid.net/aloe_viroshhuvannya_doglyad_v_domashnih_umovah_pravila_n1546500");
        nameUrlMap.put("Георгін", "https://yaskravaklumba.com.ua/ua/stati-i-video/lukovichnye-i-mnogoletniki/vyrashchivaniia-georgin-posadka-i-uhod-ispolzovanie-v-landshaftnom-dizaine");
        nameUrlMap.put("Паприка", "https://ukr.media/garden/409255/");
        nameUrlMap.put("Огірок", "https://good-harvest.ua/tips/0001/");
        nameUrlMap.put("Помідор", "https://eos.com/uk/blog/vyroshchuvannia-tomativ/");
        nameUrlMap.put("Буряк", "https://agro-business.com.ua/agro/ahronomiia-sohodni/item/16497-osoblyvosti-vyroshchuvannia-ta-dohliadu-za-buriakom-stolovym.html");
        nameUrlMap.put("Картопля", "https://superagronom.com/articles/349-drugiy-hlib-tehnologiya-viroschuvannya-kartopli-vid-a-do-ya");
        nameUrlMap.put("Цубуля", "https://svitroslyn.ua/ua/articles/sekrety-uspeshnogo-vyrashchivaniya-luka.html");
        nameUrlMap.put("Часник", "https://kurkul.com/blog/520-sekreti-viroschuvannya-chasniku-poradi-pochatkivtsyam");
        nameUrlMap.put("Морква", "https://zelenasadyba.com.ua/sad-i-gorod/morkva-pravyla-vyroshhuvannya-vid-posivu-do-vrozhayu.html");

        nameUrlMap.put("Кукурудза", "https://superagronom.com/articles/367-viroschuvannya-kukurudzi-povna-tehnologiya");
        nameUrlMap.put("Пшениця", "https://eos.com/uk/blog/vyroshchuvannia-pshenytsi/");
        nameUrlMap.put("Рис", "https://www.epravda.com.ua/publications/2021/10/22/678949/");
        nameUrlMap.put("Сізіфус", "https://ukr.media/garden/391345/");
        nameUrlMap.put("чорниця", "https://semena.in.ua/koli-saditi-chornitsu/");
        nameUrlMap.put("Лаванда", "https://zaxid.net/lavanda_yak_saditi_doglyadati_ideyi_praktichni_poradi_foto_n1519211");
        nameUrlMap.put("Базилік", "https://www.unian.ua/lite/advice/yak-posiyati-bazilik-koli-i-yak-saditi-nasinnya-na-rozsadu-12167829.html");
        nameUrlMap.put("М'ята", "https://agro-market.net/ua/news/tips_and_advice/vyrashchivanie_myaty/");
        nameUrlMap.put("Ромашка", "https://yaskravaklumba.com.ua/ua/stati-i-video/lukovichnye-i-mnogoletniki/romashka-mnogoletniaia-sadovaia-vsegda-sovremennaia-uiutnaia-klassika-i-miagkii-derevenskii-stil-v-dizaine-sada");
        nameUrlMap.put("Шавлія", "https://agro-market.net/ua/news/mnogoletnie%20tsvetyi/vyrashchivanie_shalfeya/");

        // Add URLs for other plants
    }

    // Method to open URL in a browser
    private void openUrlInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
