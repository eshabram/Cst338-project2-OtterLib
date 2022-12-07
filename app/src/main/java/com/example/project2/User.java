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

    public User(String username, String password, int privileged) {
        this.username = username;
        this.password = password;
        this.privileged = privileged;
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
        return "User{" +
                "userName='" + username;
    }
}
