package com.example.praktikumpamm8tugas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contact ORDER BY id ASC")
    LiveData<List<Contact>> getAllContacts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertContact(Contact contact);

    @Update()
    void updateContact(Contact contact);

    @Delete()
    void deleteContact(Contact contact);

}
