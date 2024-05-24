package com.example.greenscape;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.greenscape.entity.PlantDatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calen extends AppCompatActivity {
    private EditText txtPlantName, txtDate, txtTime;
    private Button btnSave, btnCapture, btnDate, btnTime;
    private ImageView imgPlant;

    private PlantDatabaseHelper databaseHelper;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        txtPlantName = findViewById(R.id.et_plant_name);
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);
        btnSave = findViewById(R.id.btn_save);
        btnCapture = findViewById(R.id.btn_capture);
        btnDate = findViewById(R.id.btn_date);
        btnTime = findViewById(R.id.btn_time);
        imgPlant = findViewById(R.id.img_plant);

        databaseHelper = new PlantDatabaseHelper(this);

        btnSave.setOnClickListener(this::create);
        btnCapture.setOnClickListener(this::capturePhoto);
        btnDate.setOnClickListener(this::openDatePicker);
        btnTime.setOnClickListener(this::openTimePicker);
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
            Intent intent = new Intent(Calen.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(Calen.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void create(View view) {
        String plantName = txtPlantName.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();

        if (plantName.isEmpty()) {
            Toast.makeText(this, "Please enter plant name", Toast.LENGTH_SHORT).show();
        } else if (date.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
        } else if (time.isEmpty()) {
            Toast.makeText(this, "Please select time", Toast.LENGTH_SHORT).show();
        } else if (imageBitmap == null) {
            Toast.makeText(this, "Please capture a photo", Toast.LENGTH_SHORT).show();
        } else {
            savePlant(plantName, date, time);
        }
    }

    private void openDatePicker(View view) {
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) -> {
                    String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                    txtDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openTimePicker(View view) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (TimePicker timePicker, int selectedHour, int selectedMinute) -> {
                    String selectedTime = selectedHour + ":" + selectedMinute;
                    txtTime.setText(selectedTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void capturePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgPlant.setImageBitmap(imageBitmap);
        }
    }
    // При збереженні фотографії в базі даних
    private String saveImageToStorage(Bitmap bitmap) {
        String imagePath = null;
        try {
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File directory = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, "unique_image_filename" + ".jpg");

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            imagePath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }


    private void savePlant(String name, String date, String time) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("watering_date", date + " " + time);

        // Save photo as byte array
        if (imageBitmap != null) {
            values.put("photo", getByteArrayFromBitmap(imageBitmap));
        }

        long newRowId = db.insert("plants", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Plant saved successfully", Toast.LENGTH_SHORT).show();
            saveEventInCalendar(name, date + " " + time);

            // Passing data to SecondActivity
            Intent intent = new Intent(this, SecondActivity.class);
            // Pass bitmap byte array to SecondActivity
            intent.putExtra("photo", getByteArrayFromBitmap(imageBitmap));
            intent.putExtra("date", date + " " + time);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error saving plant", Toast.LENGTH_SHORT).show();
        }
    }


    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    private void saveEventInCalendar(String eventName, String eventDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = dateFormat.parse(eventDate);

            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(date);

            Calendar endTime = Calendar.getInstance();
            endTime.setTimeInMillis(beginTime.getTimeInMillis() + 60 * 60 * 1000);

            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, eventName)
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Water plant event")
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Your plant location")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No calendar app found", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing date", Toast.LENGTH_SHORT).show();
        }
    }
}
