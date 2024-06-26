package com.software.reportapp.view.contact.add;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.software.reportapp.R;
import com.software.reportapp.databinding.FragmentAddContactBinding;
import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.utill.EmailValidator;
import com.software.reportapp.view.MainActivity;
import com.software.reportapp.view.contact.list.ContactListFragment;

public class AddContactFragment extends Fragment {
    private FragmentAddContactBinding binding;
    private TextInputLayout emailInputLayout;
    private TextInputLayout nameInputLayout;
    private TextInputLayout phoneNumberInputLayout;
    private TextInputLayout titleInputLayout;
    private TextInputLayout workPlaceInputLayout;
    private Button saveButton;
    private Button cancelButton;
    private AddContactViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddContactViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddContactBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(AddContactFragment.this);
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
                if(saveContactValidate()) { // 검증 값이 true 일 시 DB에 내용 저장
                    boolean result = saveContact();

                    if(result) {
                        Toast.makeText(getContext(), "저장에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        navController.popBackStack();
                    } else {
                        Toast.makeText(getContext(), "저장에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancelButton = binding.cancelButton;
        cancelButton.setOnClickListener(new View.OnClickListener() { // 취소 버튼 클릭 시
            @Override
            public void onClick(View view) {
                showAddCancelDialog();
            }
        });
    }

    private boolean saveContactValidate() {
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

    private boolean saveContact() {
        String name = nameInputLayout.getEditText().getText().toString();
        String email = emailInputLayout.getEditText().getText().toString();
        String phoneNumber = phoneNumberInputLayout.getEditText().getText().toString();
        String workPlace = workPlaceInputLayout.getEditText().getText().toString();
        String title = titleInputLayout.getEditText().getText().toString();

        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setWorkPlace(workPlace);
        contact.setTitle(title);

        return viewModel.insertContact(contact);
    }

    private void showAddCancelDialog() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getString(R.string.cancel_check_title))
                .setMessage(getString(R.string.cancel_check_massage))
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navController.popBackStack();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

}
