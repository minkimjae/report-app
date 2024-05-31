package com.software.reportapp.view.contact.detail;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.repository.DBRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactDetailViewModel extends ViewModel {
    private LiveData<Contact> contact;
    private DBRepository repository = new DBRepository();

    public ContactDetailViewModel() {
    }

    public void setLiveDataContact(int contactId) {
        this.contact = repository.getContactById(contactId);
    }

    public LiveData<Contact> getContact() {
        return contact;
    }

    @SuppressLint("CheckResult")
    public boolean updateContact(Contact contact) {
        Log.d("ContactDetailViewModel", "cotact name : "+contact.getName());
        boolean isUpdate = true;

        try {
            // Rxjava를 통해 비동기 처리
            repository.updateContact(contact)
                    .subscribeOn(Schedulers.io()) // IO 스레드에서 DB 작업 실행, Main 스레드에서 실행 시 오류 발생
                    .observeOn(AndroidSchedulers.mainThread()) // 결과를 메인 스레드에서 처리
                    .subscribe(
                            () -> {
                                // 삽입 성공 시 처리
                                Log.d("ContactDetailViewModel", "Update successful");
                            },
                            throwable -> {
                                // 에러 처리
                                Log.e("ContactDetailViewModel", "Error Update contact", throwable);
                                throw new Exception();
                            }
                    );
        } catch (Exception e) {
            e.printStackTrace();
            isUpdate = false;
        }

        return isUpdate;
    }

}
