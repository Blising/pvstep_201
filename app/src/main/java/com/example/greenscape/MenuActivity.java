package com.example.greenscape;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private Button bUroom;
    private Button bSearchRecycle;
    private Button bAnswearCv;
    private Button btnscanNet;
    private Button btnStorage;
    private Button bCreateOrder;
    private Button btnListPlant;
    private ImageView btnListCalendar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bUroom = findViewById(R.id.bUsersRoom);
        btnscanNet = findViewById(R.id.btnScanNet);
        btnStorage = findViewById(R.id.btnStorage);
        bCreateOrder = findViewById(R.id.bCreateOrder);
        bSearchRecycle = findViewById(R.id.searchView);
        bAnswearCv = findViewById(R.id.imageAi);
        btnListCalendar = findViewById(R.id.btnListCalendar);
        btnListPlant = findViewById(R.id.btnListPlants);

        auth = FirebaseAuth.getInstance();

        bUroom.setOnClickListener(this::goRoomusers);
        btnStorage.setOnClickListener(this::dowloadPhotoTithFireBase);
        bCreateOrder.setOnClickListener(this::MoveOrderByPlants);
        bSearchRecycle.setOnClickListener(this::GotoSearchList);
        bAnswearCv.setOnClickListener(this::GoToAnswearWithImage);
        btnListPlant.setOnClickListener(this::GotoListPlants);
        btnscanNet.setOnClickListener(this::goToScanNEt);
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
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.accaunt) {
            Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
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
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSdvAW0XnEdiNpIDsrkELNinQ-xYLtNapgfl3bZrnSFsZgnbaQ/viewform?usp=sf_link"));
            if (browserIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(browserIntent);
            } else {
                Toast.makeText(this, "No application can handle this request", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Toast.makeText(this, "Logout: " + email, Toast.LENGTH_SHORT).show();

            FirebaseAuth.getInstance().signOut();

            // Видалення стану входу
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            Intent intent = new Intent(MenuActivity.this, MainActivity.class);
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

    private void GotoListPlants(View view) {
        Toast.makeText(this, "Library", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuActivity.this, RecycleSearchActivity.class);
        startActivity(intent);
    }

    private void GotoSearchList(View view) {
        Intent intent = new Intent(MenuActivity.this, DbFirebaseMain.class);
        startActivity(intent);
    }

    private void GoToAnswearWithImage(View view) {
        Intent intent = new Intent(MenuActivity.this, AcitiviTestPlantnet.class);
        startActivity(intent);
    }

    private void MoveOrderByPlants(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSezjUnP8PlxQP-IXa7_a-1tX91THrGB9EWW5BLodpIlD9rCEA/viewform?usp=sf_link"));
        startActivity(intent);
    }

    private void dowloadPhotoTithFireBase(View view) {
        Intent intent = new Intent(MenuActivity.this, TestSqllite.class);
        startActivity(intent);
    }

    private void goToScanNEt(View view) {
        Intent intent = new Intent(MenuActivity.this, AcitiviTestPlantnet.class);
        startActivity(intent);
    }

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
