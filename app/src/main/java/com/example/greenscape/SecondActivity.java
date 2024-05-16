
        package com.example.greenscape;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Отримання даних з Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            byte[] photoByteArray = extras.getByteArray("photo");
            String dateString = extras.getString("date");

            // Отримання ресурсів з макету
            ImageView imageView = findViewById(R.id.imageView);
            TextView dateTextView = findViewById(R.id.dateTextView);

            // Встановлення фотографії та дати відповідно
            if (photoByteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);
                imageView.setImageBitmap(bitmap);
            }
            dateTextView.setText(dateString);
        }
    }

    private Bitmap getBitmapFromByteArray(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } else {
            return null;
        }
    }
}