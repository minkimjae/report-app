package com.software.reportapp.phonestatus;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.software.reportapp.databinding.ActivityPhoneStatusBinding;

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


        // viewModel 초기화
        viewModel = new ViewModelProvider(this).get(PhoneStatusViewModel.class);


        // USAGE_STATS에 대한 권한 체크
        if(!checkPermission()) {
            // 권한 설정창 실행
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            // 권한이 허용 되었다면 view Init
            initView();
        }
    }



    @SuppressLint("SetTextI18n")
    private void initView() {
        // 헤더 세팅
        binding.header.titleText.setText("스마트폰 동작 상태");
        binding.header.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvAndroidVersion = binding.tvAndroidVersion;
        // 안드로이드 버전 입력
        tvAndroidVersion.setText(viewModel.getAndroidVersion());

        tvWifiOnOff = binding.tvWifiOnOff;
        // Wifi 상태 입력
        tvWifiOnOff.setText(viewModel.getWIFIStatus(this));

        tvLteOnOff = binding.tvLteOnOff;
        // LTE 상태 입력
        tvLteOnOff.setText(viewModel.getLTEStatus(this));

        tvSdCardVolume = binding.tvSdCardVolume;
        // SDCARD 용량 입력
        tvSdCardVolume.setText(viewModel.getSCCardInfo());

        tvAppStatus = binding.tvAppStatus;
        // 전체 앱 / 실행중인 앱 입력
        tvAppStatus.setText(viewModel.getInstalledAppsCount(this)+"/"+viewModel.getRunningAppCount(this));
    }

    private boolean checkPermission(){ // 권한 체크 함수
        boolean granted = false;

        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (getApplicationContext().checkCallingOrSelfPermission(
                    android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        return granted;
    }
}