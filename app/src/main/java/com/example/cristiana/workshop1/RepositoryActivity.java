package com.example.cristiana.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristiana.workshop1.model.GitHub;
import com.example.cristiana.workshop1.model.Repository;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cristiana on 4/4/2017.
 */

public class RepositoryActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        fetchRepository();

        /* setare layout manager default */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* setare adaptop */
        adapter = new Adapter();
        mRecyclerView.setAdapter(adapter);

    }

    private void fetchRepository() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Call<List<Repository>> callable = GitHub.Service.Get()
                .getRepositories(preferences.getString(Contract.Preferecnes.AUTH_HASH, null));

        callable.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {
                    List<Repository> repos = response.body();
                    adapter.setData(repos);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RepositoryActivity.this, "Services unavailable", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(RepositoryActivity.this, "No Internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* adaptor care leaga modelul la view */
    static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Repository> mData = new ArrayList<>();

        public void setData(List<Repository> repos) {
            for (Repository repo : repos) {
                mData.add(repo);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repository, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0;
        }

        /* pentru ca avem nevoie de mai multe tipuri de date */
        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView mWatchersCount;
            private final TextView mNameAndOwner;
            private final TextView mDescription;
            private final CheckBox mIsPublic;

            public ViewHolder(View itemView) {
                super(itemView);

                /* referinta catre toate elementele din view */
                mWatchersCount = (TextView) itemView.findViewById(R.id.WatchersCount);
                mNameAndOwner = (TextView) itemView.findViewById(R.id.NameAndOwner);
                mDescription = (TextView) itemView.findViewById(R.id.Description);
                mIsPublic = (CheckBox) itemView.findViewById(R.id.PublicCheckbox);
            }

            public void bind(Repository repository) {
                /* afisarea datelor din Repository */
                mWatchersCount.setText(String.valueOf(repository.getWatchersCount()));
                /* itemView returneaza LinearLayout-ul din activity_repository */
                mNameAndOwner.setText(itemView.getContext().getString(R.string.repoNameAndOwner,
                        repository.getName(), repository.getOwner().getLogin()));
                mDescription.setText(repository.getDescription());
                mIsPublic.setChecked(!repository.getPrivate());
            }
        }
    }
}
