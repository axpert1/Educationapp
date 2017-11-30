package com.bibliophile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.GroupViewAdapter;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;

public class GroupViewActivity extends AppCompatActivity {
    private TextView activityTitle;
    private RecyclerView recyclerViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        initialize();
    }

    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText("Group name");
        recyclerViewGroup = (RecyclerView) findViewById(R.id.recyclerGroupView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewGroup.setLayoutManager(mLayoutManager);
        recyclerViewGroup.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGroup.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        GroupViewAdapter multiSelectAdapter = new GroupViewAdapter(this, Utilis_.prepareMovieData());
        multiSelectAdapter.notifyDataSetChanged();
        recyclerViewGroup.setAdapter(multiSelectAdapter);
        recyclerViewGroup.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewGroup, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Utilis_.startActivity(GroupViewActivity.this, CommentActivity.class);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
