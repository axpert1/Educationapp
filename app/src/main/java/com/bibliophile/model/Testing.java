package com.bibliophile.model;

/**
 * Created by ANDROID on 9/8/2017.
 */

public class Testing {
    private String name;
    private int ids;
    private String value;
    private String imageUrl;

    public Testing(String name, int ids, String value,String imageUrl) {
        this.name = name;
        this.ids = ids;
        this.value = value;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIds() {
        return ids;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Testing(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
