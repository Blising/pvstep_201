// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// ResultActivityOrder class definition
public class ResultActivityOrder extends AppCompatActivity {
    // UI components
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private Button button;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_result_order); // Set the layout file for this activity

        // Initialize UI components
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        button = findViewById(R.id.btnExitMainMenu);

        // Get order details from the intent
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(OrderInfoUser.KEYORDER);

        // Set text for TextViews
        tv1.setText("Numbers: " + order.getNumbers());
        tv2.setText("Name: " + order.getName());
        tv3.setText("Poshta: " + order.getPoshta());

        // Set click listener for the button
        button.setOnClickListener(this::goMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prevmeny,menu);
        return  true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.backtomenu) {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ResultActivityOrder.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(ResultActivityOrder.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    // Method to navigate to the main menu
    private void goMenu(View view) {
        Intent intent = new Intent(ResultActivityOrder.this, MenuActivity.class);
        startActivity(intent);
    }
}
