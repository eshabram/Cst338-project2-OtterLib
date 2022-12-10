package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityManageBookListBinding;

import java.util.List;
import java.util.Locale;

public class ManageBookList extends AppCompatActivity {
    private ActivityManageBookListBinding binding;
    private ProjectDatabase db;
    private List<Book> bookBank;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private String title;
    private String author;
    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageBookListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(ManageBookList.this);
        db.populateInitialData();
        bookBank = db.book().getAll();

        binding.addBook.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.book_title);
            ed2 = (EditText) findViewById(R.id.book_author);
            ed3 = (EditText) findViewById(R.id.book_genre);
            title = ed1.getText().toString();
            author = ed2.getText().toString();
            genre = ed3.getText().toString().toLowerCase();
            boolean flag = false;
            if (!title.equals("") && !author.equals("") && !genre.equals("")) { //check that something was entered
                if (genre.equalsIgnoreCase("computer science") ||
                        genre.equalsIgnoreCase("fiction") ||
                        genre.equalsIgnoreCase("memoir")) { // check for genres
//                    addBook(title, author, genre);
                    for (int i = 0; i < bookBank.size(); i++) { // loop through and check that title doesn't already exist
                        if (bookBank.get(i).getTitle().toLowerCase().equalsIgnoreCase(title)) {
                                flag = true;
                                break;
                        } else {
                            Toast toast = Toast.makeText(this, "Title Already Exists", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP, 0, 250);
                            toast.show();
                            Intent intent = new Intent(ManageBookList.this, ManagementScreen.class);
                            startActivity(intent);
                        }
                    }
                    if (!flag) {
                        db.book().addBook(new Book(" - " + title, author, genre, ""));
                        db.trans().addTransaction(new Transaction("Type: Book Added - ", title, 0));
                        Toast toast = Toast.makeText(this, "Title Was Successfully Added", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 250);
                        toast.show();
                        Intent intent = new Intent(ManageBookList.this, ManagementScreen.class);
                        startActivity(intent);
                    }
                } else {
                    Toast toast = Toast.makeText(this, "Genre Not Accepted", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(ManageBookList.this, ManagementScreen.class);
                    startActivity(i);
                }
            } else {
                Toast toast = Toast.makeText(this, "Not Enough Info", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
                Intent i = new Intent(ManageBookList.this, ManagementScreen.class);
                startActivity(i);
                }
        });
        // Title says it all
        binding.removeBook.setOnClickListener(v -> {
            ed4 = (EditText) findViewById(R.id.book_title_remove);
            String temp = ed4.getText().toString();
            temp = temp.toLowerCase(Locale.ROOT);
            if (!temp.equals("")) {
                if (db.book().getBook(temp) != null) {
                    db.book().delete(db.book().getBook(temp));
                    db.trans().addTransaction(new Transaction("Type: Book Removed - ", temp, 0));
                    Toast toast = Toast.makeText(this, R.string.done, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(ManageBookList.this, ManagementScreen.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, "Title Not In Database", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "No Title Entered", Toast.LENGTH_LONG);
                toast.show();
            }
        });
        // Title says it all
        binding.removeAll.setOnClickListener(v -> {
            db.book().deleteAll();
            db.trans().addTransaction(new Transaction("Type: All Books Removed - ", "admin", 0));
            Toast toast = Toast.makeText(this, R.string.futile, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            Intent i = new Intent(ManageBookList.this, ManagementScreen.class);
            startActivity(i);
        });
    }
}