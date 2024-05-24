package com.example.greenscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class AddActivity extends AppCompatActivity {
EditText name , course,email,turl;
Button btnAdd , btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        name = (EditText) findViewById(R.id.txtName);
        course = (EditText) findViewById(R.id.txtCourse);
        email = (EditText) findViewById(R.id.txtEmail);
        turl = (EditText) findViewById(R.id.txtImageUrl);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);

btnAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        insertData();
        clearAll();

    }
});

btnBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
    }
});

    }
    private  void insertData(){
        Map<String, Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("course",course.getText().toString());
        map.put("turl",turl.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("teacher").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Data Inseared succsessful",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this,"Error while Insertion",Toast.LENGTH_SHORT).show();


                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prevmeny,menu);
        return  true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.backtomenu) {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddActivity.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private  void clearAll(){
        name.setText("");
        course.setText("");
        email.setText("");
        turl.setText("");

    }

}