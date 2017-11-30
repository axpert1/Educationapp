package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10/10/2017.
 */

public class TestSeriesModel {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("series_list")
    @Expose
    public List<SeriesList> seriesList = null;

    public class SeriesList {
        @SerializedName("series_name")
        @Expose
        public String seriesName;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("rules")
        @Expose
        public String rules;
        @SerializedName("subject_name")
        @Expose
        public String subjectName;
        @SerializedName("activated")
        @Expose
        public String activated;
        @SerializedName("course_name")
        @Expose
        public String courseName;
        @SerializedName("series_id")
        @Expose
        public Integer seriesId;
    }
}
