package com.example.greenscape.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.greenscape.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
     void    insert(Item item);
     @Update
     void update(Item item);
     @Delete
     void delete(Item item);

    @Query("SELECT * FROM items")
    LiveData<List<Item>> getAllItems();
}
