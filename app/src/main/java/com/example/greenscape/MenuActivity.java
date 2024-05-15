// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private  Button btnLogoutFireBase;

    private Button bCreateOrder;
    private Button btnDb;
    private ImageView IWcalendar;

    // Firebase authentication
    private FirebaseAuth auth;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Set the layout file for this activity

        btnLogoutFireBase = findViewById(R.id.btnLogoutFireBase);

        // Initialize UI components
        bUroom = findViewById(R.id.bUsersRoom);
        btnExit = findViewById(R.id.btnLogout);
        btnStorage = findViewById(R.id.btnStorage);
        bCreateOrder= findViewById(R.id.bCreateOrder);
        bSearchRecycle  = findViewById(R.id.searchView);
        bAnswearCv = findViewById(R.id.imageAi);

        btnDb = findViewById(R.id.btnSomfingElse);

        btnExit = findViewById(R.id.btnLogout);
        IWcalendar =findViewById(R.id.IWcalendar);


        // Initialize Firebase authentication
        auth = FirebaseAuth.getInstance();


        bUroom.setOnClickListener(this::goRoomusers);
        btnStorage.setOnClickListener(this::dowloadPhotoTithFireBase);
        bCreateOrder.setOnClickListener(this::MoveOrderByPlants);
        bSearchRecycle.setOnClickListener(this::GotoSearchList);
        bAnswearCv.setOnClickListener(this::GoToAnswearWithImage);

        btnDb.setOnClickListener(this::GotoLibrary);



        btnExit.setOnClickListener(this::goToRegister);
        btnLogoutFireBase.setOnClickListener(this::logoutFirebase);
        IWcalendar.setOnClickListener(this::gotoCalendarActivity);
    }

    private void gotoCalendarActivity(View view) {
        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        Toast.makeText(this,"Calendar: "+displayName,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this,CalendatActivity.class);
        startActivity(intent);
    }

    private void logoutFirebase(View view) {
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


        Toast.makeText(this,"Logout: "+ email , Toast.LENGTH_SHORT).show();

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MenuActivity.this,MainActivity.class);
        startActivity(intent);
    }


    private void GotoLibrary(View view) {
        Toast.makeText(this, "Library", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this,RecycleSearchActivity.class);
        startActivity(intent);
    }

    // Method to display a toast message when the search view is clicked


    // Method to navigate to RecycleSearchActivity
    private void GotoSearchList(View view) {
        Intent intent = new Intent(MenuActivity.this, DbFirebaseMain.class);
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
        Intent intent = new Intent(MenuActivity.this, ImageAnswear.class);
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
            Toast.makeText(this, "Something wrong Authentication: ", Toast.LENGTH_SHORT).show();
        }
    }
}
