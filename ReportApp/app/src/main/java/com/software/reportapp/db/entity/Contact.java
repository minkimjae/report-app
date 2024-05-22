package com.software.reportapp.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate = true) // DB 값 추가 시 자동으로 +1 증가된 값 삽입
    private int id; // PK
    private String name; // 이름
    private String phoneNumber; // 연락처
    private String email; // 이메일
    private String workPlace; // 직장명
    private String title; // 직합
}
