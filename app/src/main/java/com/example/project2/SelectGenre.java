package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.databinding.ActivityPlaceHoldBinding;
import com.example.project2.databinding.ActivitySelectGenreBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectGenre extends AppCompatActivity {
    private ActivitySelectGenreBinding binding;
    private ProjectDatabase db;
    private List<Book> bookBank;
    private ArrayAdapter<Book> bookAdapter;
    private Spinner gSpinner;
    private String str;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectGenreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(SelectGenre.this);
        db.populateInitialData();
        binding.topText.setText("Please select a genre:");
//        bookBank = db.book().getAll();
        binding.computerScience.setOnClickListener(v -> {
            bookAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bookBank);
            bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.genreSpinner.setAdapter(bookAdapter);
            flag = updateUI("computer science");
            if (flag == false) {
                Toast toast = Toast.makeText(this, R.string.no_book, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        });
        binding.fiction.setOnClickListener(v -> {
            str = "fiction";
        });
        binding.memoirs.setOnClickListener(v -> {
            str = "memoirs";
        });

    }
    private boolean updateUI(String str) {
        bookBank = db.book().getGenre(str);
        if (bookAdapter == null) {
            bookAdapter = new ArrayAdapter<>(this, R.layout.spin,
            R.id.spin_view, bookBank);
            gSpinner.setAdapter(bookAdapter);
            return true;
        } else {
            bookAdapter.clear();
            bookAdapter.addAll(bookBank);
            bookAdapter.notifyDataSetChanged();
            return false;
        }

    }
}