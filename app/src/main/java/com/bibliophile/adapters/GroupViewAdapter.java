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

public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.MyViewHolder> {
    private List<MultiModel> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;

        public MyViewHolder(View view) {
            super(view);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);


        }
    }

    public GroupViewAdapter(Context context, List<MultiModel> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public GroupViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_group_view, parent, false);
        return new GroupViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewAdapter.MyViewHolder holder, int position) {
        Utilis_.imageFromUrl(mcontext, holder.profileImage, "http://images.statusfacebook.com/profile_pictures/profile-pictures/cool-funny-romantic-stylish-profile-pictures-for-whatsapp-facebook-245.jpg");

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
