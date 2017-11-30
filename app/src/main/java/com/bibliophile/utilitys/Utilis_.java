package com.bibliophile.utilitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bibliophile.R;
import com.bibliophile.custom.textdrawable.TextDrawable;
import com.bibliophile.datas.Constant;
import com.bibliophile.model.MultiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ANDROID on 9/4/2017.
 */

public class Utilis_ {
    private static Dialog apiCallingProgressDialog = null;

    public static void progressDialog(Context mContext, String title) {
        if (apiCallingProgressDialog == null) {

            apiCallingProgressDialog = new Dialog(mContext);
            apiCallingProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            apiCallingProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            apiCallingProgressDialog.setContentView(R.layout.progress_alert);

            TextView txtProgressMsg = (TextView) apiCallingProgressDialog.findViewById(R.id.txtProgressMsg);
            txtProgressMsg.setText(title);

            ProgressBar progressBar = (ProgressBar) apiCallingProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.gray), PorterDuff.Mode.MULTIPLY);

            apiCallingProgressDialog.setCancelable(false);
            apiCallingProgressDialog.show();
        }
    }

    public static void dismissProgressDialog() {
        if (apiCallingProgressDialog != null) {
            apiCallingProgressDialog.dismiss();
            apiCallingProgressDialog = null;
        }
    }


    public static void imageFromUrl(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .priority(Priority.IMMEDIATE)
                .error(R.mipmap.ic_launcher)
                .fallback(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static int displayHeightWeight(Context context, int persent, int type) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (Constant.HEIGHT == type) {
            return (displayMetrics.heightPixels * persent) / 100;//height
        } else {
            return (displayMetrics.widthPixels * persent) / 100;//width
        }
    }

    public static String getMACAddress(Context context) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }


    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(context);
        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);
        blurOutput.copyTo(bitmap);
        renderScript.destroy();
        return bitmap;
    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];
        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);
        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    //to show a center toast.
    public static void showCenterToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static int getAge(String dob) {
        int age;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date parse = null;
        try {
            parse = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        int DOByear = c.get(Calendar.YEAR);
        int DOBmonth = (c.get(Calendar.MONTH) + 1);
        int DOBday = c.get(Calendar.DATE);
        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);
        age = currentYear - DOByear;
        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }

    //compress image like whats up
    public static String compressImage(String imageUri) {
        String filePath = imageUri;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
//      max Height and width values of the compressed image is taken as 816x612
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
//      width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;
            }
        }
