package com.example.cristiana.workshop1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import com.example.cristiana.workshop1.model.Repository;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Cristiana on 4/4/2017.
 */

public class RepositoryActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        /* setare layout manager default */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /* setare adaptop */
        Adapter adapter = new Adapter(Repository.sMockRepository);
        mRecyclerView.setAdapter(adapter);

    }


    /* adaptor care leaga modelul la view */
    static class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<Repository> mData;

        public Adapter(List<Repository> mData) {
            this.mData = mData;
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
            private final LinearLayout mTopics;
            private final CheckBox mIsPublic;

            public ViewHolder(View itemView) {
                super(itemView);

                /* referinta catre toate elementele din view */
                mWatchersCount = (TextView) itemView.findViewById(R.id.WatchersCount);
                mNameAndOwner = (TextView) itemView.findViewById(R.id.NameAndOwner);
                mDescription = (TextView) itemView.findViewById(R.id.Description);
                mTopics = (LinearLayout) itemView.findViewById(R.id.Topics);
                mIsPublic = (CheckBox) itemView.findViewById(R.id.PublicCheckbox);
            }

            public void bind(Repository repository) {
                /* afisarea datelor din Repository */
                mWatchersCount.setText(String.valueOf(repository.getmWatchersCount()));
                /* itemView returneaza LinearLayout-ul din activity_repository */
                mNameAndOwner.setText(itemView.getContext().getString(R.string.repoNameAndOwner,
                        repository.getmName(), repository.getmOwner()));
                mDescription.setText(repository.getmDescription());
                mIsPublic.setChecked(!repository.ismIsPrivate());
            }
        }
    }
}
