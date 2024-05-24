package com.software.reportapp.view.contact;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.software.reportapp.databinding.ActivityContactManageBinding;

public class ContactManageActivity extends AppCompatActivity {
    private ActivityContactManageBinding binding;
    // 하단 floatingAction Button 선언
    private FloatingActionButton faButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityContactManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private void initView() {
        faButton = binding.faButton;

        faButton.setOnClickListener(view -> {

        });
    }
}