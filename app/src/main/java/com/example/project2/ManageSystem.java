package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityManageSystemBinding;

import java.util.List;

public class ManageSystem extends AppCompatActivity {

    private ActivityManageSystemBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private List<User> bookBank;
    private EditText ed1;
    private EditText ed2;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageSystemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(ManageSystem.this);
        db.populateInitialData();
        userBank = db.user().getAll();

        binding.manageSystem.setText("Manage System");
        binding.manageSystem.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.user_name);
            ed2 = (EditText) findViewById(R.id.password);;
            username = ed1.getText().toString();
            password = ed2.getText().toString();
            if (!username.equals("") && !password.equals("")) {
                checkAdmin(username, password);
            } else {
                if (username.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(ManageSystem.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(ManageSystem.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
    public void checkAdmin(String username, String password) {
        boolean privileged = false;
        int messageResId = 20;
        for (int i = 0; i < userBank.size(); i++) {
            if (userBank.get(i).getUsername().equalsIgnoreCase(username)) {
                if (userBank.get(i).getPassword().equalsIgnoreCase(password)) {
                    if (userBank.get(i).getPrivileged() == 1) {
                        messageResId = R.string.command;
                        privileged = true;
                        break;
                    } else {messageResId = R.string.not_privileged; break;}
                } else {messageResId = R.string.incorrect_password; break;}
            } messageResId = R.string.no_username;
        }
        if (privileged) {
            Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            Intent i = new Intent(ManageSystem.this, ManagementScreen.class);
            startActivity(i);
        } else {
            Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();            Intent i = new Intent(ManageSystem.this, MainActivity.class);
            startActivity(i);
        }
    }
}