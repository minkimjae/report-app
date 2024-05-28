package com.software.reportapp.view;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityAuthorBinding;

public class AuthorActivity extends AppCompatActivity {
    private ActivityAuthorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    private void initView() {
        binding.header.titleText.setText("저자 소개");
        binding.header.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}