package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ANDROID on 9/28/2017.
 */

public class CenterProfileJson {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("user_profile")
    @Expose
    public UserProfile userProfile;

    public class UserProfile {

        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("role")
        @Expose
        public String role;
        @SerializedName("activated")
        @Expose
        public String activated;
        @SerializedName("banned")
        @Expose
        public String banned;
        @SerializedName("ban_reason")
        @Expose
        public Object banReason;
        @SerializedName("new_password_key")
        @Expose
        public Object newPasswordKey;
        @SerializedName("new_password_requested")
        @Expose
        public Object newPasswordRequested;
        @SerializedName("new_email")
        @Expose
        public Object newEmail;
        @SerializedName("new_email_key")
        @Expose
        public Object newEmailKey;
        @SerializedName("last_ip")
        @Expose
        public String lastIp;
        @SerializedName("last_login")
        @Expose
        public String lastLogin;
        @SerializedName("created")
        @Expose
        public String created;
        @SerializedName("modified")
        @Expose
        public String modified;
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
