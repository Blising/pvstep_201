// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// MenuActivity class definition
public class MenuActivity extends AppCompatActivity {
    // UI components
    private Button bUroom;
    private Button bSearchRecycle;
    private Button bAnswearCv;
    private Button btnExit;
    private Button btnStorage;
    private Button bCreateOrder;
    private Button btnDb;
    private SearchView searchView;

    // Firebase authentication
    private FirebaseAuth auth;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Set the layout file for this activity

        // Initialize UI components
        bUroom = findViewById(R.id.bUsersRoom);
        btnExit = findViewById(R.id.btnLogout);
        btnStorage = findViewById(R.id.btnStorage);
        bCreateOrder= findViewById(R.id.bCreateOrder);
        bSearchRecycle  = findViewById(R.id.searchView);
        bAnswearCv = findViewById(R.id.imageAi);
        searchView = findViewById(R.id.searchForMeny);
        btnDb = findViewById(R.id.btnSomfingElse);


        // Initialize Firebase authentication
        auth = FirebaseAuth.getInstance();

        // Set click listeners for buttons
        btnExit.setOnClickListener(this::goToRegister);
        bUroom.setOnClickListener(this::goRoomusers);
        btnStorage.setOnClickListener(this::dowloadPhotoTithFireBase);
        bCreateOrder.setOnClickListener(this::MoveOrderByPlants);
        bSearchRecycle.setOnClickListener(this::GotoSearchList);
        bAnswearCv.setOnClickListener(this::GoToAnswearWithImage);
        searchView.setOnClickListener(this::ToastSearchView);
        btnDb.setOnClickListener(this::GotoDbInfo);
    }

    private void GotoDbInfo(View view) {
        Toast.makeText(this, "Go to Db Info", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this, InfoFromDb.class);
        startActivity(intent);
    }

    // Method to display a toast message when the search view is clicked
    private void ToastSearchView(View view) {
        Toast.makeText(this, "It will be", Toast.LENGTH_SHORT).show();
    }

    // Method to navigate to RecycleSearchActivity
    private void GotoSearchList(View view) {
        Intent intent = new Intent(MenuActivity.this, RecycleSearchActivity.class);
        startActivity(intent);
    }

    // Method to navigate to ImageAnswear activity
    private void GoToAnswearWithImage(View view) {
        Intent intent = new Intent(MenuActivity.this, ImageAnswear.class);
        startActivity(intent);
    }

    // Method to navigate to OrderInfoUser activity
    private void MoveOrderByPlants(View view) {
        Intent intent = new Intent(MenuActivity.this, OrderInfoUser.class);
        startActivity(intent);
    }

    // Method to navigate to UploadImage activity
    private void dowloadPhotoTithFireBase(View view) {
        Intent intent = new Intent(MenuActivity.this, UploadImage.class);
        startActivity(intent);
    }

    // Method to navigate to MainActivity
    private void goToRegister(View view) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // Method to navigate to RoomUsersActivity if user is authenticated, otherwise display a toast message
    private void goRoomusers(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String photoUrl = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";
            String displayName = user.getDisplayName() != null ? user.getDisplayName() : "";
            String email = user.getEmail() != null ? user.getEmail() : "";

            Intent intent = new Intent(MenuActivity.this, RoomUsersActivity.class);
            intent.putExtra("photoUrl", photoUrl);
            intent.putExtra("displayName", displayName);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You need Authentication", Toast.LENGTH_SHORT).show();
        }
    }
}
