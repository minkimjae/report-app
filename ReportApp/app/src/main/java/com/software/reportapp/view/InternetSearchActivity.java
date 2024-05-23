package com.software.reportapp.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
    private AppCompatButton searchButton;

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
        searchButton = binding.searchButton;

        webView.setWebViewClient(new WebViewClient()); // 클릭시 새 창이 뜨지 않도록
        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용 허용

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "URL을 입력해주세요", Toast.LENGTH_SHORT).show();

                    return;
                }

                webView.loadUrl(textView.getText().toString());
            }
        });

        // WebView 설정
    }
}