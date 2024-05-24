package com.software.reportapp.view.contact;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.software.reportapp.db.dao.ContactDao;
import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.repository.DBRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private LiveData<List<Contact>> contacts;
    private DBRepository repository = new DBRepository();

    public ContactViewModel() {
        contacts = repository.getAllData();
    }

    public LiveData<List<Contact>> getContacts() {
        return contacts;
    }
}
