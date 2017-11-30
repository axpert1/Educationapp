package com.bibliophile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bibliophile.R;
import com.bibliophile.model.MultiModel;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/14/2017.
 */

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.MyViewHolder> {
    private List<MultiModel> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImage;

        public MyViewHolder(View view) {
            super(view);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);


        }
    }

    public MyGroupAdapter(Context context, List<MultiModel> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public MyGroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mygroup, parent, false);
        return new MyGroupAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyGroupAdapter.MyViewHolder holder, int position) {
        final String movie = data.get(position).getName();
        Utilis_.imageFromUrl(mcontext, holder.profileImage, "http://images.statusfacebook.com/profile_pictures/profile-pictures/cool-funny-romantic-stylish-profile-pictures-for-whatsapp-facebook-245.jpg");

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
