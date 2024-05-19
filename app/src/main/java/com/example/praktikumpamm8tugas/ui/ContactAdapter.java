package com.example.praktikumpamm8tugas.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikumpamm8tugas.database.Contact;
import com.example.praktikumpamm8tugas.databinding.ItemContactBinding;
import com.example.praktikumpamm8tugas.helper.ContactDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private final Context context;
    private List<Contact> contactList;
    private static ClickListener clickListener;
    private EditClickListener editClickListener;
    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setContacts(List<Contact> contacts) {
        if (contacts == null) {
            contacts = new ArrayList<>();
        }
        if (contactList == null) {
            contactList = new ArrayList<>();
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ContactDiffCallback(this.contactList, contacts));
        this.contactList.clear();
        this.contactList.addAll(contacts);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final Contact contact = contactList.get(position);
        holder.binding.tvName.setText(contact.getName());
        holder.binding.tvNumber.setText(contact.getNumber());

        holder.binding.tvCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contact.getNumber()));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            context.startActivity(intent);
        });

        holder.binding.tvMessage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("sms:" + contact.getNumber()));
            context.startActivity(intent);
        });

        holder.binding.tvWhatsapp.setOnClickListener(v -> {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + contact.getNumber() + "&text=";
            sendIntent.setData(Uri.parse(url));
            context.startActivity(sendIntent);
        });

        holder.binding.contactLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailContactActivity.class);
            intent.putExtra("contact", contact);
            context.startActivity(intent);
        });

        holder.binding.tvEdit.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(contact);
            }
        });

        holder.binding.tvDelete.setOnClickListener(v -> {
            clickListener.onItemClick(position, v);
        });
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public Contact getContact(int position) {
        return contactList.get(position);
    }


    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ItemContactBinding binding;
        private ClickListener clickListener;

        public ContactViewHolder(ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.tvDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ContactAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setEditClickListener(EditClickListener editClickListener) {
        this.editClickListener = editClickListener;
    }

    public interface EditClickListener {
        void onEditClick(Contact contact);
    }
}

