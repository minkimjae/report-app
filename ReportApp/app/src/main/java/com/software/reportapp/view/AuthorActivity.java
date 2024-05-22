package com.software.reportapp.view;

import android.os.Bundle;

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
}