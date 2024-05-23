// Package declaration
package com.example.greenscape;

// Imports
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    private ImageView btnListCalendar;


    // Firebase authentication
    private FirebaseAuth auth;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Set the layout file for this activity

        // Initialize UI components
        bUroom = findViewById(R.id.bUsersRoom);
        btnExit = findViewById(R.id.btnLogout);
        btnStorage = findViewById(R.id.btnStorage);
        bCreateOrder= findViewById(R.id.bCreateOrder);
        bSearchRecycle  = findViewById(R.id.searchView);
        bAnswearCv = findViewById(R.id.imageAi);
        btnListCalendar = findViewById(R.id.btnListCalendar);

        btnDb = findViewById(R.id.btnSomfingElse);

        btnExit = findViewById(R.id.btnLogout);



        // Initialize Firebase authentication
        auth = FirebaseAuth.getInstance();


        bUroom.setOnClickListener(this::goRoomusers);

        btnStorage.setOnClickListener(this::dowloadPhotoTithFireBase);
        bCreateOrder.setOnClickListener(this::MoveOrderByPlants);
        bSearchRecycle.setOnClickListener(this::GotoSearchList);
        bAnswearCv.setOnClickListener(this::GoToAnswearWithImage);

        btnDb.setOnClickListener(this::GotoLibrary);



        btnExit.setOnClickListener(this::goToRegister);

        btnListCalendar.setOnClickListener(this::gotoListCalendar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.accaunt) {
            Toast.makeText(this, "accaunt", Toast.LENGTH_SHORT).show();
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

            return true;
        } else if (item.getItemId() == R.id.error) {
            // Створіть об'єкт Intent для відкриття браузера з вказаною URL-адресою
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdvAW0XnEdiNpIDsrkELNinQ-xYLtNapgfl3bZrnSFsZgnbaQ/viewform?usp=sf_link"));
            // Перевірте, чи існує додаток, який може обробити цей Intent
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                // Запустіть Intent, якщо є додаток, який може обробити його
                startActivity(browserIntent);
            } else {
                // Якщо немає додатків, які можуть обробити цей Intent, виведіть повідомлення про помилку
                Toast.makeText(this, "No application can handle this request", Toast.LENGTH_SHORT).show();
            }
            return true;

    } else if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();


            Toast.makeText(this,"Logout: "+ email , Toast.LENGTH_SHORT).show();

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MenuActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.calendar) {
            Intent intent = new Intent(MenuActivity.this, Calen.class);
            startActivity(intent);


            return true;

        }



        return super.onOptionsItemSelected(item);
    }


    private void gotoListCalendar(View view) {
        Intent intent = new Intent(MenuActivity.this, SecondActivity.class);
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
