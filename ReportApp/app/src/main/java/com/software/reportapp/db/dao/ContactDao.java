package com.software.reportapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.software.reportapp.db.entity.Contact;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface ContactDao {
    @Insert
    Completable insert(Contact contact); // 연락처 추가

    @Update
    Completable update(Contact contact); // 연락처 수정

    @Delete
    Completable deleteContact(Contact contact); // 연락처 삭제

    @Query("SELECT id, name FROM Contact")
    LiveData<List<Contact>> getContactList(); // 연락처 목록

    @Query("SELECT * FROM Contact WHERE id = :id")
    LiveData<Contact> getContactById(int id); // 연락처 상세 보기에 사용

}
