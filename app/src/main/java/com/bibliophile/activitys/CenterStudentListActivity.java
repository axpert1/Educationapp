package com.bibliophile.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.adapters.CenterStudentListAdapter;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;

public class CenterStudentListActivity extends AppCompatActivity {
    private RecyclerView recyclerCenterStudent;
    private TextView activityTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_student_list);

        initialize();

    }
    private void initialize() {
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.add_member));
        recyclerCenterStudent = (RecyclerView) findViewById(R.id.recyclerCenterStudent);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerCenterStudent.setLayoutManager(mLayoutManager);
        recyclerCenterStudent.setItemAnimator(new DefaultItemAnimator());
        recyclerCenterStudent.addItemDecoration(new com.bibliophile.others.DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        CenterStudentListAdapter multiSelectAdapter = new CenterStudentListAdapter(this, Utilis_.prepareMovieData());
        multiSelectAdapter.notifyDataSetChanged();
        recyclerCenterStudent.setAdapter(multiSelectAdapter);

        recyclerCenterStudent.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerCenterStudent, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
