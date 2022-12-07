package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project2.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private List<Book> bookBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = ProjectDatabase.getInstance(MainActivity.this);
        db.populateInitialData();
        userBank = db.user().getAll();
        bookBank = db.book().getAll();

//        db.book().delete(db.book().getBook("The Grapes Of Wrath"));
//        db.book().deleteAll();
//        db.user().deleteAll();


        binding.createAccount.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, CreateAccount.class);
            startActivity(i);
        });
        binding.placeHold.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SelectGenre.class);
            startActivity(i);
        });
        binding.manageSystem.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ManageSystem.class);
            startActivity(i);
        });
    }
}