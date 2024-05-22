package com.software.reportapp.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityInternetSearchBinding;

public class InternetSearchActivity extends AppCompatActivity {

    private ActivityInternetSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityInternetSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}