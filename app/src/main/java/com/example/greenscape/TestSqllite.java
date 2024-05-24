package com.example.greenscape;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greenscape.adapters.ItemAdapter;
import com.example.greenscape.entity.Item;
import com.example.greenscape.model.ItemViewModel;

import java.util.List;

public class TestSqllite extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextDescription;
    private Button buttonAdd;
    private Button buttonChooseImage;
    private RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    private Uri imageUri; // Оголошення поля imageUri

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

        // Отримуємо всі елементи при створенні активності
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.setItems(items);
            }
        });

        // Додавання слухача кнопки "Add"
        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            if (!name.isEmpty() && !description.isEmpty() && imageUri != null) {
                Item item = new Item();
                item.setName(name);
                item.setDescription(description);
                item.setImageUrl(imageUri.toString()); // Передача imageUri в об'єкт Item
                itemViewModel.insert(item);
                editTextName.setText("");
                editTextDescription.setText("");
            }
        });

        // Додавання слухача кнопки "Choose Image"
        buttonChooseImage.setOnClickListener(v -> openFileChooser());
    }

    // Метод для вибору зображення з галереї
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Обробник результату вибору зображення
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }
}