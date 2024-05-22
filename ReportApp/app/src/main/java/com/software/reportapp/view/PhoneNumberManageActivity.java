package com.software.reportapp.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityPhoneNumberManageBinding;

public class PhoneNumberManageActivity extends AppCompatActivity {
    private ActivityPhoneNumberManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityPhoneNumberManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}