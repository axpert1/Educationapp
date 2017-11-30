package com.bibliophile.retroFitService.retrofit;

import com.bibliophile.datas.AppUrls;
import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * Created by anil xpert on 15-04-2017.
 */


public interface RequestInterface {


    // TODO: 9/28/2017
    //for send post data
    @FormUrlEncoded
    @POST(AppUrls.CENTER_PROFILE)
    Call<JsonObject> getCenterProfile(@Field("id") String center_id);

    // TODO: 9/28/2017
    //for send post data
    @FormUrlEncoded
    @POST(AppUrls.CENTER_LOGIN)
    Call<JsonObject> centerLogin(@Field("email") String email, @Field("password") String password);

    // TODO: 9/28/2017
    //for send post data
    @FormUrlEncoded
    @POST(AppUrls.STUDENT_LOGIN)
    Call<JsonObject> studentLogin(@Field("email") String email, @Field("password") String password);


    // TODO: 9/28/2017
    // @FormUrlEncoded
    @POST(AppUrls.GET_COURSE)
    Call<JsonObject> getCourse();

    // TODO: 9/28/2017
    @FormUrlEncoded
    @POST(AppUrls.GET_COURSE_FOR_STUDENT)
    Call<JsonObject> getCoursetudent(@Field("id") String center_id);


    // TODO: 9/28/2017
    @POST(AppUrls.GET_PACKAGES)
    Call<JsonObject> getPackage();

    // TODO: 9/28/2017
    @POST(AppUrls.GET_PACKAGES_STUDENT)
    Call<JsonObject> getPackageStudent();

    // TODO: 9/28/2017
    @Multipart
    @POST(AppUrls.JOIN_US)
    Call<JsonObject> joinUsNew(
            @Part MultipartBody.Part image
            , @PartMap Map<String, RequestBody> params
    );


    // TODO: 9/28/2017
    @FormUrlEncoded
    @POST(AppUrls.FORGOT_PASSWORD)
    Call<JsonObject> forgotPassword(@Field("email") String editText);


    // TODO: 9/28/2017
    @Multipart
    @POST(AppUrls.ADD_STUDENT)
    Call<JsonObject> addStudent(
            @Part MultipartBody.Part image
            , @PartMap Map<String, RequestBody> params
    );


    // TODO: 9/28/2017
    @Multipart
    @POST(AppUrls.JOIN_US)
    Call<JsonObject> joinUs(
            @Part MultipartBody.Part image
            , @Field("center_name") String center_name
            , @Field("course_id") String course_id
            , @Part("payment_mode") String payment_mode
            , @Part("address") String address
            , @Part("contact_no") String contact_no
            , @Part("package_id") String package_id
            , @Part("contact_person_name") String contact_person_name
            , @Part("email") String email
            , @Part("password") String password
            , @Part("about") String about
            , @Part("country") String country
            , @Part("state") String state
            , @Part("city") String city
    );


    // TODO: 9/28/2017
    @Multipart
    @POST("GetCartCount.php")
    Call<JsonObject> getcartCountDataB(@Part MultipartBody.Part image, @Part("user_id") String id);


    // TODO: 9/28/2017
    @FormUrlEncoded
    @POST("GetCartCount.php")
    Call<JsonObject> getcartCountDataC(@Field("user_id") String user_id);




    //    @GET("group/{id}/users")
    //    Call<Loginjson> groupList(@Path("id") int groupId);

    //    @FormUrlEncoded
    //    @POST("login")
    //    Call<Loginjson> getloginData(@Field("email")String email, @Field("password") String password);


    //     @FormUrlEncoded
    //     @Headers("Content-Type: application/json")
    //     @POST("api/VoidOrderItem")
    //     Call<VoidItem> getVoidData(  @Body RequestBody params);


    //    @GET("categories.php")
    //    Call<RRcatJson> groupList();


    //    @GET("GetCartCount.php")
    //    Call<TestingJson> getJSON();

    //    //for send post data
//    @FormUrlEncoded
//    @POST("GetCartCount.php")
//    Call<JsonObject> getcartCountDataA(@Field("user_id") String user_id);
}


