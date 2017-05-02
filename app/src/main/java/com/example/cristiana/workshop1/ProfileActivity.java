package com.example.cristiana.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import android.net.Credentials;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristiana.workshop1.R;
import com.example.cristiana.workshop1.model.GitHub;
import com.example.cristiana.workshop1.model.ProfileData;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    boolean mShouldReturnData;
    private ProfileData mProfileData;

    ImageView mImage;
    TextView mName;
    TextView mOrganization;
    TextView mBio;
    TextView mLocation;
    TextView mEmail;
    TextView mCreatedAt;
    TextView mUpdatedAt;
    TextView mPublicRepos;
    TextView mPrivaterepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImage = (ImageView) findViewById(R.id.profileImage);
        mName = (TextView) findViewById(R.id.profileName);
        mOrganization = (TextView) findViewById(R.id.profileOrganization);
        mBio = (TextView) findViewById(R.id.profileBio);
        mLocation = (TextView) findViewById(R.id.profileLocation);
        mEmail = (TextView) findViewById(R.id.profileEmail);
        mCreatedAt = (TextView) findViewById(R.id.profileCreatedAt);
        mUpdatedAt = (TextView) findViewById(R.id.profileUpdatedAt);
        mPublicRepos = (TextView) findViewById(R.id.profilePublicRepos);
        mPrivaterepos = (TextView) findViewById(R.id.profilePrivateRepos);

        fetchProfile();

        Button viewRepoButton = (Button) findViewById(R.id.ViewRepositoriesButton);
        viewRepoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ViewRepositoriesButton) {
            Intent intent = new Intent(this, RepositoryActivity.class);
            startActivity(intent);
        }
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
            preferences.edit().remove(Contract.Preferecnes.AUTH_HASH).apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        return false;
    }

    private void updateUI(ProfileData profile) {
        mName.setText(profile.getName());
        mOrganization.setText(profile.getCompany());
        //mProfileData.setAvatarUrl(profile.getAvatarUrl());
        mBio.setText(profile.getBio());
        mLocation.setText(profile.getLocation());
        mEmail.setText(profile.getEmail());
        mCreatedAt.setText(profile.getCreatedAt());
        mUpdatedAt.setText(profile.getUpdatedAt());
        mPublicRepos.setText(String.valueOf(profile.getPublicRepos()));
        mPrivaterepos.setText(String.valueOf(profile.getOwnedPrivateRepos()));
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

    private void fetchProfile() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Call<ProfileData> callable = GitHub.Service.Get()
                .getUserProfile(preferences.getString(Contract.Preferecnes.AUTH_HASH, null));

        callable.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if (response.isSuccessful()) {
                    ProfileData profile = response.body();
                    updateUI(profile);
                } else {
                    String message = errorMessage(response.code());
                    Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();

                String message = failureMessage(t);
                Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}