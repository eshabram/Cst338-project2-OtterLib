package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.databinding.ActivityLoginPlaceHoldBinding;

import java.util.ArrayList;
import java.util.List;

public class LoginPlaceHold extends AppCompatActivity {
    private ActivityLoginPlaceHoldBinding binding;
    private ProjectDatabase db;
    private List<User> userBank;
    private List<Transaction> transBank;
    private EditText ed1;
    private EditText ed2;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPlaceHoldBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = ProjectDatabase.getInstance(LoginPlaceHold.this);
        db.populateInitialData();
        userBank = db.user().getAll();
        transBank = db.trans().getAll();

        Bundle extras = getIntent().getExtras();
        String passedStr = extras.getString("mystr");
        binding.confirmButton.setOnClickListener(v -> {
            ed1 = (EditText) findViewById(R.id.user_name);
            ed2 = (EditText) findViewById(R.id.password);;
            username = ed1.getText().toString();
            password = ed2.getText().toString();
            if (!username.equals("") && !password.equals("")) {
                checkUser(username, password, passedStr);
            } else {
                if (username.equals("")) {
                    Toast toast = Toast.makeText(this, R.string.no_user, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, R.string.no_pass, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 0, 250);
                    toast.show();
                }
            }
        });
    }
    public void checkUser(String username, String password, String passedStr) {
        boolean exist = false;
        boolean success = false;
        boolean hasBookRes = false;
        int messageResId = 20;
        String tStr = "";
        for (int i = 0; i < userBank.size(); i++) {
            if (userBank.get(i).getUsername().equalsIgnoreCase(username)) {
                if (db.user().getUser(username).getBookRes() < 0) {
                    if (db.book().getBook(passedStr).getWho().equalsIgnoreCase("")) {
                        exist = true;
                        success = true;
                        Book book = db.book().getBook(passedStr);
                        User user = db.user().getUser(username);
                        book.setWho(username);
                        db.book().update(book);
                        Transaction t;
                        int resId = transBank.size() + 1;
                        db.trans().addTransaction(t = new Transaction("Type: Book-hold placed - ",
                                userBank.get(i).getUsername(), resId));
                        user.setBookRes(resId);
                        db.user().update(user);
                        tStr = Integer.toString(resId);
                        t.setResId(resId);
                        db.trans().update(t);
                        break;
                    } else {
                        exist = true;
                    }
                } else {
                    hasBookRes = true;
                }
            }
        }
        if (!hasBookRes) {
            if (exist && success) {
                Toast toast = Toast.makeText(this, "Hold Placed: " + username + " - "
                        + passedStr + " - " + tStr, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            } else if (exist) {
                messageResId = R.string.book_held;
                Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            } else {
                messageResId = R.string.hold_not_placed;
                Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this, "User Already Has Hold", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 250);
            toast.show();
        }
        Intent i = new Intent(LoginPlaceHold.this, MainActivity.class);
        startActivity(i);
    }

}