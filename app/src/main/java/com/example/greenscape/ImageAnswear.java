package com.example.greenscape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;


public class ImageAnswear extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;

    private ImageView imageView;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_answear);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        }

        imageView = findViewById(R.id.imageView);
        tvResult = findViewById(R.id.tvResult);
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
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);

                // Сканування вибраного фото
                labelImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для сканування зображення та отримання міток
    private void labelImage(Bitmap bitmap) {
        // Створення ImageLabeler
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        // Створення InputImage з Bitmap
        com.google.mlkit.vision.common.InputImage image = com.google.mlkit.vision.common.InputImage.fromBitmap(bitmap, 0);

        // Виконання сканування
        labeler.process(image)
                .addOnSuccessListener(imageLabels -> {
                    // Отримання результатів сканування
                    StringBuilder result = new StringBuilder();
                    for (ImageLabel label : imageLabels) {
                        String text = label.getText();
                        float confidence = label.getConfidence();
                        result.append(text).append(": ").append(confidence).append("\n");
                    }
                    // Виведення результатів
                    tvResult.setText(result.toString());
                    Toast.makeText(ImageAnswear.this, "Фото скановано", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Обробка помилки
                    Toast.makeText(ImageAnswear.this, "Помилка сканування: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}

