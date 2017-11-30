package com.bibliophile.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.datas.SharedPref;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.loopjServcice.NetworkManager;
import com.bibliophile.model.BottomModel;
import com.bibliophile.model.CourseJson;
import com.bibliophile.model.MultiModel;
import com.bibliophile.model.PackageJson;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;

import static com.bibliophile.utilitys.Utilis_.toRequestBody;

/**
 * Created by ANDROID on 9/16/2017.
 */

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener, DilogCustom.onCallbackCameraGallry, NetworkManagerRetroFit.onCallback, DilogCustom.onCallbackMultiItem, NetworkManager.onCallback, DilogCustom.onCallbackBottom {

    private EditText edtName, edtEmail, edtPhone, edtAddress, edtPincode;
    private TextView txtCourse, txtState, txtCity, txtPackage, txtPayment;
    private LinearLayout llCity;

    private Button btnSubmit;
    private ImageView iv_camera, profilePic;

    private Context mContext;
    private DilogCustom dilogCustom;

    private int CAMERA = 500;
    private int RESULT_LOAD_IMAGE = 100;

    private String directoryPath = "/sdcard/bibliophile";
    private Uri mImageUri;

    private String picturePath = null;
    private boolean isFromCamera = false;

    private String state_id = "0", city_id = "0", packageID = "0";
    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;

    private List<MultiModel> multiModels;
    private List<BottomModel> mPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);
        mContext = AddStudentActivity.this;
        dilogCustom = new DilogCustom();
        initialize();
        apiInitialize();
    }


    private void initialize() {
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);

        txtCourse = (TextView) findViewById(R.id.txtCourse);
        txtState = (TextView) findViewById(R.id.txtState);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtPackage = (TextView) findViewById(R.id.txtPackage);
        txtPayment = (TextView) findViewById(R.id.txtPayment);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        profilePic = (ImageView) findViewById(R.id.profilePic);

        llCity = (LinearLayout) findViewById(R.id.llCity);

        iv_camera.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        txtCourse.setOnClickListener(this);
        txtState.setOnClickListener(this);
        txtCity.setOnClickListener(this);
        txtPackage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                apiCall(request.addStudent(Utilis_.sendMultPartImage(picturePath), getBodyParams()), true, Constant.WHITCH_2, getString(R.string.please_wait));
                //apiCall(request.joinUs(Utilis_.sendMultPartImage(picturePath), "test1", Utilis_.getCourse(multiModels), "cod", "no address", "1219235341", "43", "demo", "aani41@gmail.com", "111111", "adsajfh", "101", "44", "43"), true, Constant.WHITCH_2, getString(R.string.please_wait));
                // requestApi(getParams(), AppUrls.JOIN_US, 0);
                if (getTextEmpety()) {
                    Utilis_.showCenterToast(mContext, "Call api");
                } else {
                    Utilis_.showCenterToast(mContext, "error show");
                }
                break;
            case R.id.iv_camera:
                dilogCustom.selectPhotoGalleryAlert(mContext, this);
                break;
            case R.id.txtCourse:
                apiCall(request.getCoursetudent(SharedPref.getSP(Constant.CENTER_ID)), true, Constant.WHITCH_1, getString(R.string.please_wait));
                break;
            case R.id.txtState:
                Intent intent = new Intent(getApplicationContext(), StateAndCityActivity.class);
                intent.putExtra(Constant.INTENT_NAME, Constant.STATE);
                intent.putExtra(Constant.INTENT_ID, "0");
                startActivityForResult(intent, 1);
                break;
            case R.id.txtCity:
                Intent intent2 = new Intent(getApplicationContext(), StateAndCityActivity.class);
                intent2.putExtra(Constant.INTENT_NAME, Constant.CITY);
                intent2.putExtra(Constant.INTENT_ID, state_id);
                startActivityForResult(intent2, 2);
                break;
            case R.id.txtRASecond:
                dilogCustom.dismissRetryAlert();
                break;
            case R.id.txtDilogSubmit:
                if (Utilis_.getCourseNotEmpety(multiModels)) {
                    dilogCustom.dismissCourseDilog();
                    Log.d("MyData", Utilis_.getCourse(multiModels));
                    txtCourse.setText(Utilis_.getStringCourse(multiModels));
                } else {
                    Snackbar snackbar = Snackbar
                            .make(view, getString(R.string.select_courses), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                break;
            case R.id.txtPackage:
                apiCall(request.getPackageStudent(), true, Constant.WHITCH_3, getString(R.string.please_wait));
                break;
        }
    }

    @Override
    public void onPositiveResult(boolean success, int clickId) {
        if (clickId == R.id.txtFromGallery) {
            isFromCamera = false;
            txtFromGallery();
        } else if (clickId == R.id.txtFromCamera) {
            isFromCamera = true;
            txtFromCamera();
            //openCamera();
        }
    }

    private void txtFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        startActivityForResult(cameraIntent, CAMERA);

    }

    private void txtFromGallery() {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public Uri setImageUri() {
        File myDir = new File(directoryPath);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(directoryPath, "/image" + new Date().getTime() + ".jpg");
        Uri imgUri = Uri.fromFile(file);
        mImageUri = imgUri;
        picturePath = file.getAbsolutePath();
        return imgUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = Utilis_.compressImage(cursor.getString(columnIndex));
            cursor.close();
            try {
                Log.d("filePath", picturePath);
                Bitmap bmp = BitmapFactory.decodeFile(picturePath);
                profilePic.setImageBitmap(bmp);
            } catch (ArithmeticException e) {
                Utilis_.showCenterToast(mContext, "Select other image");
            }

        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                llCity.setVisibility(View.VISIBLE);
                String state_name = data.getStringExtra("result_name");
                state_id = data.getStringExtra("result_id");
                txtState.setText(state_name);

            }

        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String city_name = data.getStringExtra("result_name");
                city_id = data.getStringExtra("result_id");
                txtCity.setText(city_name);
            }

        } else if (requestCode == CAMERA && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            picturePath = Utilis_.compressImage(mImageUri.getPath());
            final Bitmap bitmap = BitmapFactory.decodeFile(picturePath,
                    options);
            profilePic.setImageBitmap(bitmap);

        }


    }

    private boolean getTextEmpety() {
        if (Utilis_.getEditTextBool(edtName)
                && Utilis_.getEditTextBool(edtEmail)
                && Utilis_.getEditTextBool(edtAddress)
                && Utilis_.getEditTextBool(edtPhone)
                && Utilis_.getEditTextBool(edtPincode)

                && Utilis_.getTextBool(txtState)
                && Utilis_.getTextBool(txtCity)
                && Utilis_.getTextBool(txtCourse)
                ) {
            return true;
        }
        return false;
    }

    private void apiInitialize() {
        mNetwork = new NetworkManagerRetroFit();
        request = mNetwork.getRetrofit();
    }

    private void apiCall(Call<JsonObject> call, boolean progressBar, int whichApi, String titleProgress) {
        mNetwork.callAPI(call, mContext, this, progressBar, whichApi, titleProgress);
    }

    @Override
    public void onResponce(boolean success, int which, String response) {
        if (success) {
            if (which == Constant.WHITCH_1) {
                setCourseFromApi(response);
            } else if (which == Constant.WHITCH_3) {
                // TODO: 9/22/2017 Get package
                setPackageFromApi(response);
            } else {
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), response, getString(R.string.cancel), getString(R.string.retry), this);
            }
        } else {
            dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), response, getString(R.string.cancel), getString(R.string.retry), this);
        }

    }

    private void setPackageFromApi(String response) {
        PackageJson packageJson = com.bibliophile.loopjServcice.JsonDeserializer.deserializeJson(response, PackageJson.class);
        if (packageJson.status == 1) {
            if (packageJson.packageList != null && packageJson.packageList.size() > 0) {
                mPackage = new ArrayList<>();
                Toast.makeText(mContext, packageJson.packageList.get(0).name, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < packageJson.packageList.size(); i++) {
                    //String name, String duration, String description, String activated, boolean selected
                    mPackage.add(new BottomModel(packageJson.packageList.get(i).name, packageJson.packageList.get(i).id, packageJson.packageList.get(i).price));

                }
                dilogCustom.openBottomSheet(mContext, getString(R.string.select_package), mPackage, this);
            }

        }

    }

    private void setCourseFromApi(String response) {
        CourseJson courseJson = com.bibliophile.loopjServcice.JsonDeserializer.deserializeJson(response, CourseJson.class);
        if (courseJson.status == 1) {
            if (courseJson.courseList != null && courseJson.courseList.size() > 0) {
                multiModels = new ArrayList<>();
                for (int i = 0; i < courseJson.courseList.size(); i++) {
                    //String name, String duration, String description, String activated, boolean selected
                    multiModels.add(new MultiModel(courseJson.courseList.get(i).name, courseJson.courseList.get(i).duration, courseJson.courseList.get(i).description, courseJson.courseList.get(i).id, false));

                }
                dilogCustom.callDilogefullscreen(mContext, getString(R.string.select_courses), this, 1, multiModels, this);
            }

        }
    }

    private void requestApi(RequestParams params, String url, int which) {
        NetworkManager networkManager = new NetworkManager();
        networkManager.callAPI(AddStudentActivity.this, com.bibliophile.loopjServcice.Constant.VAL_POST, url, params, "Title for Dilog", this, true, which);
    }

    public Map<String, RequestBody> getBodyParams() {
        //  @PartMap() Map<String, RequestBody> partMap
        Map<String, RequestBody> params = new HashMap<>();
        params.put("center_id", toRequestBody(SharedPref.getSP(Constant.CENTER_ID)));
        params.put("name", toRequestBody(Utilis_.getEditText(edtName)));
        params.put("course_id", toRequestBody(Utilis_.getCourse(multiModels)));
        params.put("mac_address", toRequestBody(Utilis_.getMACAddress(mContext)));
        params.put("address", toRequestBody(Utilis_.getEditText(edtAddress)));
        params.put("mobile", toRequestBody(Utilis_.getEditText(edtPhone)));
        params.put("email", toRequestBody(Utilis_.getEditText(edtEmail)));
        params.put("state", toRequestBody(state_id));
        params.put("city", toRequestBody(city_id));
        params.put("pincode", toRequestBody(Utilis_.getEditText(edtPincode)));
        return params;
    }


    @Override
    public void onSuccess(boolean success, String response, int which) {
        dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), response, getString(R.string.cancel), getString(R.string.retry), this);

    }

    @Override
    public void onFailure(boolean success, String response, int which) {

    }


    @Override
    public void onSelectPackage(String name, String id, String price) {
        txtPackage.setText(name);
        txtPayment.setText(price);
        this.packageID = id;
    }

}
