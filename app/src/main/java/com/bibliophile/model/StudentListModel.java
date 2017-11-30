package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10/16/2017.
 */

public class StudentListModel {

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("student_list")
    @Expose
    public List<StudentList> studentList = null;
    public class StudentList {

        @SerializedName("student_name")
        @Expose
        public String studentName;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("student_id")
        @Expose
        public String studentId;

    }
}
