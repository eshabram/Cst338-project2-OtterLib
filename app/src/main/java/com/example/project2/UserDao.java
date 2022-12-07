package com.example.project2;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(User u);

    @Query("SELECT COUNT(*) FROM userBank")
    int count();

    @Query("select * from userBank")
    List<User> getAll();

    @Query("select * from userBank where username =:user")
    User getUser(String user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM userBank")
    void deleteAll();
}
