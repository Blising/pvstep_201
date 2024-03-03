package com.example.greenscape;

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

public class MenuActivity extends AppCompatActivity {
    private Button bUroom;
    private Button bSearchRecycle;
    private Button bAnswearCv;
    private Button btnExit;
    private Button btnStorage;
    private Button bCreateOrder;
    private SearchView searchView;


    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bUroom = findViewById(R.id.bUsersRoom);
        btnExit = findViewById(R.id.btnLogout);
        btnStorage = findViewById(R.id.btnStorage);
        bCreateOrder= findViewById(R.id.bCreateOrder);



        bSearchRecycle  = findViewById(R.id.searchView);
        bAnswearCv = findViewById(R.id.imageAi);
searchView = findViewById(R.id.searchForMeny);

        auth = FirebaseAuth.getInstance();

        btnExit.setOnClickListener(this::goToRegister);
        bUroom.setOnClickListener(this::goRoomusers);
        btnStorage.setOnClickListener(this::dowloadPhotoTithFireBase);
        bCreateOrder.setOnClickListener(this::MoveOrderByPlants);
        bSearchRecycle.setOnClickListener(this::GotoSearchList);

                bAnswearCv.setOnClickListener(this::GoToAnswearWithImage);
        searchView.setOnClickListener(this::ToastSearchView);



    }

    private void ToastSearchView(View view) {
        Toast.makeText(this, "It wiil be", Toast.LENGTH_SHORT).show();
    }

    private void GotoSearchList(View view) {
        Intent intent = new Intent(MenuActivity.this, RecycleSearchActivity.class);
        startActivity(intent);
    }

    private void GoToAnswearWithImage(View view) {
        Intent intent = new Intent(MenuActivity.this, ImageAnswear.class);
        startActivity(intent);
    }

    private void MoveOrderByPlants(View view) {
        Intent intent = new Intent(MenuActivity.this,OrderInfoUser.class);
        startActivity(intent);
    }

    private void dowloadPhotoTithFireBase(View view) {
        Intent intent = new Intent(MenuActivity.this, UploadImage.class);
        startActivity(intent);
    }

    private void goToRegister(View view) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
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
        }else {
            Toast.makeText(this, "You need Autificanion", Toast.LENGTH_SHORT).show();
        }
    }
}
