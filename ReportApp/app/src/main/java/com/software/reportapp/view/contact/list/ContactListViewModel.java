package com.software.reportapp.view.contact.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.repository.DBRepository;

import java.util.List;

public class ContactListViewModel extends ViewModel {
    private LiveData<List<Contact>> contactList;
    private DBRepository dbRepository = new DBRepository();

    public ContactListViewModel() {
        this.contactList = loadContactList();
    }

    private LiveData<List<Contact>> loadContactList() {
        return dbRepository.getAllData();
    }

    public LiveData<List<Contact>> getContactList() {
        return contactList;
    }

}