//      setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static String getintentString(String key, Intent intent) {
        String output ;
        Bundle b = intent.getExtras();
        if (b != null) {
            output = (String) b.get(key);
            return output;
        }
        return null;
    }

    public static void hideSoftKeyboard(Context context) {
        try {

            Activity activity = (Activity) context;
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {

        }

    }

    public static void finish_previous_activities(Context context, Class<?> class1) {
        Intent intent = new Intent(context, class1);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    //to start any activity.
    public static void startActivity(Context context, Class<?> class1) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, class1);
        ((Activity) context).startActivity(intent);
    }

    //to start any activity.
    public static void startActivityPutValue(Context context, Class<?> class1, String value) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, class1);
        intent.putExtra(Constant.INTENT_NAME, value);
        ((Activity) context).startActivity(intent);
    }

    //to start any activity.
    public static void startActivityWithFinish(Context context, Class<?> class1) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(context, class1);
        ((Activity) context).startActivity(intent);
        ((Activity) context).finish();
    }

    public static void setmultiSlect(Context mcontext, boolean b, ImageView imageView, String s) {
        if (b) {


            imageView.setBackgroundColor(R.color.gray);
            imageView.setImageResource(R.mipmap.ic_action_done);
        } else {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRoundRect(s, mcontext.getResources().getColor(R.color.gray), 5);
            imageView.setBackgroundColor(R.color.white);
            imageView.setImageDrawable(drawable);
        }
    }

    // TODO  TEST FOR FUNCTION
    public static List<MultiModel> prepareMovieData() {
        List<MultiModel> multiModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MultiModel movie = new MultiModel("ABCDEGHIJKL", "ANDROID", false);
            multiModels.add(movie);
        }
        return multiModels;
    }

    // TODO  TEST FOR FUNCTION
    public static List<MultiModel> prepareMovieData2() {
        List<MultiModel> multiModels = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            if (i == 2 || i == 5 || i == 8) {
                multiModels.add(new MultiModel("ABCDEGHIJKL", "ANDROID", true));
            } else {
                multiModels.add(new MultiModel("ABCDEGHIJKL", "ANDROID", false));
            }


        }

        return multiModels;
    }

    public static Drawable tintColor(Context context, int iconID, int colorID) {
        final Drawable upArrow = context.getResources().getDrawable(iconID);
        upArrow.setColorFilter(context.getResources().getColor(colorID), PorterDuff.Mode.SRC_ATOP);
        return upArrow;
    }

    public static boolean editTextSize(EditText editText) {
        if (editText.getText().toString().length() > 0) {
            return true;
        }
        return false;
    }

    public static String getEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static String getTextfromRadioGroup(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioSexButton = (RadioButton) radioGroup.findViewById(selectedId);
        return radioSexButton.getText().toString();
    }

    public static int getIDRadioGroup(RadioGroup radioGroup) {
        return radioGroup.getCheckedRadioButtonId();

    }

    public static boolean getEditTextBool(EditText editText) {
        if (editText.getText().toString().length() > 0) {
            return true;

        }
        return false;
    }

    public static boolean getTextBool(TextView textview) {
        if (textview.getText().toString().length() > 0) {
            return true;

        }
        return false;
    }

    public static int getDisplayHeight(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = Math.round(displayMetrics.heightPixels / displayMetrics.density);
        return height;
    }

    public static int getDisplayWidth(Context mContext) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = Math.round(displayMetrics.widthPixels / displayMetrics.density);
        return width;
    }

    public static String getCourse(List<MultiModel> add) {
        JSONArray earn_ = new JSONArray();

        for (int i = 0; i < add.size(); i++) {
            if (add.get(i).isSelected()) {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("_id", add.get(i).getActivated());

                    earn_.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


        return earn_.toString();
    }

    public static String getStringCourse(List<MultiModel> add) {
        String name = null;

        for (int i = 0; i < add.size(); i++) {
            if (add.get(i).isSelected()) {
                if (name != null) {
                    name = name + ", " + add.get(i).getName();
                } else {
                    name = add.get(i).getName();
                }

            }


        }


        return name;
    }

    public static boolean getCourseNotEmpety(List<MultiModel> add) {
        JSONArray earn_ = new JSONArray();

        for (int i = 0; i < add.size(); i++) {
            if (add.get(i).isSelected()) {

                return true;
            }


        }


        return false;
    }

    public static RequestBody toRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static MultipartBody.Part sendMultPartImage(String picturePath) {
        if (picturePath != null) {
            File file = new File(picturePath);

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            return
                    MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);
        }
        return null;
    }

    public static void setTextValueNA(TextView textView, String value) {
        if (value != null && value.length() > 0) {
            textView.setText(value);
        } else {
            textView.setText("N/A");
        }

    }
    public static void setRadioGroup(RadioGroup radioGroup, String value) {
        try {
            int getChild = radioGroup.getChildCount();
            //   Log.d("getchild", "" + getChild);
            for (int i = 0; i < getChild; i++) {
                int selectedId = radioGroup.getChildAt(i).getId();
                RadioButton radioSexButton = (RadioButton) radioGroup.findViewById(selectedId);
                // Log.d("getchild", "" + radioSexButton.getText().toString());
                if (radioSexButton.getText().toString().equals(value)) {
                    radioSexButton.setChecked(true);
                }
                // Log.d("getchild", "" + getChild);
            }


        } catch (NullPointerException e) {
            Log.d("Null_Pointer", e.toString());
        }

    }
    public static String getTextfromRadioGroup2(RadioGroup radioGroup) {
        try {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioSexButton = (RadioButton) radioGroup.findViewById(selectedId);
            return radioSexButton.getText().toString();
        } catch (NullPointerException e) {
            return "SKIP";
        }

    }
}
