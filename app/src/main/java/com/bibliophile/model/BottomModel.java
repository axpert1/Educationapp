package com.bibliophile.model;

/**
 * Created by ANDROID on 9/25/2017.
 */

public class BottomModel {
    private String name;
    private String id;
    private String price;

    public BottomModel(String name, String id, String price) {
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
