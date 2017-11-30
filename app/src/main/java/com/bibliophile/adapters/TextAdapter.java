package com.bibliophile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bibliophile.R;
import com.bibliophile.model.Testing;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/8/2017.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.MyViewHolder> {
    private List<Testing> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;


        public MyViewHolder(View view) {
            super(view);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);


        }
    }

    public TextAdapter(Context context, List<Testing> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public TextAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roe_text, parent, false);
        return new TextAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TextAdapter.MyViewHolder holder, int position) {
        final String movie = data.get(position).getName();

        Utilis_.imageFromUrl(mcontext, holder.profileImage, "https://favim.com/orig/201107/26/cute-dog-pink-puppy-sunglasses-Favim.com-114474.jpg");

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
