package com.bibliophile.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.model.MultiModel;
import com.bibliophile.model.StudentListModel;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {
    private List<StudentListModel.StudentList> data;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView name;

        public MyViewHolder(View view) {
            super(view);
            profileImage = (ImageView) view.findViewById(R.id.profileImage);
            name = (TextView) view.findViewById(R.id.name);


        }
    }

    public StudentListAdapter(Context context, List<StudentListModel.StudentList> data) {
        this.data = data;
        this.mcontext = context;

    }

    @Override
    public StudentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_student_list, parent, false);
        return new StudentListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentListAdapter.MyViewHolder holder, int position) {
        StudentListModel.StudentList studentList=data.get(position);
        holder.name.setText(studentList.studentName);
        Utilis_.imageFromUrl(mcontext, holder.profileImage, "http://images.statusfacebook.com/profile_pictures/profile-pictures/cool-funny-romantic-stylish-profile-pictures-for-whatsapp-facebook-245.jpg");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
