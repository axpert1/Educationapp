package com.bibliophile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.model.MultiModel;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/12/2017.
 */
public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.MyViewHolder> {
    private List<MultiModel> moviesList;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView profileImage;
        public CheckBox checkbox;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);

        }
    }

    public MultiSelectAdapter(Context context, List<MultiModel> moviesList) {
        this.moviesList = moviesList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_multi, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MultiModel movie = moviesList.get(position);
        holder.name.setText(movie.getName());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    moviesList.get(position).setSelected(true);
                    Utilis_.setmultiSlect(mcontext, b, holder.profileImage, "!");


                } else {
                    moviesList.get(position).setSelected(false);
                    Utilis_.setmultiSlect(mcontext, b, holder.profileImage, String.valueOf(movie.getName().charAt(1)));

                }
            }
        });

        Utilis_.setmultiSlect(mcontext, false, holder.profileImage, String.valueOf(movie.getName().charAt(1)));

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}