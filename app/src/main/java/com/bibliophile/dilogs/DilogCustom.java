package com.bibliophile.dilogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bibliophile.R;
import com.bibliophile.activitys.JoinStudentListActivity;
import com.bibliophile.activitys.PakageAdapter;
import com.bibliophile.adapters.MultiSelectAdapter;
import com.bibliophile.model.BottomModel;
import com.bibliophile.model.MultiModel;
import com.bibliophile.others.RecyclerTouchListener;
import com.bibliophile.utilitys.Utilis_;

import java.util.List;

/**
 * Created by ANDROID on 9/12/2017.
 */

public class DilogCustom {

    private Context mContext;
    private static AlertDialog retryCustomAlert;
    private TextView txtFromGallery;
    private TextView txtFromCamera;
    private TextView txtCancel;
    private AlertDialog cameraAlert;
    private static Dialog dialog;
    Dialog mBottomSheetDialog;

    public interface onCallback {
        void onPositiveButton(boolean success, int which);

        void onNegativeButton(boolean success, int which);
    }

    public interface onCallbackBottom {
        void onSelectPackage(String name, String id,String price);


    }

    public interface onCallbackMultiItem {
        void onPositiveResult(boolean success, int which);

    }

    public interface onCallbackCameraGallry {
        void onPositiveResult(boolean success, int clickId);

    }

    public interface onNewGroup {
        void onCancel(boolean success, int which);

        void onSeccess(boolean success, int which);
    }

    // TODO: multiselect Recyclevew
    public void callDilogefullscreen(Context context, String title, final onCallbackMultiItem callback, final int which, final List<MultiModel> add, View.OnClickListener clicked) {
        mContext = context;
        dialog = new Dialog(mContext, R.style.AppTheme); // making dialog full screen
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.multiselect_dialog);
        TextView textView = (TextView) dialog.findViewById(R.id.dilogtext);
        textView.setText(title);
        TextView submit = (TextView) dialog.findViewById(R.id.txtDilogSubmit);
        submit.setOnClickListener(clicked);
        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MultiSelectAdapter multiSelectAdapter = new MultiSelectAdapter(mContext, add);
        multiSelectAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(multiSelectAdapter);
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//              //  callback.onPositiveResult(true, add.get(position).getName(), position, which);
//                callback.onPositiveResult(false,  which);
//                dialog.dismiss();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        ImageView dilogimage = (ImageView) dialog.findViewById(R.id.dilogimage);
        dilogimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onPositiveResult(false, which);
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialog.show();
    }


    // TODO: multiselect Recyclevew
    public void newGroupDilog(final Context context, final onNewGroup callback, final int which) {
        mContext = context;
        dialog = new Dialog(mContext, R.style.AppTheme); // making dialog full screen
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.new_group);
        TextView textView = (TextView) dialog.findViewById(R.id.dilogtext);
        FloatingActionButton nextFAB = (FloatingActionButton) dialog.findViewById(R.id.nextFAB);
        nextFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onSeccess(false, 0);
                dialog.dismiss();
                Utilis_.startActivity(context, JoinStudentListActivity.class);
            }
        });
        ImageView dilogimage = (ImageView) dialog.findViewById(R.id.dilogimage);
        dilogimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancel(false, 0);
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialog.show();
    }

    public void retryAlertDialog(Context mContext, String title, String msg, String firstButton, String SecondButton, View.OnClickListener secondButtonClick) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.retry_alert, null);
        dialogBuilder.setView(dialogView);

        TextView txtRATitle = (TextView) dialogView.findViewById(R.id.txtRATitle);
        TextView txtRAMsg = (TextView) dialogView.findViewById(R.id.txtRAMsg);
        TextView txtRAFirst = (TextView) dialogView.findViewById(R.id.txtRAFirst);
        TextView txtRASecond = (TextView) dialogView.findViewById(R.id.txtRASecond);
        View deviderView = (View) dialogView.findViewById(R.id.deviderView);

        if (title.length() == 0) {
            txtRATitle.setVisibility(View.GONE);
        } else {
            txtRATitle.setVisibility(View.VISIBLE);
            txtRATitle.setText(title);
        }

        txtRAMsg.setText(Html.fromHtml(msg));


        if (firstButton.length() == 0) {
            txtRAFirst.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRAFirst.setVisibility(View.VISIBLE);
            txtRAFirst.setText(firstButton);
        }

        if (SecondButton.length() == 0) {
            txtRASecond.setVisibility(View.GONE);
            deviderView.setVisibility(View.GONE);
        } else {
            txtRASecond.setVisibility(View.VISIBLE);
            txtRASecond.setText(SecondButton);
        }

        txtRAFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retryCustomAlert.dismiss();
            }
        });

        txtRASecond.setOnClickListener(secondButtonClick);

        retryCustomAlert = dialogBuilder.create();
        retryCustomAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        retryCustomAlert.show();
    }

    public void dismissCourseDilog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void dismissRetryAlert() {
        if (retryCustomAlert != null) {
            retryCustomAlert.dismiss();
        }
    }

    public void selectPhotoGalleryAlert(Context mContext, final onCallbackCameraGallry callBack) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        dialogBuilder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.profile_photo_alert, null);
        dialogBuilder.setView(dialogView);
        txtFromGallery = (TextView) dialogView.findViewById(R.id.txtFromGallery);
        txtFromCamera = (TextView) dialogView.findViewById(R.id.txtFromCamera);
        txtCancel = (TextView) dialogView.findViewById(R.id.txtCancel);
        txtFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraAlert.dismiss();
                callBack.onPositiveResult(true, R.id.txtFromGallery);
            }
        });
        txtFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraAlert.dismiss();
                callBack.onPositiveResult(true, R.id.txtFromCamera);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraAlert.dismiss();
            }
        });
        cameraAlert = dialogBuilder.create();
        cameraAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Setting dialogview
        Window window = cameraAlert.getWindow();
        window.setGravity(Gravity.CENTER);
        cameraAlert.show();
    }

    public void openBottomSheet(Context context, String title, final List<BottomModel> add, final onCallbackBottom callbackBottom) {
        mContext = context;
        View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtDilogTitle);
        txtTitle.setText(title);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        PakageAdapter pakageAdapter = new PakageAdapter(mContext, add);
        pakageAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(pakageAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetDialog.dismiss();
                callbackBottom.onSelectPackage(add.get(position).getName(), add.get(position).getId(),add.get(position).getPrice());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mBottomSheetDialog = new Dialog(mContext,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

//        txtTitle.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                mBottomSheetDialog.dismiss();
//            }
//        });
    }

}
