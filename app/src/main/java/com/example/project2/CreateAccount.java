package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateAccount extends AppCompatActivity {
    private com.example.project2.databinding.ActivityCreateAccountBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private EditText ed1;
    private EditText ed2;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.project2.databinding.ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(CreateAccount.this);
        db.populateInitialData();
        userBank = db.user().getAll();

        binding.createAccount.setText("Create");
        binding.createAccount.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.user_name);
            ed2 = (EditText) findViewById(R.id.password);;
            username = ed1.getText().toString();
            password = ed2.getText().toString();
            if (!username.equals("") && !password.equals("")) {
                checkUser(username, password);
            } else {
                if (username.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(CreateAccount.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(CreateAccount.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void checkUser(String username, String password) {
        boolean exist = false;
        boolean success;
        int messageResId = 20;
        for (int i = 0; i < userBank.size(); i++) {
            if (userBank.get(i).getUsername().equalsIgnoreCase(username)) {
                exist = true;
                break;
            }
        }
        if (exist == false) {
            db.addUser(username, password);
                messageResId = R.string.success;
        } else {
            messageResId = R.string.invalid;
        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();                 Intent i = new Intent(CreateAccount.this, MainActivity.class);
        startActivity(i);
    }
}