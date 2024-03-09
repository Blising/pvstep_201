package com.example.greenscape;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFromDb extends AppCompatActivity {
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_from_db);


        textView = findViewById(R.id.txDbInfoEmployee);
        NetworkService
                .getInstance()
                .getRestApi()
                .getAllEmployees()
                .enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        textView.setText(response.body().get(0).getLastName());

                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        textView.setText(t.getMessage());
                    }
                });

    }
}