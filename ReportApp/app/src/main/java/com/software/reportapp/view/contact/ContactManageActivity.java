package com.software.reportapp.view.contact;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.software.reportapp.R;
import com.software.reportapp.databinding.ActivityContactManageBinding;
import com.software.reportapp.db.LocalDatabase;

public class ContactManageActivity extends AppCompatActivity {
    private ActivityContactManageBinding binding;
    private ContactViewModel viewModel;  // viewModel 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityContactManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        initView();
    }

    private void initView() {
        // NavController 초기화
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frameLayout);
        // NavController 선언
        NavController navController = navHostFragment.getNavController();
        // Fragment 이동 시 header의 title을 변경
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = "";
            int id = destination.getId();

            if (id == R.id.contactListFragment) {
                title = getString(R.string.contact_list_title); // string.xml에서 문자열 추출
            } else if (id == R.id.addContactFragment) {
                title = getString(R.string.contact_add_title);
            } else if (id == R.id.contactDetailFragment) {
                title = getString(R.string.contact_detail_title);
            }

            binding.header.titleText.setText(title);

        });

        // 뒤로가기 버튼 클릭 시 연락처 관리 화면으로 가게 설정
        binding.header.backButton.setOnClickListener(v -> {
            if (navController.getCurrentDestination().getId() == R.id.contactListFragment) {
                // 현재 contactListFragment에 있다면 액티비티 종료 (메인 액티비티로 돌아가기)
                finish();
            } else {
                // 다른 프래그먼트에 있다면 이전 화면으로 이동
                navController.navigateUp();
            }
        });
    }



}