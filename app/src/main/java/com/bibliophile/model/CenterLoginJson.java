package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 9/25/2017.
 */

public class CenterLoginJson {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("user_details")
    @Expose
    public UserDetails userDetails;
    public class UserDetails {

        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("activated")
        @Expose
        public String activated;
        @SerializedName("banned")
        @Expose
        public String banned;
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("center_id")
        @Expose
        public String centerId;
        @SerializedName("course_id")
        @Expose
        public String courseId;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("series_id")
        @Expose
        public String seriesId;
        @SerializedName("package_id")
        @Expose
        public String packageId;
        @SerializedName("f_name")
        @Expose
        public String fName;
        @SerializedName("l_name")
        @Expose
        public String lName;
        @SerializedName("contact_person_name")
        @Expose
        public String contactPersonName;
        @SerializedName("score")
        @Expose
        public String score;
        @SerializedName("time")
        @Expose
        public String time;
        @SerializedName("payment_mode")
        @Expose
        public String paymentMode;
        @SerializedName("center_name")
        @Expose
        public String centerName;
        @SerializedName("unique_code")
        @Expose
        public String uniqueCode;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("website")
        @Expose
        public Object website;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("postal_code")
        @Expose
        public String postalCode;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("is_delete")
        @Expose
        public String isDelete;
        @SerializedName("designation")
        @Expose
        public String designation;

    }
}
