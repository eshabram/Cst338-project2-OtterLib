package com.example.project2;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void addTransaction(Transaction t);

    @Query("SELECT COUNT(*) FROM transBank")
    int count();

    @Query("select * from transBank")
    List<Transaction> getAll();

    @Query("select * from transBank where resId = :resId")
    Transaction getTrans(int resId);

    @Query("DELETE FROM transBank")
    void deleteAll();

    @Update()
    void update(Transaction transaction);

}
