package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityAddUserBinding;

import java.util.List;

public class AddUser extends AppCompatActivity {
    private ActivityAddUserBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private String username;
    private String password;
//    private String temp;
    private int privilege; // add some parseIn shit here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(AddUser.this);
        db.populateInitialData();
        userBank = db.user().getAll();

        binding.createAccount.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.user_name);
            ed2 = (EditText) findViewById(R.id.password);;
            ed3 = (EditText) findViewById(R.id.privilege);
            username = ed1.getText().toString();
            password = ed2.getText().toString();
            String temp = ed3.getText().toString();
            privilege = Integer.parseInt(temp);
            if (!username.equals("") && !password.equals("")) {
                checkUser(username, password, privilege);
            } else {
                if (username.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(AddUser.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(AddUser.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        binding.deleteAccount.setOnClickListener(v -> {
            ed4 = (EditText) findViewById(R.id.enter_user_delete);
            String temp = ed4.getText().toString();
            if (!temp.equalsIgnoreCase("")) {
                if (db.user().getUser(temp) != null) {
                    db.user().delete(db.user().getUser(temp));
                    Toast toast = Toast.makeText(this, R.string.done, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(AddUser.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, R.string.invalid_username, Toast.LENGTH_LONG);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        binding.deleteAll.setOnClickListener(v -> {
            db.user().deleteAll();
//            db.addUser("admin", "p", 1);
            Toast toast = Toast.makeText(this, R.string.futile, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            Intent i = new Intent(AddUser.this, ManagementScreen.class);
            startActivity(i);
        });
    }
    public void checkUser(String username, String password, int privilege) {
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
            db.addUser(username, password, privilege);
            messageResId = R.string.success;
        } else {
            messageResId = R.string.invalid;
        }
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
        Intent i = new Intent(AddUser.this, MainActivity.class);
        startActivity(i);
    }
}