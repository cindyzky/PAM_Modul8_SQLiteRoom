package com.example.praktikumpamm8tugas.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.example.praktikumpamm8tugas.database.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactDiffCallback extends DiffUtil.Callback {
    private final List<Contact> mOldContactList;
    private final List<Contact> mNewContactList;

    public ContactDiffCallback(List<Contact> oldContactList, List<Contact> newContactList) {
        this.mOldContactList = oldContactList != null ? oldContactList : new ArrayList<>();
        this.mNewContactList = newContactList != null ? newContactList : new ArrayList<>();
    }

    @Override
    public int getOldListSize() {
        return mOldContactList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewContactList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = mOldContactList.get(oldItemPosition);
        Contact newContact = mNewContactList.get(newItemPosition);
        return oldContact.getId()==(newContact.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = mOldContactList.get(oldItemPosition);
        Contact newContact = mNewContactList.get(newItemPosition);
        return Objects.equals(oldContact.getName(), newContact.getName()) &&
                Objects.equals(oldContact.getNumber(), newContact.getNumber()) &&
                Objects.equals(oldContact.getGroup(), newContact.getGroup()) &&
                Objects.equals(oldContact.getInstagram(), newContact.getInstagram());
    }
}
