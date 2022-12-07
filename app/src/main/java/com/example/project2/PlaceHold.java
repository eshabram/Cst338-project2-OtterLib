package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityMainBinding;
import com.example.project2.databinding.ActivityPlaceHoldBinding;

import java.util.List;

public class PlaceHold extends AppCompatActivity {

    private ActivityPlaceHoldBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private List<Book> bookBank;
    private EditText ed1;
    private EditText ed2;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceHoldBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(PlaceHold.this);
        db.populateInitialData();
        userBank = db.user().getAll();
        bookBank = db.book().getAll();

        binding.topText.setText("Please enter credentials:");
        binding.signIn1.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.user_name);
            ed2 = (EditText) findViewById(R.id.password);
            ;
            username = ed1.getText().toString();
            password = ed2.getText().toString();
            if (!username.equals("") && !password.equals("")) {
                checkUser(username, password);
            } else {
                if (username.equals("")) {
                    Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(PlaceHold.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(PlaceHold.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void checkUser(String username, String password) {
        boolean exist = false;
        boolean pass = false;
        boolean success;
        int messageResId = 20;
        for (int i = 0; i < userBank.size(); i++) {
            if (userBank.get(i).getUsername().equalsIgnoreCase(username)) {
                exist = true;
                if (userBank.get(i).getPassword().equalsIgnoreCase(password)) {
                    pass = true;
                    break;
                }
                else {
                    break;
                }
            }
        }
        if (exist == false) {
            messageResId = R.string.no_username;
            Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
            Intent i = new Intent(PlaceHold.this, MainActivity.class);
            startActivity(i);
        } else if (exist == true && pass == false) {
            messageResId = R.string.incorrect_password;
            Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
            Intent i = new Intent(PlaceHold.this, MainActivity.class);
            startActivity(i);
        } else {
            messageResId = R.string.welcome;
            Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show();
            Intent i = new Intent(PlaceHold.this, MainActivity.class);
            startActivity(i);
        }
    }
}