package com.example.greenscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MenuSecondRows extends AppCompatActivity {
    private Button btnNext;

    private Button btnExit;

    private Button btnDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_second_rows);

        btnNext =  findViewById(R.id.btnNext);
        btnExit = findViewById(R.id.btnLogout);
        btnDb = findViewById(R.id.imageView1);


        btnNext.setOnClickListener(this::goTOSeconMeny);
        btnExit.setOnClickListener(this::goToRegister);
        btnDb.setOnClickListener(this::gotoDbPage);

    }

    private void gotoDbPage(View view) {
        Intent intent = new Intent(MenuSecondRows.this, DbFirebaseMain.class);
        startActivity(intent);

    }

    private void goToRegister(View view) {
        Intent intent = new Intent(MenuSecondRows.this, MainActivity.class);
        startActivity(intent);
    }
    private void goTOSeconMeny(View view) {
        Toast.makeText(this, "Move", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuSecondRows.this, MenuActivity.class);
        startActivity(intent);

    }
}