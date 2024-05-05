package com.example.greenscape;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.adapters.PlantsAdapter;
import com.example.greenscape.entity.Plants;
import com.example.greenscape.service.NetworkService;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.adapters.PlantsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestPlants extends AppCompatActivity {


    private TextView tvTextVie;
    private RecyclerView recycleviewMainActivity;
    private PlantsAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_plants);
        tvTextVie = findViewById(R.id.tvTextVie);
        recycleviewMainActivity  = findViewById(R.id.recycleviewMainActivity);
        recycleviewMainActivity.setLayoutManager(new LinearLayoutManager(this));
        getAllCars();
        NetworkService.getInstance().getRestApi().getAllPlant().enqueue(new Callback<List<Plants>>() {

            @Override
            public void onResponse(Call<List<Plants>> call, Response<List<Plants>> response) {
                tvTextVie.setText(response.body().get(1).getName());
            }

            @Override
            public void onFailure(Call<List<Plants>> call, Throwable t) {
                Toast.makeText(TestPlants.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getAllCars() {

        NetworkService.getInstance().getRestApi().getAllPlant().enqueue(new Callback<List<Plants>>() {

            @Override
            public void onResponse(Call<List<Plants>> call, Response<List<Plants>> response) {

                if(response.isSuccessful() &&response.body() != null){
                    List<Plants> plants=response.body();
                    carAdapter = new PlantsAdapter(plants);
                    recycleviewMainActivity.setAdapter(carAdapter);
                }else {
                    Toast.makeText(TestPlants.this, "Failed to get cars", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Plants>> call, Throwable t) {
                Toast.makeText(TestPlants.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}