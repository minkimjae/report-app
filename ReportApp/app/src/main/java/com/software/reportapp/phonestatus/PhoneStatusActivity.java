package com.software.reportapp.phonestatus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityPhoneStatusBinding;
import com.software.reportapp.view.contact.add.AddContactViewModel;

public class PhoneStatusActivity extends AppCompatActivity {

    private ActivityPhoneStatusBinding binding;
    private PhoneStatusViewModel viewModel;

    private TextView tvAndroidVersion;
    private TextView tvWifiOnOff;
    private TextView tvLteOnOff;
    private TextView tvSdCardVolume;
    private TextView tvAppStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPhoneStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(PhoneStatusViewModel.class);

        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        binding.header.titleText.setText("스마트폰 동작 상태");
        binding.header.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAndroidVersion = binding.tvAndroidVersion;
        tvAndroidVersion.setText(viewModel.getAndroidVersion());

        tvWifiOnOff = binding.tvWifiOnOff;
        tvWifiOnOff.setText(viewModel.getWIFIStatus(this));

        tvLteOnOff = binding.tvLteOnOff;
        tvLteOnOff.setText(viewModel.getLTEStatus(this));

        tvSdCardVolume = binding.tvSdCardVolume;
        tvSdCardVolume.setText(viewModel.getSCCardInfo());

        tvAppStatus = binding.tvAppStatus;
        tvAppStatus.setText(viewModel.getInstalledAppsCount(this)+"/"+viewModel.getRunningServicesCount(this));
    }
}