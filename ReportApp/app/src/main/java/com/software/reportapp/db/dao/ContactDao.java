package com.software.reportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.software.reportapp.db.entity.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Query("SELECT id, name FROM Contact")
    LiveData<List<Contact>> getContactList();

    @Query("SELECT * FROM Contact WHERE id = :id")
    LiveData<Contact> getContactById(int id);

    @Delete
    int delete(Contact contact);
}
