package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.databinding.ActivitySelectGenreBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class SelectGenre extends AppCompatActivity {
    private ActivitySelectGenreBinding binding;
    private ProjectDatabase db;
    private List<Book> bookBank = new ArrayList<>();
    private List<Book> bookBank1 = new ArrayList<>();
    private static ArrayAdapter<Book> bookAdapter;
    private String str = "";
    private boolean flag = false;
    private Bundle info = new Bundle();
    private Book book;
    private boolean spinnerFlag = false;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectGenreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(SelectGenre.this);
        db.populateInitialData();
        binding.topText.setText("Please select a genre:");

        // do a little spinner action right here
        spinner = binding.bookSpinner;
        bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookBank);
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(bookAdapter);

        // update spinner UI to genre selection
        binding.computerScience.setOnClickListener(v -> {
            bookBank1 = db.book().getGenre("computer science");
            bookBank.clear();
            for (int i = 0; i < bookBank1.size(); i++) {
                if (bookBank1.get(i).getWho().equalsIgnoreCase("")) {
                    bookBank.add(bookBank1.get(i));
                }
            }
            if (bookBank.size() > 0) {
                if (!bookBank.get(0).getTitle().equalsIgnoreCase("Computer Science:")) {
                    bookBank.add(0, new Book("Computer Science:", "", "", ""));
                }
            } else {
                bookBank.add(0, new Book("Computer Science:", "", "", ""));
            }
            bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookBank);
            bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(bookAdapter);
            spinnerFlag = false;
        });
        // update spinner UI to genre selection
        binding.fiction.setOnClickListener(v -> {
            bookBank1 = db.book().getGenre("fiction");
            bookBank.clear();
            for (int i = 0; i < bookBank1.size(); i++) {
                if (bookBank1.get(i).getWho().equalsIgnoreCase("")) {
                    bookBank.add(bookBank1.get(i));
                }
            }
            if (bookBank.size() > 0) {
                if (!bookBank.get(0).getTitle().equalsIgnoreCase("Fiction:")) {
                    bookBank.add(0, new Book("Fiction:", "", "", ""));
                }
            } else  {
                bookBank.add(0, new Book("Fiction:", "", "", ""));
            }
            bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookBank);
            bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(bookAdapter);
            spinnerFlag = false;
        });
        // update spinner UI to genre selection
        binding.memoirs.setOnClickListener(v -> {
            bookBank1 = db.book().getGenre("memoir");
            bookBank.clear();
            for (int i = 0; i < bookBank1.size(); i++) {
                if (bookBank1.get(i).getWho().equalsIgnoreCase("")) {

                    bookBank.add(bookBank1.get(i));
                }
            }
            if (bookBank.size() > 0) {
                if (!bookBank.get(0).getTitle().equalsIgnoreCase("Memoir:")) {
                    bookBank.add(0, new Book("Memoir:", "", "", ""));
                }
            } else {
                bookBank.add(0, new Book("Memoir:", "", "", ""));
            }
            bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookBank);
            bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(bookAdapter);
            spinnerFlag = false;
        });
        // deal with spinner selection.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (spinnerFlag == true) {
                        book = bookBank.get(i);
                        str = book.getTitle();
                        info.putString("mystr", str);
                        Intent intent = new Intent(SelectGenre.this, LoginPlaceHold.class);
                        intent.putExtras(info);
                        startActivity(intent);
                    } else { spinnerFlag = true; }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }
}