package com.software.reportapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.software.reportapp.App;
import com.software.reportapp.db.LocalDatabase;
import com.software.reportapp.db.entity.Contact;

import java.util.List;

public class DBRepository {
    Context contact = App.getContext();
    LocalDatabase db = LocalDatabase.getDatabase(contact);

    public LiveData<List<Contact>> getAllData() {
        return db.dao().getContactList();
    }
}
