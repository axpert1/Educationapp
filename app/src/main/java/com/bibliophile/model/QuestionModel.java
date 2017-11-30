package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10/16/2017.
 */

public class QuestionModel {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("list_of_questions")
    @Expose
    public List<ListOfQuestion> listOfQuestions = null;
    public class ListOfQuestion {
        @SerializedName("id")
        @Expose
        public Integer id;
        public String givenAnser;
        @SerializedName("course_id")
        @Expose
        public String courseId;
        @SerializedName("category_id")
        @Expose
        public String categoryId;
        @SerializedName("series_id")
        @Expose
        public String seriesId;
        @SerializedName("question_name")
        @Expose
        public String questionName;
        @SerializedName("question_image")
        @Expose
        public String questionImage;
        @SerializedName("answer")
        @Expose
        public String answer;
        @SerializedName("activated")
        @Expose
        public String activated;
        @SerializedName("banned")
        @Expose
        public String banned;
        @SerializedName("is_delete")
        @Expose
        public String isDelete;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
    }
}