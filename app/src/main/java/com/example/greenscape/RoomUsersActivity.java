// Package declaration
package com.example.greenscape;

// Imports
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

// RoomUsersActivity class definition
public class RoomUsersActivity extends AppCompatActivity {
    // UI components
    private Button btn;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_users); // Set the layout file for this activity
        btn = findViewById(R.id.btnExit); // Initialize the button

        btn.setOnClickListener(this::gomeny); // Set click listener for the button

        // Get data from intent
        String photoUrl = getIntent().getStringExtra("photoUrl");
        String displayName = getIntent().getStringExtra("displayName");
        String email = getIntent().getStringExtra("email");

        // Find views from layout
        ImageView profileImage = findViewById(R.id.profileImage);
        TextView displayNameTextView = findViewById(R.id.displayNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);

        // Set data to the corresponding views
        Glide.with(this).load(photoUrl).into(profileImage); // Load image using Glide library
        displayNameTextView.setText(displayName); // Set display name
        emailTextView.setText(email); // Set email
    }

    // Method to navigate to the main menu
    private void gomeny(View view) {
        Intent intent = new Intent(RoomUsersActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
