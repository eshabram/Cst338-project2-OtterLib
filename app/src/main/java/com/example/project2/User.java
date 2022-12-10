package com.example.project2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userBank")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="privileged")
    private int privileged;

    @ColumnInfo(name="bookRes")
    private int bookRes;

    public User(String username, String password, int privileged, int bookRes) {
        this.username = username;
        this.password = password;
        this.privileged = privileged;
        this.bookRes = bookRes;
    }

    public int getBookRes() {
        return bookRes;
    }

    public void setBookRes(int bookRes) {
        this.bookRes = bookRes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrivileged() {
        return privileged;
    }

    public void setPrivileged(int privileged) {
        this.privileged = privileged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
