package com.example.praktikumpamm8tugas.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.praktikumpamm8tugas.database.Contact;
import com.example.praktikumpamm8tugas.databinding.ActivityContactBinding;

import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private ActivityContactBinding binding;
    private ContactAdapter contactAdapter;
    private ContactViewModel contactViewModel;
    private Contact editContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleContact.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this);
        binding.recycleContact.setAdapter(contactAdapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                contactAdapter.setContacts(contacts);
            }
        });

        contactAdapter.setOnItemClickListener(new ContactAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Contact contact = contactAdapter.getContact(position);
                contactViewModel.delete(contact);
            }
        });

        contactAdapter.setEditClickListener(contact -> {
            binding.recycleContact.setVisibility(View.GONE);
            binding.layoutAdd.setVisibility(View.VISIBLE);

            editContact = contact;
            binding.etName.setText(contact.getName());
            binding.etNumber.setText(contact.getNumber());
            binding.etInstagram.setText(contact.getInstagram());
            binding.etGroup.setText(contact.getGroup());
        });

        binding.tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.recycleContact.getVisibility() == View.VISIBLE) {
                    binding.recycleContact.setVisibility(View.GONE);
                    binding.layoutAdd.setVisibility(View.VISIBLE);
                    clearData();
                } else {
                    binding.recycleContact.setVisibility(View.VISIBLE);
                    binding.layoutAdd.setVisibility(View.GONE);
                }
            }
        });

        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString();
                String number = binding.etNumber.getText().toString();
                String instagram = binding.etInstagram.getText().toString();
                String group = binding.etGroup.getText().toString();

                if (name.isEmpty() || number.isEmpty() || instagram.isEmpty() || group.isEmpty()) {
                    Toast.makeText(ContactActivity.this, "Please fill in the entire form", Toast.LENGTH_SHORT).show();
                } else {
                    if (editContact != null){
                        editContact.setName(name);
                        editContact.setNumber(number);
                        editContact.setInstagram(instagram);
                        editContact.setGroup(group);
                        contactViewModel.update(editContact);
                        editContact = null;

                        contactAdapter.notifyDataSetChanged();
                    }else {
                        Contact contact = new Contact(name, number, group, instagram);
                        contactViewModel.insert(contact);
                    }
                    clearData();
                    binding.recycleContact.setVisibility(View.VISIBLE);
                    binding.layoutAdd.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clearData() {
        binding.etName.setText("");
        binding.etNumber.setText("");
        binding.etInstagram.setText("");
        binding.etGroup.setText("");
    }
}