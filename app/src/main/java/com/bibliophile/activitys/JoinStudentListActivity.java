package com.bibliophile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.JoinStudentListAdapter;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;

public class JoinStudentListActivity extends AppCompatActivity {
    private RecyclerView recyclerStudent;
    private TextView activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initialize();

    }

    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.add_member));
        recyclerStudent = (RecyclerView) findViewById(R.id.recyclerStudent);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerStudent.setLayoutManager(mLayoutManager);
        recyclerStudent.setItemAnimator(new DefaultItemAnimator());
        recyclerStudent.addItemDecoration(new com.bibliophile.others.DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        JoinStudentListAdapter joinAdapter = new JoinStudentListAdapter(this, Utilis_.prepareMovieData());
        joinAdapter.notifyDataSetChanged();
        recyclerStudent.setAdapter(joinAdapter);
        recyclerStudent.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerStudent, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
