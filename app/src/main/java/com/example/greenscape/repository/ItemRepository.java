package com.example.greenscape.repository;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.example.greenscape.dao.ItemDao;
import com.example.greenscape.database.AppDatabase;
import com.example.greenscape.entity.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ItemRepository {
    private final ItemDao itemDao;
    private final DatabaseReference databaseReference;
    private final StorageReference storageReference;
    private final Uri imageUri; // Додано поле для imageUri

    public ItemRepository(Application application, Uri imageUri) {
        AppDatabase db = AppDatabase.getInstance(application);
        itemDao = db.itemDao();
        databaseReference = FirebaseDatabase.getInstance().getReference("items");
        storageReference = FirebaseStorage.getInstance().getReference("images");
        this.imageUri = imageUri; // Зберігаємо imageUri
    }

    public LiveData<List<Item>> getAllItems() {
        return itemDao.getAllItems();
    }

    public void insert(Item item) {
        if (imageUri != null) {
            StorageReference imageRef = storageReference.child(System.currentTimeMillis() + ".jpg");
            imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        item.setImageUrl(uri.toString());
                        insertItemWithFirebase(item);
                    })
            );
        } else {
            insertItemWithFirebase(item);
        }
    }

    private void insertItemWithFirebase(Item item) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValue(item);
        }
    }

    public void update(Item item) {
        new Thread(() -> itemDao.update(item)).start();
        // You can add update logic for Firebase here if needed
    }

    public void delete(Item item) {
        new Thread(() -> itemDao.delete(item)).start();
        // You can add delete logic for Firebase here if needed
    }

    public void syncDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new Thread(() -> {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Item item = snapshot.getValue(Item.class);
                        if (item != null) {
                            insertItemWithRoom(item);
                        }
                    }
                }).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void insertItemWithRoom(Item item) {
        new Thread(() -> itemDao.insert(item)).start();
    }
}
