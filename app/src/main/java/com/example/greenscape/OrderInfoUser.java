// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

// OrderInfoUser class definition
public class OrderInfoUser extends AppCompatActivity {
    // Constant for passing order details to the next activity
    public static final String KEYORDER = "keyorder";

    // UI components
    private Button btSave;
    private TextInputEditText inputName;
    private EditText inpNumber;

    // Order object to hold user information
    private Order order;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge display
        setContentView(R.layout.activity_order_info_user); // Set the layout file for this activity

        // Initialize UI components
        inputName = findViewById(R.id.textInputName);
        inpNumber = findViewById(R.id.etNumber);
        btSave = findViewById(R.id.btnSave);

        // Add text change listener to input fields
        inputName.addTextChangedListener(textWatcher);
        inpNumber.addTextChangedListener(textWatcher);

        // Initialize Order object
        order = new Order();

        // Set click listener for the save button
        btSave.setOnClickListener(v -> {
            // Check if fields are not empty before proceeding
            if (isFieldsNotEmpty()) {
                // Create intent to navigate to MenuOrderActivity
                Intent intent = new Intent(this, MenuOrderActivity.class);
                // Get user input data
                String message = inputName.getText().toString();
                String numberText = inpNumber.getText().toString();
                int numbers = Integer.parseInt(numberText);

                // Set order details
                order.setName(message);
                order.setNumbers(numbers);

                // Pass order object to the next activity
                intent.putExtra(KEYORDER, order);
                startActivity(intent);
            }
        });
    }

    // TextWatcher to enable/disable save button based on input field status
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            btSave.setEnabled(isFieldsNotEmpty());
        }
    };

    // Method to check if input fields are not empty
    private boolean isFieldsNotEmpty() {
        return inputName.getText().toString().trim().length() != 0
                && inpNumber.getText().toString().trim().length() != 0;
    }
}
