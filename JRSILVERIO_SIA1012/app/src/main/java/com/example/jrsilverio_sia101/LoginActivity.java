package com.example.jrsilverio_sia101;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText textLoginUsername = findViewById(R.id.textLoginUsername);
        EditText textLoginPassword = findViewById(R.id.textLoginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textLoginUsername.getText().toString();
                String password = textLoginPassword.getText().toString();

                if (isValidCredentials(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivty.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView btn=findViewById(R.id.textViewSignUp);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    private boolean isValidCredentials(String username, String password) {
        List<User> validUsers = getValidUsersFromDatabaseOrService();

        for (User user : validUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    private List<User> getValidUsersFromDatabaseOrService() {
        List<User> validUsers = new ArrayList<>();
        validUsers.add(new User("johnrey", "password123"));
        validUsers.add(new User("user2", "password456"));
        String storedUsername = getStoredUsername();
        String storedPassword = getStoredPassword();

        if (!storedUsername.isEmpty() && !storedPassword.isEmpty()) {
            validUsers.add(new User(storedUsername, storedPassword));
        }

        return validUsers;
    }

    private String getStoredUsername() {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        return preferences.getString("username", "");
    }

    private String getStoredPassword() {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        return preferences.getString("password", "");
    }
}