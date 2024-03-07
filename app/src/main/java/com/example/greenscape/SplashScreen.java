// Package declaration
package com.example.greenscape;

// Imports
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

// SplashScreen class definition
public class SplashScreen extends AppCompatActivity {

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the splash screen layout

        // Delayed execution to show the splash screen for 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity after 3 seconds
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                // Finish the SplashScreen activity
                finish();
            }
        }, 2000); // 3000 milliseconds (3 seconds)
    }
}
