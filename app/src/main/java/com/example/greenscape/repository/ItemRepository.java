package com.example.greenscape.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;

import com.example.greenscape.dao.ItemDao;

import com.example.greenscape.database.AppDatabase;
import com.example.greenscape.entity.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> allItems;
    private DatabaseReference firebaseDatabaseReference;

    public ItemRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        itemDao = database.itemDao();
        allItems = itemDao.getAllItems();
        firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("items");
    }

    public void insert(Item item) {
        new InsertItemAsyncTask(itemDao, firebaseDatabaseReference).execute(item);
    }

    public void update(Item item) {
        new UpdateItemAsyncTask(itemDao, firebaseDatabaseReference).execute(item);
    }

    public void delete(Item item) {
        new DeleteItemAsyncTask(itemDao, firebaseDatabaseReference, allItems).execute(item);
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    private static class InsertItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        private DatabaseReference firebaseDatabaseReference;

        private InsertItemAsyncTask(ItemDao itemDao, DatabaseReference firebaseDatabaseReference) {
            this.itemDao = itemDao;
            this.firebaseDatabaseReference = firebaseDatabaseReference;
        }

        @Override
        protected Void doInBackground(Item... items) {
            String id = firebaseDatabaseReference.push().getKey();
            if (id != null) {
                items[0].setId(id);
                itemDao.insert(items[0]);
                firebaseDatabaseReference.child(id).setValue(items[0]);
            }
            return null;
        }
    }

    private static class UpdateItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        private DatabaseReference firebaseDatabaseReference;

        private UpdateItemAsyncTask(ItemDao itemDao, DatabaseReference firebaseDatabaseReference) {
            this.itemDao = itemDao;
            this.firebaseDatabaseReference = firebaseDatabaseReference;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.update(items[0]);
            firebaseDatabaseReference.child(items[0].getId()).setValue(items[0]);
            return null;
        }
    }

    private static class DeleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao itemDao;
        private DatabaseReference firebaseDatabaseReference;
        private LiveData<List<Item>> allItems;

        private DeleteItemAsyncTask(ItemDao itemDao, DatabaseReference firebaseDatabaseReference, LiveData<List<Item>> allItems) {
            this.itemDao = itemDao;
            this.firebaseDatabaseReference = firebaseDatabaseReference;
            this.allItems = allItems;
        }

        @Override
        protected Void doInBackground(Item... items) {
            itemDao.delete(items[0]);
            firebaseDatabaseReference.child(items[0].getId()).removeValue();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Notify that the dataset has changed
            if (allItems instanceof MutableLiveData) {
                ((MutableLiveData<List<Item>>) allItems).setValue(itemDao.getAllItems().getValue());
            }
        }
    }
}
