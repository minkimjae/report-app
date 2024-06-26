package com.software.reportapp.view.contact.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.software.reportapp.R;
import com.software.reportapp.databinding.FragmentContactListBinding;
import com.software.reportapp.db.entity.Contact;
import com.software.reportapp.view.adapter.ContactAdapter;

public class ContactListFragment extends Fragment implements ContactAdapter.ContactAdapterClickListener {
    private FragmentContactListBinding binding;
    private FloatingActionButton faButton;

    private ContactListViewModel viewModel;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel 선언
        viewModel = new ViewModelProvider(requireActivity()).get(ContactListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContactListBinding.inflate(inflater, container, false);
        // NavController 초기화
        navController = NavHostFragment.findNavController(ContactListFragment.this);
        // Inflate the layout for this fragment
        initView();
        return binding.getRoot();
    }

    private void initView() {
        faButton = binding.faButton;

        faButton.setOnClickListener(view -> {
            navController.navigate(R.id.action_contactListFragment_to_addContactFragment2);
        });

        // RecyclerView 세팅
        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext());
        binding.contactRecyclerView.setLayoutManager(layoutManager);
        // 어댑터 세팅
        ContactAdapter adapter = new ContactAdapter(this);
        binding.contactRecyclerView.setAdapter(adapter);

        // LiveData 구독 - 쿼리문 결과에 변화가 생길 시에 해당 내용 감지하여 실행됨
        viewModel.getContactList().observe(getViewLifecycleOwner(), newData -> {
            adapter.setData(newData);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public void itemOnclick(Contact contact) {
        Bundle bundle = new Bundle();
        bundle.putInt("contactId", contact.getId());
        navController.navigate(R.id.action_contactListFragment_to_contactDetailFragment2, bundle);
    }
}