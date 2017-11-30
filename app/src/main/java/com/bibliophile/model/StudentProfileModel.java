package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10/17/2017.
 */

public class StudentProfileModel {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("student_details")
    @Expose
    public List<StudentDetail> studentDetails = null;
    public class StudentDetail {

        @SerializedName("student_name")
        @Expose
        public String studentName;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("created")
        @Expose
        public String created;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("student_id")
        @Expose
        public String studentId;
        @SerializedName("center_id")
        @Expose
        public String centerId;
        @SerializedName("address")
        @Expose
        public String address;

    }
}
