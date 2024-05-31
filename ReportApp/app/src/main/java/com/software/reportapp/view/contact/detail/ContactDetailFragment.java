package com.software.reportapp.view.contact.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.software.reportapp.R;
import com.software.reportapp.databinding.FragmentContactDetailBinding;
import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.view.contact.add.AddContactFragment;
import com.software.reportapp.view.contact.add.AddContactViewModel;

import java.util.Objects;

public class ContactDetailFragment extends Fragment {
    private int contactId = -1;
    private FragmentContactDetailBinding binding;

    private ContactDetailViewModel viewModel;

    private TextInputLayout emailInputLayout;
    private TextInputLayout nameInputLayout;
    private TextInputLayout phoneNumberInputLayout;
    private TextInputLayout titleInputLayout;
    private TextInputLayout workPlaceInputLayout;
    private Button saveButton;
    private Button cancelButton;
    private Button shareButton;

    private NavController navController;

    public ContactDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ContactDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactDetailBinding.inflate(inflater, container, false);

        navController = NavHostFragment.findNavController(ContactDetailFragment.this);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        emailInputLayout = binding.emailInputLayout;
        nameInputLayout = binding.nameInputLayout;
        phoneNumberInputLayout = binding.phoneNumberInputLayout;
        workPlaceInputLayout = binding.workPlaceInputLayout;
        titleInputLayout = binding.titleInputLayout;

        saveButton = binding.saveButton;
        saveButton.setOnClickListener(new View.OnClickListener() { // 저장 버튼 클릭 시
            @Override
            public void onClick(View view) {
                Contact contact = new Contact();
                contact.setName(Objects.requireNonNull(nameInputLayout.getEditText()).getText().toString());
                contact.setEmail(Objects.requireNonNull(emailInputLayout.getEditText()).getText().toString());
                contact.setPhoneNumber(Objects.requireNonNull(phoneNumberInputLayout.getEditText()).getText().toString());
                contact.setWorkPlace(Objects.requireNonNull(workPlaceInputLayout.getEditText()).getText().toString());
                contact.setTitle(Objects.requireNonNull(titleInputLayout.getEditText()).getText().toString());

                boolean updateResult = viewModel.updateContact(contact);

                if(updateResult) {
                    Toast.makeText(getContext(), "정보 변경에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    navController.navigateUp();
                } else {
                    Toast.makeText(getContext(), "정보 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelButton = binding.cancelButton;
        cancelButton.setOnClickListener(new View.OnClickListener() { // 취소 버튼 클릭 시
            @Override
            public void onClick(View view) {
                navController.navigateUp();
            }
        });

        shareButton = binding.shareButton;
        shareButton.setOnClickListener(new View.OnClickListener() { // 공유 버튼 클릭 시
            @Override
            public void onClick(View view) {

            }
        });


        if(getArguments() != null) {
            contactId = getArguments().getInt("contactId");

            // viewModel LiveData에 DB 데이터 세팅
            viewModel.setLiveDataContact(contactId);

            viewModel.getContact().observe(getViewLifecycleOwner(), contact -> {
                nameInputLayout.getEditText().setText(contact.getName());
                emailInputLayout.getEditText().setText(contact.getEmail());
                phoneNumberInputLayout.getEditText().setText(contact.getPhoneNumber());
                workPlaceInputLayout.getEditText().setText(contact.getWorkPlace());
                titleInputLayout.getEditText().setText(contact.getTitle());
            });

        }
    }
}