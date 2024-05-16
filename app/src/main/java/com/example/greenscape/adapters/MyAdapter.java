package com.example.greenscape.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greenscape.R;

public class MyAdapter extends CursorAdapter {

    public MyAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        // Отримуємо дані з курсора
        byte[] photoByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("photo"));
        String plantName = cursor.getString(cursor.getColumnIndexOrThrow("name"));

        // Перетворюємо масив байтів у Bitmap
        Bitmap photoBitmap = BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.length);

        // Встановлюємо фотографію та назву рослини відповідно
        imageView.setImageBitmap(photoBitmap);
        textView.setText(plantName);
    }
}
