package com.example.project2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.DeleteTable;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Locale;

@Database(entities = {Book.class, User.class, Transaction.class}, version = 7, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {
    public static ProjectDatabase sInstance;
    public abstract UserDao user();
    public abstract BookDao book();
    public abstract TransactionDao trans();

    public static synchronized ProjectDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ProjectDatabase.class, "project2.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
    public void populateInitialData() {
        runInTransaction(() -> {
            if (user().count() == 0) {
                user().addUser(new User("admin", "p", 1, -1));
                user().addUser(new User("eshabram", "password", 0, -1));
                user().addUser(new User("abiblarz", "ich bin ein berliner", 0, -1));
            }
            if (book().count() == 0) {
                book().addBook(new Book(" - 1984",
                        "Orwell", "fiction", ""));
                book().addBook(new Book(" - The Fountainhead",
                        "Rand", "fiction", ""));
                book().addBook(new Book(" - OSETP",
                        "Arpaci-Dusseau", "computer science",""));
                book().addBook(new Book(" - Mastering Bitcoin", "Antonopoulos",
                        "computer science", ""));
                book().addBook(new Book(" - Becoming Michelle Obama",
                        "Obama", "memoir", ""));
            }
        });
    }
    public void addUser(String username, String password) {
        user().addUser(new User(username.toLowerCase(), password, 0, -1));
    }
    public void addUser(String username, String password, int privilege) {
        user().addUser(new User(username.toLowerCase(), password, privilege, -1));
    }
    public void addBook(String title, String author, String genre, String hold) {
        book().addBook(new Book(" - " + title, author, genre, hold));
    }
    public Book getBook(String title) {
        return book().getBook(title);
    }
}