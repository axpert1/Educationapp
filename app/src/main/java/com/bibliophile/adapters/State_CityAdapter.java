package com.bibliophile.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.model.GettersSetters;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/21/2017.
 */

public class State_CityAdapter extends RecyclerView.Adapter<State_CityAdapter.MyViewHolder> {
    private List<GettersSetters> moviesList;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textview;


        public MyViewHolder(View view) {
            super(view);
            textview = (TextView) view.findViewById(R.id.textview);

        }
    }

    public State_CityAdapter(Context context, List<GettersSetters> moviesList) {
        this.moviesList = moviesList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_state_and_city, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GettersSetters movie = moviesList.get(position);
        holder.textview.setText(movie.getName());
        holder.textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnData(movie.getId(),movie.getName()) ;
            }
        });

    }
    public void returnData(String id, String name) {
        Utilis_.hideSoftKeyboard(mcontext);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result_id", id);
        returnIntent.putExtra("result_name", name);
        ((Activity)mcontext).setResult(Activity.RESULT_OK, returnIntent);
        ((Activity)mcontext).finish();
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}