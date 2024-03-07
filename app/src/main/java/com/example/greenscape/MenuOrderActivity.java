// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// MenuOrderActivity class definition
public class MenuOrderActivity extends AppCompatActivity {
    // UI components
    private TextView takeinfo;
    private Order order;
    private CheckBox firstchecbox;
    private CheckBox secondchecbox;
    private CheckBox thirthchecbox;
    private Button btnnextActivity;
    private RadioGroup groupRadiogroup;
    private RadioGroup groupRad;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_menu_order); // Set the layout file for this activity

        // Initialize UI components
        takeinfo = findViewById(R.id.evMenu);
        btnnextActivity = findViewById(R.id.btnNextResultActivity);
        firstchecbox = findViewById(R.id.firstChecbox);
        secondchecbox = findViewById(R.id.secondChecbox);
        thirthchecbox = findViewById(R.id.thirdChecbox);
        groupRadiogroup = findViewById(R.id.groupRadiogroup);
        groupRad = findViewById(R.id.rbnew);

        // Get order details from previous activity
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra(OrderInfoUser.KEYORDER);
        takeinfo.setText("Name: " + order.getName() + ", Numbers: " + order.getNumbers());

        // Set click listeners for buttons and radio groups
        btnnextActivity.setOnClickListener(this::send);
        groupRadiogroup.setOnCheckedChangeListener(this::AddSize);
        groupRad.setOnCheckedChangeListener(this::AddElseSomething);
    }

    // Method to handle radio group selection for delivery option
    private void AddElseSomething(RadioGroup radioGroup, int id) {
        String poshta = null;
        if (id == R.id.radioButton7) {
            poshta = "Novaposhta";
        } else if (id == R.id.radioButton8) {
            poshta = "Ukrposhta";
        } else if (id == R.id.radioButton9) {
            poshta = "Delivery";
        }
        order.setPoshta(poshta);
        Toast.makeText(this, poshta, Toast.LENGTH_SHORT).show();
    }

    // Method to handle radio group selection for  size
    private void AddSize(RadioGroup radioGroup, int id) {
        String size = null;
        if (id == R.id.firstRadioButton) {
            size = "Little";
        } else if (id == R.id.secondRadiobatton) {
            size = "Middle";
        } else if (id == R.id.thirthRadiobatton) {
            size = "BIG";
        }
        order.setSize(size);
        Toast.makeText(this, size, Toast.LENGTH_SHORT).show();
    }

    // Method to handle order submission
    private void send(View view) {
        String result = "";
        order.getIngredients().clear();
        if (firstchecbox.isChecked()) {
            result += "AmiacMick";
            order.getIngredients().add("AmiacMick");
        }
        if (secondchecbox.isChecked()) {
            result += "shavild";
            order.getIngredients().add("shavild");
        }
        if (thirthchecbox.isChecked()) {
            result += " MaksimFRS";
            order.getIngredients().add("MaksimFRS");
        }
        takeinfo.setText(result);

        Toast.makeText(this, order.toString(), Toast.LENGTH_SHORT).show();

        // Navigate to ResultActivityOrder and pass the order details
        Intent intent = new Intent(this, ResultActivityOrder.class);
        intent.putExtra(OrderInfoUser.KEYORDER, order);
        startActivity(intent);
    }
}
