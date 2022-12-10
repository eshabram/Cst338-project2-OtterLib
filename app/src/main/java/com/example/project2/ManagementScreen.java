package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.databinding.ActivityMainBinding;
import com.example.project2.databinding.ActivityManagementScreenBinding;

import java.util.List;

public class  ManagementScreen extends AppCompatActivity {
    private ActivityManagementScreenBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private List<Book> bookBank;
    private List<Transaction> transBank;
    private Spinner spinner;
    private static ArrayAdapter<Transaction> transAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagementScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        spinner = binding.transSpinner;

        db = ProjectDatabase.getInstance(ManagementScreen.this);
        db.populateInitialData();
        userBank = db.user().getAll();
        bookBank = db.book().getAll();
        binding.manageUser.setOnClickListener(v -> {
            Intent i = new Intent(ManagementScreen.this, AddUser.class);
            startActivity(i);
        });
        binding.manageBooks.setOnClickListener(v -> {
            Intent i = new Intent(ManagementScreen.this, ManageBookList.class);
            startActivity(i);
        });
        binding.viewTransactions.setOnClickListener(v -> {
            transBank = db.trans().getAll();
            transBank.add(0, new Transaction("Transactions:", "", 10));
            transAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transBank);
            transAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(transAdapter);
        });
        binding.deleteTrans.setOnClickListener(v -> {
            db.trans().deleteAll();
            transBank = db.trans().getAll();
            transBank.add(0, new Transaction("Transactions:", "", 10));
            transAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transBank);
            transAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(transAdapter);
            Toast toast = Toast.makeText(this, R.string.trans_delete, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
        });
        binding.signOut.setOnClickListener(v -> {
            Toast toast = Toast.makeText(this, R.string.goodbye, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            Intent i = new Intent(ManagementScreen.this, MainActivity.class);
            startActivity(i);
        });
    }

}