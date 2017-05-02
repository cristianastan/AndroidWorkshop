package com.example.cristiana.workshop1.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Cristiana on 4/6/2017.
 */

public interface GitHub {
    /* primul end point -> info de login */
    @GET("/")
    Call<LoginData> checkAuth(@Header("Authorization") String auth);

    /* al doilea end point -> info de profil ale utilizatorului */
    @GET("/user")
    Call<ProfileData> getUserProfile(@Header("Authorization") String auth);

    @GET("/user/repos")
    Call<List<Repository>> getRepositories(@Header("Authorization") String auth);

    class Service {
        private static GitHub sInstance;

        public synchronized static GitHub Get() {
            if (sInstance == null)
                sInstance = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GitHub.class);

            return sInstance;
        }
    }
}
