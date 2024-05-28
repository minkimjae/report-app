package com.software.reportapp.view.contact.add;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.repository.DBRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddContactViewModel extends ViewModel{
    private DBRepository dbRepository = new DBRepository();

    @SuppressLint("CheckResult")
    public boolean insertContact(Contact contact) {
        boolean isSave = true;

        try {
            // Rxjava를 통해 비동기 처리
            dbRepository.insertContact(contact)
                    .subscribeOn(Schedulers.io()) // IO 스레드에서 DB 작업 실행, Main 스레드에서 실행 시 오류 발생
                    .observeOn(AndroidSchedulers.mainThread()) // 결과를 메인 스레드에서 처리
                    .subscribe(
                            () -> {
                                // 삽입 성공 시 처리
                                Log.d("ContactViewModel", "Insert successful");
                            },
                            throwable -> {
                                // 에러 처리
                                Log.e("ContactViewModel", "Error inserting contact", throwable);
                                throw new Exception();
                            }
                    );
        } catch (Exception e) {
            e.printStackTrace();
            isSave = false;
        }

        return isSave;
    }
}
