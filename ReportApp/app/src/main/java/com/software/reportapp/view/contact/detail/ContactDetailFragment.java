package com.software.reportapp.view.contact.detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.reportapp.R;
import com.software.reportapp.databinding.FragmentContactDetailBinding;

public class ContactDetailFragment extends Fragment {
    private FragmentContactDetailBinding binding;

    public ContactDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentContactDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}