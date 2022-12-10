package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.databinding.ActivityAddUserBinding;

import java.util.List;
import java.util.Locale;

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
    private int privilege;
    private Spinner spinner;
    User deleteUser;
    private static ArrayAdapter<User> userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(AddUser.this);
        db.populateInitialData();
        userBank = db.user().getAll();
        userBank.add(0, new User("Select User To Delete:", "", 2, -1));

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
                    Intent i = new Intent(AddUser.this, ManagementScreen.class);
                    startActivity(i);
                } else {
                    Toast toast = Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                    Intent i = new Intent(AddUser.this, ManagementScreen.class);
                    startActivity(i);
                }
            }
        });
        spinner = binding.userSpinner;
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userBank);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(userAdapter);
        // delete one account
        binding.deleteAccount.setOnClickListener(v -> {
            String dUser = deleteUser.getUsername();
            db.user().delete(deleteUser);
            Intent i = new Intent(AddUser.this, ManagementScreen.class);
            db.trans().addTransaction(new Transaction("Type: User Deleted - ", dUser, 0));
            Toast toast = Toast.makeText(this, "User Deleted", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            startActivity(i);
        });
        binding.deleteAll.setOnClickListener(v -> {
            db.user().deleteAll();
//            db.addUser("admin", "p", 1);
            db.trans().addTransaction(new Transaction("Type: All Accounts Deleted - ", "admin", 0));
            Toast toast = Toast.makeText(this, R.string.futile, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
            Intent i = new Intent(AddUser.this, ManagementScreen.class);
            startActivity(i);
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                deleteUser = userBank.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void checkUser(String username, String password, int privilege) {
        boolean exist = false;
        boolean success;
        int count = 0;
        int messageResId = 20;
        Toast toast = null;
        for (int i = 0; i < userBank.size(); i++) {
            if (userBank.get(i).getUsername().equalsIgnoreCase(username)) {
                exist = true;
                count = i;
                break;
            }
        }
        if (!exist) {
            db.addUser(username, password, privilege);
            db.trans().addTransaction(new Transaction(
                    "Type: New Account - ", username, 0));
            messageResId = R.string.success;
            toast = Toast.makeText(this, "New Account: " + username, Toast.LENGTH_LONG);
        } else {
            messageResId = R.string.invalid;
            toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
        }
        toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
        Intent i = new Intent(AddUser.this, MainActivity.class);
        startActivity(i);
    }

}