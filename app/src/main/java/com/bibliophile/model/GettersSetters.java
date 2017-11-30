package com.bibliophile.model;

/**
 * Created by ANDROID on 9/21/2017.
 */

public class GettersSetters {
    private String id;
    private String name;

    public GettersSetters(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
