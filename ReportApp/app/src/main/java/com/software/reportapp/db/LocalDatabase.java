package com.software.reportapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.software.reportapp.db.dao.ContactDao;
import com.software.reportapp.db.entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ContactDao dao;
}
