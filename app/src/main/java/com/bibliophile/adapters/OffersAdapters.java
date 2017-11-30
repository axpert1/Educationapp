package com.bibliophile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bibliophile.R;
import com.bibliophile.custom.textdrawable.TextDrawable;
import com.bibliophile.model.Testing;

import java.util.List;


/**
 * Created by wingstud on 29-06-2017.
 */
public class OffersAdapters extends RecyclerView.Adapter<OffersAdapters.MyViewHolder> {
    private List<Testing> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView name;

        public MyViewHolder(View view) {
            super(view);
            name = (ImageView) view.findViewById(R.id.profileImage);


        }
    }

    public OffersAdapters(Context context, List<Testing> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roe_text, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String movie = data.get(position).getName();
        TextDrawable drawable = TextDrawable.builder().beginConfig().textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(30) /* size in px */
                .bold()
                .toUpperCase().endConfig()
                .buildRound(movie, Color.GRAY);


        //Apply to two ImageViews with different scale types
        holder.name.setImageDrawable(drawable);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
