package com.bibliophile.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by ANDROID on 10/4/2017.
 */
public class CommanJson {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("errors")
    @Expose
    public Errors errors;
    public class Errors {
        @SerializedName("mobile")
        @Expose
        public String mobile;

    }
}
