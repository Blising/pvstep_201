package com.example.greenscape;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.adapters.ItemAdapter;
import com.example.greenscape.entity.Item;
import com.example.greenscape.model.ItemViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class TestSqllite extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private Button buttonAdd;
    private Button buttonChooseImage;
    private RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    private Uri imageUri;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sqllite);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.setItems(items);
            }
        });

        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            if (!name.isEmpty() && !description.isEmpty() && imageUri != null) {
                String imagePath = saveImageToInternalStorage(imageUri);
                if (imagePath != null) {
                    String id = UUID.randomUUID().toString();
                    Item item = new Item(id, name, description, imagePath);
                    itemViewModel.insert(item);
                    editTextName.setText("");
                    editTextDescription.setText("");
                    imageUri = null; // Reset the URI after adding
                }
            }
        });

        buttonChooseImage.setOnClickListener(v -> openFileChooser());

        adapter.setOnDeleteClickListener(item -> {
            deleteImageFromInternalStorage(item.getImageUrl());
            itemViewModel.delete(item);
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.backtomenu) {
            Intent intent = new Intent(TestSqllite.this, MenuActivity.class);
            startActivity(intent);
        }
        return true;



    }
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
            File directory = wrapper.getDir("images", MODE_PRIVATE);
            String fileName = UUID.randomUUID().toString() + ".jpg";
            File file = new File(directory, fileName);
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteImageFromInternalStorage(String imagePath) {
        File file = new File(imagePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
