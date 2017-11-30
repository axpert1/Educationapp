package com.bibliophile.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bibliophile.R;
import com.bibliophile.datas.Constant;
import com.bibliophile.dilogs.DilogCustom;
import com.bibliophile.model.BottomModel;
import com.bibliophile.model.CourseJson;
import com.bibliophile.model.MultiModel;
import com.bibliophile.model.PackageJson;
import com.bibliophile.retroFitService.retrofit.NetworkManagerRetroFit;
import com.bibliophile.retroFitService.retrofit.RequestInterface;
import com.bibliophile.utilitys.CmdRequest;

import com.bibliophile.utilitys.Utilis_;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;


public class JoinUsActivity extends AppCompatActivity implements View.OnClickListener, DilogCustom.onCallbackCameraGallry, DilogCustom.onCallbackMultiItem, NetworkManagerRetroFit.onCallback, DilogCustom.onCallbackBottom {

    private ImageView iv_camera, profilePic;


    private EditText edtCenterName, edtEmail, edtPersonName, edtPhone, edtAddress, edtPincode;
    private TextView txtState, txtCity, txtCourses, txtPackage, txtPayment;
    private Button btnRegister;
    private LinearLayout llCity;


    private Context mContext;
    private DilogCustom dilogCustom;


    private int CAMERA = 500;
    private int RESULT_LOAD_IMAGE = 100;
    private String directoryPath = "/sdcard/bibliophile";
    private Uri mImageUri;
    private String picturePath = null;
    private boolean isFromCamera = false;


    private String state_id = "0", city_id = "0", packageID = null;

    //TOOL BAR
    private TextView activityTitle;
    private Toolbar toolbar;

    //API
    NetworkManagerRetroFit mNetwork;
    RequestInterface request;


    private List<MultiModel> multiCourseModels;
    private List<BottomModel> mPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dilogCustom = new DilogCustom();
        mContext = JoinUsActivity.this;
        initialize();
        apiInitialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        activityTitle.setText(getString(R.string.join_us));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(Utilis_.tintColor(mContext, R.drawable.ic_arrow_back_black_24dp, R.color.white));


        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        llCity = (LinearLayout) findViewById(R.id.llCity);

        edtCenterName = (EditText) findViewById(R.id.edtCenterName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPersonName = (EditText) findViewById(R.id.edtPersonName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);


        txtState = (TextView) findViewById(R.id.txtState);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtCourses = (TextView) findViewById(R.id.txtCourses);
        txtPackage = (TextView) findViewById(R.id.txtPackage);
        txtPayment = (TextView) findViewById(R.id.txtPayment);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        iv_camera.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        txtState.setOnClickListener(this);
        txtCity.setOnClickListener(this);
        txtCourses.setOnClickListener(this);
        txtPackage.setOnClickListener(this);
        txtPayment.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //  Constant.test(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_camera:
                dilogCustom.selectPhotoGalleryAlert(mContext, this);
                break;
            case R.id.btnRegister:
                loginButton();

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
            case R.id.txtCourses:
                apiCall(request.getCourse(), true, Constant.WHITCH_1, getString(R.string.please_wait));
                break;
            case R.id.txtPackage:
                apiCall(request.getPackage(), true, Constant.WHITCH_3, getString(R.string.please_wait));
                break;
            case R.id.txtPayment:

                break;
            case R.id.txtRASecond:
                dilogCustom.dismissRetryAlert();
                break;
            case R.id.txtDilogSubmit:
                if (Utilis_.getCourseNotEmpety(multiCourseModels)) {
                    dilogCustom.dismissCourseDilog();
                    Log.d("MyData", Utilis_.getCourse(multiCourseModels));
                    txtCourses.setText(Utilis_.getStringCourse(multiCourseModels));
                } else {
                    Snackbar snackbar = Snackbar
                            .make(view, getString(R.string.select_courses), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                break;

        }
    }

    private void loginButton() {

        if (getTextEmpety()) {

            apiCall(request.joinUsNew(Utilis_.sendMultPartImage(picturePath), CmdRequest.joinUsParams(Utilis_.getEditText(edtCenterName), Utilis_.getCourse(multiCourseModels), "cod", Utilis_.getEditText(edtAddress), Utilis_.getEditText(edtPhone), packageID, Utilis_.getEditText(edtPersonName), Utilis_.getEditText(edtEmail), "about", "101", state_id, city_id)), true, Constant.WHITCH_2, getString(R.string.please_wait));

        } else {
            //      dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), "Oct 18, 2011 - I am able to call getLayoutInflater only in activity, is that an restriction? ... I need the inflater in places in the code that isn't in the activity class.", "", "yes", this);
        }
    }

    private boolean getTextEmpety() {
        if (Utilis_.getEditTextBool(edtCenterName)
                && Utilis_.getEditTextBool(edtEmail)
                && Utilis_.getEditTextBool(edtPersonName)
                && Utilis_.getEditTextBool(edtPhone)
                && Utilis_.getEditTextBool(edtAddress)
                && Utilis_.getEditTextBool(edtPincode)
                && Utilis_.getTextBool(txtState)
                && Utilis_.getTextBool(txtCity)
                && Utilis_.getTextBool(txtCourses)
                && Utilis_.getTextBool(txtPackage)
                ) {
            return true;
        }

        return false;
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

//    private void openCamera() {
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, RESULT_LOAD_IMAGE);
//    }

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

    private void setCourseFromApi(String response) {
        CourseJson courseJson = com.bibliophile.loopjServcice.JsonDeserializer.deserializeJson(response, CourseJson.class);
        if (courseJson.status == 1) {
            if (courseJson.courseList != null && courseJson.courseList.size() > 0) {
                multiCourseModels = new ArrayList<>();

                for (int i = 0; i < courseJson.courseList.size(); i++) {
                    //String name, String duration, String description, String activated, boolean selected
                    multiCourseModels.add(new MultiModel(courseJson.courseList.get(i).name, courseJson.courseList.get(i).duration, courseJson.courseList.get(i).description, courseJson.courseList.get(i).id, false));

                }
                dilogCustom.callDilogefullscreen(mContext, getString(R.string.select_courses), this, 1, multiCourseModels, this);
            }

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
                dilogCustom.openBottomSheet(mContext, getString(R.string.select_courses), mPackage, this);
            }

        }

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
                // TODO: 9/22/2017 Get course 
                setCourseFromApi(response);

            } else if (which == Constant.WHITCH_2) {
                // TODO: 9/22/2017 Register responce
                dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), response, getString(R.string.cancel), getString(R.string.retry), this);
            } else if (which == Constant.WHITCH_3) {
                // TODO: 9/22/2017 Get package
                setPackageFromApi(response);
            }


        } else {

            dilogCustom.retryAlertDialog(mContext, getString(R.string.app_name), response, getString(R.string.cancel), getString(R.string.retry), this);
        }

    }


    @Override
    public void onSelectPackage(String name, String id, String price) {
        txtPackage.setText(name);
        txtPayment.setText(price);
        packageID = id;
    }
}
