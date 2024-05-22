package com.software.reportapp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true) // DB 값 추가 시 자동으로 +1 증가된 값 삽입
    public int id; // PK
    public String name; // 이름
    public String phoneNumber; // 연락처
    public String email; // 이메일
    public String workPlace; // 직장명
    public String title; // 직합
}
