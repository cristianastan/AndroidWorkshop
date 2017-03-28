package com.example.cristiana.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mUsername, mPassword;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* preferences */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("loggedIn", false)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        mUsername = (TextView) findViewById(R.id.username);
        mPassword = (TextView) findViewById(R.id.password);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Button loginButton = (Button) findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LoginButton) {
            performLogin(mUsername.getText().toString(), mPassword.getText().toString());
        }
    }


    private void performLogin(String username, String password) {
        //  TODO: make a network call and authenticate the user
        if ("password".equals(password)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().putBoolean("loggedIn", true).apply();

            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            finish();
        } else {
            mUsername.setError("Invalid Username");
            mPassword.setError("Invalid Password");
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
        }
    }
}
