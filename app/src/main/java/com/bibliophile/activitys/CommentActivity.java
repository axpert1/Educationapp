package com.bibliophile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.CommentAdapter;
import com.bibliophile.utilitys.Utilis_;

public class CommentActivity extends AppCompatActivity {
    private TextView activityTitle;
    private RecyclerView recyclerViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initialize();
    }

    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText("View All");
        recyclerViewComment = (RecyclerView) findViewById(R.id.recyclerViewComment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        recyclerViewComment.setLayoutManager(layoutManager);
        recyclerViewComment.setItemAnimator(new DefaultItemAnimator());

        CommentAdapter multiSelectAdapter = new CommentAdapter(this, Utilis_.prepareMovieData2());
        multiSelectAdapter.notifyDataSetChanged();
        recyclerViewComment.setAdapter(multiSelectAdapter);

    }
}
