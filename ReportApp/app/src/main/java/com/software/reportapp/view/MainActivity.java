package com.software.reportapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.software.reportapp.databinding.ActivityMainBinding;
import com.software.reportapp.phonestatus.PhoneStatusActivity;
import com.software.reportapp.view.contact.ContactManageActivity;

public class MainActivity extends AppCompatActivity {
    // MainActivity viewBonding 선언
    private ActivityMainBinding binding;

    // view 선언
    private MaterialCardView cvPhoneNumber;
    private MaterialCardView cvInternetSearch;
    private MaterialCardView cvPhoneStatus;
    private MaterialCardView cvAuthor;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // viewBinding 초기화
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // context 초기화
        context = getApplicationContext();

        // view 연동 및 초기화
        initView();

        // onClick 리스너
        cardViewOnclick();

    }

    // View 연동 메서드
    private void initView() {
        // ViewBinding으로 CardView id 가져와서 연동
        cvPhoneNumber = binding.cvPhoneNumber;
        cvInternetSearch = binding.cvInternetSearch;
        cvPhoneStatus = binding.cvPhoneStatus;
        cvAuthor = binding.cvAuthor;
    }


    private void cardViewOnclick()
    {
        // 각각의 컨텐츠 클릭 시 해당하는 Activity 실행
        cvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ContactManageActivity.class);
            }
        });

        cvInternetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(InternetSearchActivity.class);
            }
        });

        cvPhoneStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PhoneStatusActivity.class);
            }
        });

        cvAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AuthorActivity.class);
            }
        });

    }

    // 실행 할 activity 클래스를 매개변수로 넘겨주면 해당 엑티비티로 화면 넘어감
    private void startActivity( Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        startActivity(intent);
    }

}