package com.example.greenscape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.greenscape.R;
import com.example.greenscape.ResultsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


public class ImageAnswear extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;

    private ImageView imageView;
    private TextView tvResult;


    private ImageButton viewResultsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_answear);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }
        viewResultsButton = findViewById(R.id.viewResultsButton);

        imageView = findViewById(R.id.imageView);
        tvResult = findViewById(R.id.tvResult);

        viewResultsButton.setOnClickListener(this::gotoResult);
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
            Intent intent = new Intent(ImageAnswear.this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.exit) {
            Intent intent = new Intent(ImageAnswear.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    private void gotoResult(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);

    }

    // Метод для вибору фото з галереї
    public void chooseFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_IMAGE);
    }

    // Метод для зйомки фото
    public void takePhoto(View view) {
        dispatchTakePictureIntent();
    }

    // Виклик активності для зйомки фото
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Обробка результатів вибору або зйомки фото
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            // Сканування фото після зйомки
            labelImage(imageBitmap);

            // Змінюємо видимість елементів після отримання результатів
            tvResult.setVisibility(View.VISIBLE);
            findViewById(R.id.textView6).setVisibility(View.VISIBLE);
            viewResultsButton.setVisibility(View.VISIBLE);
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                imageView.setVisibility(View.VISIBLE);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);

                // Сканування вибраного фото
                labelImage(bitmap);

                // Змінюємо видимість елементів після отримання результатів
                tvResult.setVisibility(View.VISIBLE);
                findViewById(R.id.textView6).setVisibility(View.VISIBLE);
                viewResultsButton.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    // Метод для сканування зображення та отримання міток
    private void labelImage(Bitmap bitmap) {
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
        com.google.mlkit.vision.common.InputImage image = com.google.mlkit.vision.common.InputImage.fromBitmap(bitmap, 0);

        labeler.process(image)
                .addOnSuccessListener(imageLabels -> {
                    StringBuilder result = new StringBuilder();
                    for (ImageLabel label : imageLabels) {
                        String text = label.getText();
                        float confidence = label.getConfidence();
                        result.append(text).append(": ").append(confidence).append("\n");
                    }
                    tvResult.setText(result.toString());
                    saveLabelsToFirebase(imageLabels);
                    Toast.makeText(ImageAnswear.this, "Фото скановано", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ImageAnswear.this, "Помилка сканування: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void saveLabelsToFirebase(List<ImageLabel> imageLabels) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("scan_table_results");

        for (ImageLabel label : imageLabels) {
            String text = label.getText();
            float confidence = label.getConfidence();

            // Зберігаємо мітку та її рівень впевненості у базі даних
            databaseReference.push().setValue(text + ": " + confidence)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Results uploaded successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Failed to upload results", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}