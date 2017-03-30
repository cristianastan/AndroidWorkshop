package com.example.cristiana.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristiana.workshop1.R;

public class ProfileActivity extends AppCompatActivity {

    TextView mEditText;
    boolean mShouldReturnData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView updatedDateView = (TextView) findViewById(R.id.updatedDate);
        SimpleDateFormat date = new SimpleDateFormat("EEE, MMM dd yyyy");
        Date updatedAt = new Date();
        updatedDateView.setText(date.format(updatedAt));

        TextView createdDateView = (TextView) findViewById(R.id.createdDate);
        Date createdAt = new Date("Wed, Mar 29 1995");
        createdDateView.setText(date.format(updatedAt));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.SignOut) {
            //Delete data from preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().remove("loggedIn").apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return true;
    }
}