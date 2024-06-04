package com.software.reportapp.view.contact.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.software.reportapp.R;
import com.software.reportapp.databinding.FragmentContactDetailBinding;
import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.utill.EmailValidator;

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
    private Button deleteButton;
    private Button shareButton;

    private NavController navController;

    private Contact contactDetail;

    public ContactDetailFragment() {

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
                if(updateContactValidate()) {
                    Contact contact = new Contact();
                    contact.setId(contactId);
                    contact.setName(Objects.requireNonNull(nameInputLayout.getEditText()).getText().toString());
                    contact.setEmail(Objects.requireNonNull(emailInputLayout.getEditText()).getText().toString());
                    contact.setPhoneNumber(Objects.requireNonNull(phoneNumberInputLayout.getEditText()).getText().toString());
                    contact.setWorkPlace(Objects.requireNonNull(workPlaceInputLayout.getEditText()).getText().toString());
                    contact.setTitle(Objects.requireNonNull(titleInputLayout.getEditText()).getText().toString());

                    boolean updateResult = viewModel.updateContact(contact);

                    if(updateResult) { // 연락처 변경 성공 시
                        Toast.makeText(getContext(),
                                        getString(R.string.update_success),
                                        Toast.LENGTH_SHORT)
                                .show();

                        navController.navigateUp();
                    } else { // 연락처 변경 실패 시
                        Toast.makeText(getContext(),
                                        getString(R.string.update_fail),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                }

            }
        });

        deleteButton = binding.deleteButton;
        deleteButton.setOnClickListener(new View.OnClickListener() { // 삭제 버튼 클릭 시
            @Override
            public void onClick(View view) {
                showDeleteDialog(getString(R.string.delete_check_title),
                        getString(R.string.delete_check_message));
            }
        });

        shareButton = binding.shareButton;
        shareButton.setOnClickListener(new View.OnClickListener() { // 공유 버튼 클릭 시
            @Override
            public void onClick(View view) {
                shareContact(contactDetail);
            }
        });

        // 받은 값이 있다면
        if(getArguments() != null) {
            // 목록에서 넘어온 contactId 추출
            contactId = getArguments().getInt("contactId");

            // viewModel LiveData에 DB 데이터 세팅
            viewModel.setLiveDataContact(contactId);

            // viewModel LiveData 구독 - 데이터 변화가 생길 때를 감지하여 실행
            viewModel.getContact().observe(getViewLifecycleOwner(), contact -> {
                contactDetail = contact;
                nameInputLayout.getEditText().setText(contact.getName());
                emailInputLayout.getEditText().setText(contact.getEmail());
                phoneNumberInputLayout.getEditText().setText(contact.getPhoneNumber());
                workPlaceInputLayout.getEditText().setText(contact.getWorkPlace());
                titleInputLayout.getEditText().setText(contact.getTitle());
            });

        }
    }

    private void shareContact(Contact contact) {
        // 연락처 정보를 문자열로 변환
        String contactInfo = "성명: " + contact.getName() + "\n전화번호: "
                + contact.getPhoneNumber() + "\n이메일: "
                 + contact.getEmail() + "\n직장: "+contact.getWorkPlace() + "\n직함: "+contact.getTitle();

        // 공유 인텐트 생성
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, contactInfo);
        startActivity(Intent.createChooser(shareIntent, "Share Contact via"));
    }


    // 취소 버튼 눌렀을 때 표출되는 다이얼로그
    private void showDeleteDialog(String title, String text) {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(title)
                .setMessage(text)
                .setNegativeButton("취소", null)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleteResult = viewModel.deleteContact(contactId);

                        if(deleteResult) {
                            Toast.makeText(getContext(),
                                    getResources().getText(R.string.delete_success),
                                    Toast.LENGTH_SHORT)
                                    .show();

                            navController.navigateUp();
                        } else {
                            Toast.makeText(getContext(),
                                    getResources().getText(R.string.delete_fail),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }

                        dialog.dismiss();
                    }
                })
                .show();
    }

    private boolean updateContactValidate() {
        boolean isValidate = true;

        String name = nameInputLayout.getEditText().getText().toString();
        String email = emailInputLayout.getEditText().getText().toString();
        String phoneNumber = phoneNumberInputLayout.getEditText().getText().toString();
        String workPlace = workPlaceInputLayout.getEditText().getText().toString();
        String title = titleInputLayout.getEditText().getText().toString();

        // editText에 내용을 입력하고 저장 버튼을 다시 클릭 했을 때 표시된 에러 표시를 지워줌
        nameInputLayout.setErrorEnabled(false);
        emailInputLayout.setErrorEnabled(false);
        phoneNumberInputLayout.setErrorEnabled(false);
        workPlaceInputLayout.setErrorEnabled(false);
        titleInputLayout.setErrorEnabled(false);

        // 내용 비워져있을 때 에러 표시 isValidate를 false로 리턴하여 다음 동작 못하게 설정한다.
        if(name.isBlank()) {
            nameInputLayout.setError(getString(R.string.name_input_alert));
            isValidate = false;
        }

        if(email.isBlank()) {
            emailInputLayout.setError(getString(R.string.email_input_alert));
            isValidate = false;
        } else { // 이메일 값이 비어있지 않는데 이메일 형식이 안맞을 때
            if(!EmailValidator.isValidEmail(email)) {
                emailInputLayout.setError(getString(R.string.email_valid_input_alert));
                isValidate = false;
            }
        }
        // 전화번호 항목 비워져있을 때
        if(phoneNumber.isBlank()) {
            phoneNumberInputLayout.setError(getString(R.string.phone_input_alert));
            isValidate = false;
        }
        // 직장명 비워져있을 때
        if(workPlace.isBlank()) {
            workPlaceInputLayout.setError(getString(R.string.workplace_input_alert));
            isValidate = false;
        }
        // 직함 비워져 있을 때
        if(title.isBlank()) {
            titleInputLayout.setError(getString(R.string.title_input_alert));
            isValidate = false;
        }

        return isValidate;
    }
}