package com.software.reportapp.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityInternetSearchBinding;

public class InternetSearchActivity extends AppCompatActivity {

    private ActivityInternetSearchBinding binding;

    private WebView webView;
    private MaterialAutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // view binding 초기화
        binding = ActivityInternetSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        webView = binding.webView;
        textView = binding.tfUrlSearch;

        // WebView 설정
        webView.setWebViewClient(new WebViewClient()); // 클릭시 새 창이 뜨지 않도록
        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용 허용
    }
}