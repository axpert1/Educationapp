package com.bibliophile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.MyGroupAdapter;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;

public class GroupActivity extends AppCompatActivity {
    private RecyclerView recyclerGroup;
    private TextView activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        initialize();

    }

    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.my_group));
        recyclerGroup = (RecyclerView) findViewById(R.id.recyclerGroup);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerGroup.setLayoutManager(gridLayoutManager);
        recyclerGroup.setItemAnimator(new DefaultItemAnimator());
        MyGroupAdapter multiSelectAdapter = new MyGroupAdapter(this, Utilis_.prepareMovieData());
        multiSelectAdapter.notifyDataSetChanged();
        recyclerGroup.setAdapter(multiSelectAdapter);
        recyclerGroup.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerGroup, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Utilis_.startActivity(GroupActivity.this, GroupViewActivity.class);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
