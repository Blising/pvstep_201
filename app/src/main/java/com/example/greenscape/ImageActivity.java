package com.example.greenscape;

import android.os.Bundle;

import androidx.activity.EdgeToEdge; //  Імпорт бібліотеки EdgeToEdge для підтримки відображення від краю до краю  Importing the EdgeToEdge library for edge-to-edge display support
import androidx.appcompat.app.AppCompatActivity; // Імпорт класу AppCompatActivity з пакету androidx.appcompat Importing the AppCompatActivity class from the androidx.appcompat package


import androidx.annotation.NonNull; // Імпорт анотації NonNull з пакету androidx.annotation Importing the NonNull annotation from the androidx.annotation package

import androidx.recyclerview.widget.LinearLayoutManager; // Імпорт класу LinearLayoutManager з пакету androidx.recyclerview.widgetImporting the LinearLayoutManager class from the androidx.recyclerview.widget package
import androidx.recyclerview.widget.RecyclerView; //Імпорт класу RecyclerView з пакету androidx.recyclerview.widget  Importing the RecyclerView class from the androidx.recyclerview.widget package

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    // Оголошення всіх змінних елементів
    private RecyclerView mRecycleView; // Declaring RecyclerView variable
    private ImageAdapter mAdapter; // Declaring ImageAdapter variable
    private DatabaseReference mDatabaseRef; // Declaring DatabaseReference variable
    private List<Upload> mUploads; // Declaring List variable to hold Upload objects
    private ProgressBar mprogressCircle; // Declaring ProgressBar variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enabling edge-to-edge display
        setContentView(R.layout.activity_image); // Setting the layout file for this activity

        // Initializing RecyclerView and ProgressBar
        mRecycleView = findViewById(R.id.recycler_view); // Finding RecyclerView from the layout file
        mprogressCircle = findViewById(R.id.progress_circle); // Finding ProgressBar from the layout file
        // Встановлення властивостей RecyclerView
        // Setting RecyclerView properties
        mRecycleView.setHasFixedSize(true); // Setting fixed size for RecyclerView
        mRecycleView.setLayoutManager(new LinearLayoutManager(this)); // Setting LinearLayoutManager for RecyclerView
        mUploads = new ArrayList<>(); // Initializing the list to hold Upload objects
        // Отримання посилання на вузол "uploads" у базі даних Firebase
        // Getting reference to the "uploads" node in Firebase database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        // Додавання ValueEventListener для отримання даних з бази даних Firebase
        // Adding a ValueEventListener to fetch data from Firebase database


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Executed when data changes in the database
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // Iterating through each child node under "uploads" node
                    Upload upload = postSnapshot.getValue(Upload.class); // Retrieving data and converting it to Upload object
                    mUploads.add(upload); // Adding the Upload object to the list
                }
                // Створення та встановлення адаптера для RecyclerView
                // Creating and setting the adapter for RecyclerView
                mAdapter = new ImageAdapter(ImageActivity.this, mUploads);
                mRecycleView.setAdapter(mAdapter);
                mprogressCircle.setVisibility(View.INVISIBLE); // Making the ProgressBar invisible once data is loaded
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Executed if data retrieval is cancelled or fails
                // Виконується, якщо скасовано або не вдалося отримати дані
                Toast.makeText(ImageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show(); // Displaying error message
                mprogressCircle.setVisibility(View.INVISIBLE); // Making the ProgressBar invisible
            }
        });
    }
}
