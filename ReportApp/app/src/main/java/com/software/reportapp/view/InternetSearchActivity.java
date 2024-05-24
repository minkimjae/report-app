package com.software.reportapp.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SearchView;
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
    private SearchView searchView;
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
        searchView = binding.searchView;
        searchButton = binding.searchButton;

        webView.setWebViewClient(new WebViewClient()); // 클릭시 새 창이 뜨지 않도록
        webView.getSettings().setJavaScriptEnabled(true); // 자바스크립트 사용 허용

        // SearchView를 아이콘화하지 않고 처음부터 확장
        searchView.setIconified(false);

        // SearchView를 더 이상 아이콘화 할 수 없도록 설정
        searchView.setIconifiedByDefault(false);

        // 검색 이벤트 리스너 설정
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색 버튼이 눌렸을 때 호출
                webView.loadUrl(query);
                return true;  // true를 반환하면 이벤트가 처리되었음을 의미
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 검색 텍스트가 변경될 때마다 호출
                return false;  // true를 반환하면 이벤트가 처리되었음을 의미
            }
        });
    }




}