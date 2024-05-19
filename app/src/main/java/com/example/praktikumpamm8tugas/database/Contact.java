package com.example.praktikumpamm8tugas.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Contact implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "group")
    private String group;

    @ColumnInfo(name = "instagram")
    private String instagram;

    public Contact(String name, String number, String group, String instagram){
        this.name = name;
        this.number = number;
        this.group = group;
        this.instagram = instagram;
    }

    @Ignore
    public Contact(int id, String name, String number, String group, String instagram){
        this.id = id;
        this.name = name;
        this.number = number;
        this.group = group;
        this.instagram = instagram;
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        name = in.readString();
        number = in.readString();
        group = in.readString();
        instagram = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    public String getInstagram() {
        return instagram;
    }
    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(group);
        dest.writeString(instagram);
    }

}
