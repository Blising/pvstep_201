package com.example.greenscape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class RoomUsersActivity extends AppCompatActivity {
    // UI components
    private EditText phoneEditText, countryEditText, addressEditText;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_users);

        // Get data from intent
        String photoUrl = getIntent().getStringExtra("photoUrl");
        String displayName = getIntent().getStringExtra("displayName");
        String email = getIntent().getStringExtra("email");

        // Find views from layout
        ImageView profileImage = findViewById(R.id.profileImage);
        TextView displayNameTextView = findViewById(R.id.displayNameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        phoneEditText = findViewById(R.id.phoneEditText);
        countryEditText = findViewById(R.id.countryEditText);
        addressEditText = findViewById(R.id.addressEditText);
        Button saveButton = findViewById(R.id.saveButton);

        // Set data to the corresponding views
        Glide.with(this).load(photoUrl).into(profileImage);
        displayNameTextView.setText(displayName);
        emailTextView.setText(email);

        // Initialize SQLite database
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Load saved user info and display it
        loadUserInfo();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
    }

    private void loadUserInfo() {
        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PHONE));
            String country = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_COUNTRY));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_ADDRESS));

            phoneEditText.setText(phone);
            countryEditText.setText(country);
            addressEditText.setText(address);
        }

        cursor.close();
    }

    private void saveUserInfo() {
        String phone = phoneEditText.getText().toString();
        String country = countryEditText.getText().toString();
        String address = addressEditText.getText().toString();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_PHONE, phone);
        values.put(UserContract.UserEntry.COLUMN_NAME_COUNTRY, country);
        values.put(UserContract.UserEntry.COLUMN_NAME_ADDRESS, address);

        // Clear the table before inserting new data
        db.delete(UserContract.UserEntry.TABLE_NAME, null, null);

        long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Information saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error saving information.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.prevmeny, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.backtomenu) {
            Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RoomUsersActivity.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(RoomUsersActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
