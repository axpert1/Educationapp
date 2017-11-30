package com.bibliophile.activitys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.model.BottomModel;

import java.util.List;

/**
 * Created by ANDROID on 9/25/2017.
 */

public class PakageAdapter extends RecyclerView.Adapter<PakageAdapter.MyViewHolder> {
    private List<BottomModel> moviesList;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView profileImage;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            //  profileImage = (ImageView) view.findViewById(R.id.profileImage);


        }
    }

    public PakageAdapter(Context context, List<BottomModel> moviesList) {
        this.moviesList = moviesList;
        this.mcontext = context;
    }

    @Override
    public PakageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_row, parent, false);
        return new PakageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PakageAdapter.MyViewHolder holder, final int position) {
        final BottomModel movie = moviesList.get(position);
        holder.name.setText(movie.getName());
        if (movie.getPrice().length()>0){
            holder.price.setText(mcontext.getString(R.string.rs) + " " + movie.getPrice());
        }



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}