package com.example.greenscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainOrder extends AppCompatActivity implements PlantAdapterStore.OnItemClickListener {

    private RecyclerView recyclerView;
    private List<PlantStoreModel> plantStoreModelList;
    private List<PlantStoreModel> cart;
    private TextView totalAmountTextView;
    private double totalAmount;
    private PlantAdapterStore adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        totalAmountTextView = findViewById(R.id.totalAmountTextView);

        plantStoreModelList = getPlantList();
        cart = new ArrayList<>();

        adapter = new PlantAdapterStore(plantStoreModelList, this);
        recyclerView.setAdapter(adapter);

//        Button checkoutButton = findViewById(R.id.checkoutButton);
//        checkoutButton.setOnClickListener(v -> {
//            // Перехід до активіті оплати
//            Intent intent = new Intent(MainOrder.this, CheckoutActivity.class);
//            intent.putParcelableArrayListExtra("cart", new ArrayList<>(cart));
//            intent.putExtra("totalAmount", totalAmount);
//            startActivity(intent);
//        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sortByName) {
            sortByName();
            return true;
        } else if (id == R.id.sortByPrice) {
            sortByPrice();
            return true;
        } else if (id==R.id.backtomenu) {
            Intent intent = new Intent(MainOrder.this, MenuActivity.class);
            startActivity(intent);
            return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void sortByName() {
        Collections.sort(plantStoreModelList, new Comparator<PlantStoreModel>() {
            @Override
            public int compare(PlantStoreModel p1, PlantStoreModel p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void sortByPrice() {
        Collections.sort(plantStoreModelList, new Comparator<PlantStoreModel>() {
            @Override
            public int compare(PlantStoreModel p1, PlantStoreModel p2) {
                return Double.compare(p1.getPrice(), p2.getPrice());
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAddToCartClick(PlantStoreModel plantStoreModel) {
        if (!cart.contains(plantStoreModel)) {
            plantStoreModel.setQuantity(1);
            cart.add(plantStoreModel);
        } else {
            plantStoreModel.setQuantity(plantStoreModel.getQuantity() + 1);
        }
        updateTotalAmount();
    }

    @Override
    public void onRemoveFromCartClick(PlantStoreModel plantStoreModel) {
        if (cart.contains(plantStoreModel)) {
            if (plantStoreModel.getQuantity() > 1) {
                plantStoreModel.setQuantity(plantStoreModel.getQuantity() - 1);
            } else {
                cart.remove(plantStoreModel);
            }
            updateTotalAmount();
        }
    }

    private void updateTotalAmount() {
        totalAmount = 0;
        for (PlantStoreModel plantStoreModel : cart) {
            totalAmount += plantStoreModel.getPrice() * plantStoreModel.getQuantity();
        }
        totalAmountTextView.setText("Total Amount: " + totalAmount + " ₴");
    }

    private List<PlantStoreModel> getPlantList() {
        List<PlantStoreModel> plantStoreModels = new ArrayList<>();
        plantStoreModels.add(new PlantStoreModel("Стимулятор ", "Гуман Ультра ", 40.0, R.drawable.startup));
        plantStoreModels.add(new PlantStoreModel("Шовковиця", "  ", 200.0, R.drawable.black));
        plantStoreModels.add(new PlantStoreModel("Помідор", " ", 99.0, R.drawable.shtambovka));
        plantStoreModels.add(new PlantStoreModel("Туя звичайна", " ", 290.0, R.drawable.tuyaone));
        plantStoreModels.add(new PlantStoreModel("Туя трьох Річна ", "  ", 370.0, R.drawable.tuyatree));
        plantStoreModels.add(new PlantStoreModel("Кава Арабіка 15см", " ", 150.0, R.drawable.coffearabika));
        plantStoreModels.add(new PlantStoreModel("Роза", "", 60.0, R.drawable.rose));
        plantStoreModels.add(new PlantStoreModel("Тюльпан", " ", 40.0, R.drawable.tulip));
        plantStoreModels.add(new PlantStoreModel("ДОБРИВО ", "", 100.0, R.drawable.pidkormrouse));
        plantStoreModels.add(new PlantStoreModel("Удобрение ", " ", 40.0, R.drawable.universal));
        plantStoreModels.add(new PlantStoreModel("Огурці", "", 30.0, R.drawable.cacumber));
        plantStoreModels.add(new PlantStoreModel("Штучне дерево ", "", 2500.0, R.drawable.faketree));
        plantStoreModels.add(new PlantStoreModel("Сізіфус", "однорічне ", 340.0, R.drawable.sisifus));
        plantStoreModels.add(new PlantStoreModel("Горіх", " ", 140.0, R.drawable.nut));
        return plantStoreModels;
    }
}
