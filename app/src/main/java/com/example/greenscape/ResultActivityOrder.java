// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
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

    // Method to navigate to the main menu
    private void goMenu(View view) {
        Intent intent = new Intent(ResultActivityOrder.this, MenuActivity.class);
        startActivity(intent);
    }
}
