package com.example.praktikumpamm8tugas.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.praktikumpamm8tugas.database.Contact;
import com.example.praktikumpamm8tugas.database.ContactDAO;
import com.example.praktikumpamm8tugas.database.ContactRoomDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ContactRepository {
    private final ContactDAO contactDao;
    private final ExecutorService executorService;


    public ContactRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ContactRoomDB db = ContactRoomDB.getDatabase(application);
        contactDao = db.contactDAO();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public void insert(final Contact contact) {
        executorService.execute(() -> contactDao.insertContact(contact));
    }

    public void update(final Contact contact) {
        executorService.execute(() -> contactDao.updateContact(contact));
    }

    public void delete(final Contact contact) {
        executorService.execute(() -> contactDao.deleteContact(contact));
    }


}
