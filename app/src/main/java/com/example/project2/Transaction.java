package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "transBank")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="resId")
    private int resId;

    @ColumnInfo(name ="data")
    private String data;

    @ColumnInfo(name ="who")
    private String who;

    public Transaction(String data, String who, int resId) {
        this.data = data;
        this.who = who;
        this.resId = resId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int type) {
        this.resId = resId;
    }

    public String toString() {
        return data + who;
    }

}