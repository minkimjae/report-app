package com.software.reportapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.reportapp.databinding.ListItemContactBinding;
import com.software.reportapp.db.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> items = new ArrayList<>();

    public ContactAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemContactBinding binding = ListItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.mtrlListItemText.setText(items.get(position).name);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<Contact> data) {
        items = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ListItemContactBinding binding;

        public ViewHolder(@NonNull ListItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
