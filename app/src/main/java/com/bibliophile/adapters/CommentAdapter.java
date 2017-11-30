package com.bibliophile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bibliophile.R;
import com.bibliophile.model.MultiModel;

import java.util.List;

/**
 * Created by ANDROID on 9/14/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private List<MultiModel> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout you, me;

        public MyViewHolder(View view) {
            super(view);
            you = (LinearLayout) view.findViewById(R.id.you);
            me = (LinearLayout) view.findViewById(R.id.me);


        }
    }

    public CommentAdapter(Context context, List<MultiModel> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_comment, parent, false);
        return new CommentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {
        final MultiModel movie = data.get(position);
        if (movie.isSelected()) {
            holder.you.setVisibility(View.VISIBLE);
            holder.me.setVisibility(View.GONE);

        } else {
            holder.you.setVisibility(View.GONE);
            holder.me.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
