// Package declaration
package com.example.greenscape;

// Imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

// RoomUsersActivity class definition
public class RoomUsersActivity extends AppCompatActivity {
    // UI components


    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_users); // Set the layout file for this activity


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prevmeny,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.backtomenu) {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoomUsersActivity.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(RoomUsersActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
