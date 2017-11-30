package com.bibliophile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.custom.textdrawable.TextDrawable;
import com.bibliophile.datas.Constant;
import com.bibliophile.model.Testing;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/11/2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    private List<Testing> data;
    private Context mcontext;
    private int selectedItem = -1;
    private int lastPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView nameImage, iv_camera;
        public CardView cardQueRow;
        public RadioGroup rgAns;
        public CheckBox rbA, rbB, rbC, rbD;

        public MyViewHolder(View view) {
            super(view);
            nameImage = (ImageView) view.findViewById(R.id.nameImage);
            iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
            cardQueRow = (CardView) view.findViewById(R.id.layoutQueRow);
            //rgAns = (RadioGroup) view.findViewById(R.id.rgAns);
            rbA = (CheckBox) view.findViewById(R.id.rbA);
            rbB = (CheckBox) view.findViewById(R.id.rbB);
            rbC = (CheckBox) view.findViewById(R.id.rbC);
            rbD = (CheckBox) view.findViewById(R.id.rbD);


        }
    }

    public QuestionAdapter(Context context, List<Testing> data) {
        this.data = data;
        this.mcontext = context;

    }

    public void setSelecteditem(int selecteditem) {
        this.selectedItem = selecteditem;
        notifyDataSetChanged();
    }

    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_que, parent, false);
        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.MyViewHolder holder, final int position) {
        final String movie = data.get(position).getName();
        ViewGroup.LayoutParams paramsA =  holder.cardQueRow.getLayoutParams();
        paramsA.width = Utilis_.displayHeightWeight(mcontext, 100, Constant.WIDTH);
        paramsA.height=Utilis_.displayHeightWeight(mcontext, 100, Constant.HEIGHT);
        holder.cardQueRow.setLayoutParams(paramsA);
        ViewGroup.LayoutParams params = holder.nameImage.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        params.height = Utilis_.displayHeightWeight(mcontext, 50, Constant.HEIGHT);
        params.width = Utilis_.displayHeightWeight(mcontext, 100, Constant.WIDTH);
        holder.nameImage.setLayoutParams(params);
        TextDrawable drawable = TextDrawable.builder().beginConfig().textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .fontSize(30) /* size in px */
                .bold()
                .toUpperCase().endConfig()
                .buildRound(String.valueOf(position + 1), Color.GRAY);
        holder.iv_camera.setImageDrawable(drawable);
        Utilis_.imageFromUrl(mcontext, holder.nameImage, "http://static3.businessinsider.com/image/55ca2ba1371d2278018beb4b-690-346/screen%20shot%202015-08-11%20at%201.02.21%20pm.png");
        setChange(holder.rbA, holder,position);
        setChange(holder.rbB, holder,position);
        setChange(holder.rbC, holder,position);
        setChange(holder.rbD, holder,position);

//        setRadioButtonValue(holder.getAdapterPosition(), holder);
//        holder.rgAns.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//            }
//        });
    }

    public void setRadioButtonValue(int poo, QuestionAdapter.MyViewHolder holder) {
        if (data.get(poo).getValue() != null) {
            switch (data.get(poo).getValue().toUpperCase()) {
                case "A":
                    setCheck(holder.rbA, holder.rbB, holder.rbC, holder.rbD);
                    break;
                case "B":
                    setCheck(holder.rbB, holder.rbA, holder.rbC, holder.rbD);
                    break;
                case "C":
                    setCheck(holder.rbC, holder.rbB, holder.rbA, holder.rbD);
                    break;
                case "D":
                    setCheck(holder.rbD, holder.rbB, holder.rbC, holder.rbA);
                    break;
            }
        } else {
            holder.rbA.setChecked(false);
            holder.rbB.setChecked(false);
            holder.rbC.setChecked(false);
            holder.rbD.setChecked(false);
        }

    }
    private void setChange(final CheckBox a,final QuestionAdapter.MyViewHolder holder,final int poo) {
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a.getText().toString().toUpperCase().equals(data.get(poo).getValue())){
                    holder.rbA.setChecked(false);
                    holder.rbB.setChecked(false);
                    holder.rbC.setChecked(false);
                    holder.rbD.setChecked(false);

                    Toast.makeText(mcontext, "SKIP", Toast.LENGTH_SHORT).show();
                    data.get(poo).setValue("SKIP");
                }else {
                    holder.rbA.setChecked(false);
                    holder.rbB.setChecked(false);
                    holder.rbC.setChecked(false);
                    holder.rbD.setChecked(false);
                    a.setChecked(true);


                    data.get(poo).setValue(a.getText().toString());
                }



            }
        });

    }
    public void setCheck(CheckBox a, CheckBox b, CheckBox c, CheckBox d) {
        a.setChecked(true);
        b.setChecked(false);
        c.setChecked(false);
        d.setChecked(false);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
