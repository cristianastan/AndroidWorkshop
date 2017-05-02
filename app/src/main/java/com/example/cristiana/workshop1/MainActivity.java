package com.example.cristiana.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristiana.workshop1.model.GitHub;
import com.example.cristiana.workshop1.model.LoginData;

import org.w3c.dom.Text;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mUsername, mPassword;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* preferences */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getString(Contract.Preferecnes.AUTH_HASH, null) != null) {
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

    public void goToProfileScreen() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private String errorMessage(int code) {
        String message = "An error occured";
        switch (code) {
            case 401: message = "Invalid username or password"; break;
            case 403: message = "Login limit has been reached"; break;
            case 404: message = "Page not found"; break;
            case 500: message = "Internal server error"; break;
            case 503: message = "Serice unavailable"; break;
            case 550: message = "Permission denied"; break;
        }

        return message;
    }

    private String failureMessage(Throwable t) {
        String message = "Connection error";

        if (t instanceof UnknownHostException)
            message = "No Internet connecion found";

        if (t instanceof TimeoutException)
            message = "Connection time expired";

        return message;
    }

    private void performLogin(final String username, final String password) {
        /* Salvare username si password pentru a nu le pasa intre activitati de fiecare date */
        final String authHash = Credentials.basic(username, password);

        // Make a network call and authenticate the user
        Call<LoginData> callable = GitHub.Service.Get().checkAuth(Credentials.basic(username, password));

        callable.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    preferences.edit()
                        .putString(Contract.Preferecnes.AUTH_HASH, authHash)
                        .putString(Contract.Preferecnes.USERNAME, username)
                        .apply();
                    goToProfileScreen();
                } else {
                    String message = errorMessage(response.code());
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                t.printStackTrace();

                String message = failureMessage(t);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
