package com.bibliophile.model;

/**
 * Created by ANDROID on 9/12/2017.
 */

public class MultiModel {
    private String name;
    private String duration;
    private String description;
    private String activated;
    private boolean selected;

    public MultiModel(String name, String duration, boolean selected) {
        this.name = name;
        this.duration = duration;
        this.selected = selected;
    }

    public MultiModel(String name, String duration, String description, String activated, boolean selected) {
        this.name = name;
        this.duration = duration;
        this.description = description;
        this.activated = activated;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivated() {
        return activated;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
