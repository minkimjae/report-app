package com.software.reportapp.view.contact.add;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.software.reportapp.databinding.FragmentAddContactBinding;
import com.software.reportapp.db.entity.Contact;
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
                new AlertDialog.Builder(getContext())
                        .setTitle("연락처 추가")
                        .setMessage("취소하시면 모든 내용이 초기화 됩니다. 취소하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nameInputLayout.getEditText().setText("");
                                emailInputLayout.getEditText().setText("");
                                phoneNumberInputLayout.getEditText().setText("");
                                workPlaceInputLayout.getEditText().setText("");
                                titleInputLayout.getEditText().setText("");
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
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
            nameInputLayout.setError("이름을 입력해 주세요");
            isValidate = false;
        }

        if(email.isBlank()) {
            emailInputLayout.setError("이메일을 입력해 주세요");
            isValidate = false;
        }

        if(phoneNumber.isBlank()) {
            phoneNumberInputLayout.setError("전화번호를 입력해 주세요");
            isValidate = false;
        }

        if(workPlace.isBlank()) {
            workPlaceInputLayout.setError("직장을 입력해 주세요");
            isValidate = false;
        }

        if(title.isBlank()) {
            titleInputLayout.setError("직함을 입력해 주세요");
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
}
