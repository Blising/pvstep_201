package com.example.greenscape.model;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.greenscape.entity.Item;
import com.example.greenscape.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository repository;
    private LiveData<List<Item>> allItems;
    private Uri imageUri; // Оголошення поля imageUri

    public ItemViewModel(Application application) {
        super(application);
        repository = new ItemRepository(application, imageUri); // Передача imageUri до репозиторію
        allItems = repository.getAllItems();
        repository.syncDataFromFirebase();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
        repository = new ItemRepository(getApplication(), imageUri); // Передача оновленого imageUri до репозиторію
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }
}
