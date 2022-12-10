package com.example.project2;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void addBook(Book b);

    @Query("SELECT COUNT(*) FROM bookBank")
    int count();

    @Query("select * from bookBank")
    List<Book> getAll();

    @Query("select * from bookBank where title = :title")
    Book getBook(String title);

    @Query("select * from bookBank where genre = :genre")
    List<Book> getGenre(String genre);

    @Query("select * from bookBank where who = :who")
    List<Book> getHold(String who);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM bookBank")
    void deleteAll();

    @Update()
    void update(Book book);
}