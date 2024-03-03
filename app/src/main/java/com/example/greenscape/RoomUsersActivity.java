package com.example.greenscape;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RoomUsersActivity extends AppCompatActivity {
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_users);
        btn = findViewById(R.id.btnExit);


        btn.setOnClickListener(this::gomeny);
        // Отримати дані з інтенту
        String photoUrl = getIntent().getStringExtra("photoUrl");
        String displayName = getIntent().getStringExtra("displayName");
        String email = getIntent().getStringExtra("email");

        // Знайти елементи макету
        ImageView profileImage = findViewById(R.id.profileImage);
        TextView displayNameTextView = findViewById(R.id.displayNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);

        // Встановити дані у відповідні елементи макету
        Glide.with(this).load(photoUrl).into(profileImage);
        displayNameTextView.setText(displayName);
        emailTextView.setText(email);
    }

    private void gomeny(View view) {
        Intent intent = new Intent(RoomUsersActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
