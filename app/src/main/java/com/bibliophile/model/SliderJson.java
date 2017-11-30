package com.bibliophile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ANDROID on 10/9/2017.
 */

public class SliderJson {
    @SerializedName("slider_list")
    @Expose
    public List<SliderList> sliderList = null;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("status")
    @Expose
    public Integer status;
    public class SliderList {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("sub_title")
        @Expose
        public String subTitle;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("slider_image")
        @Expose
        public String sliderImage;
        @SerializedName("priority")
        @Expose
        public String priority;
        @SerializedName("activated")
        @Expose
        public String activated;
        @SerializedName("banned")
        @Expose
        public String banned;
        @SerializedName("is_delete")
        @Expose
        public String isDelete;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
    }
}
