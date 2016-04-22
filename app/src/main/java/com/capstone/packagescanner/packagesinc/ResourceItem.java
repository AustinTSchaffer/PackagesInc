package com.capstone.packagescanner.packagesinc;

import android.widget.CheckBox;

/**
 * Created by Cameron on 4/21/2016.
 */
public class ResourceItem {

    String resourceTitle, resourceValue;
    CheckBox send;

    public ResourceItem(){}

    public ResourceItem(String title) {
        resourceTitle = title;
    }

    public CheckBox getSend() {
        return send;
    }

    public void setSend(CheckBox send) {
        this.send = send;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    @Override
    public String toString() {
        return this.resourceTitle + " " + this.resourceValue;
    }
}
