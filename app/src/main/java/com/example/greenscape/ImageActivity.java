package com.example.greenscape;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    private RecyclerView mRecycleView;
    private  ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    private ProgressBar mprogressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image);


        mRecycleView = findViewById(R.id.recycler_view);
        mprogressCircle = findViewById(R.id.progress_circle);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mUploads = new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(ImageActivity.this, mUploads);
                mRecycleView.setAdapter(mAdapter);
                mprogressCircle.setVisibility(View.INVISIBLE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImageActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mprogressCircle.setVisibility(View.INVISIBLE);
            }
        });



    }
}