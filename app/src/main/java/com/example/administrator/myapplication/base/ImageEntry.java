package com.example.administrator.myapplication.base;

import android.net.Uri;

import java.io.Serializable;

public class ImageEntry implements Serializable {
    private String parentName;
    private String filePath;
    private long createrTime;
    private Uri uri;
    public ImageEntry(String parentName, String filePath) {
        this.parentName = parentName;
        this.filePath = filePath;
    }


    public ImageEntry(String parentName, Long createrTime, Uri uri) {
        this.parentName = parentName;
        this.createrTime = createrTime;
        this.uri = uri;
    }

    public ImageEntry(String parentName, String filePath, long createrTime, Uri uri) {
        this.parentName = parentName;
        this.filePath = filePath;
        this.createrTime = createrTime;
        this.uri = uri;
    }

    public ImageEntry(String parentName, String filePath, Long createrTime) {
        this.parentName = parentName;
        this.filePath = filePath;
        this.createrTime = createrTime;
    }


    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Long getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(Long createrTime) {
        this.createrTime = createrTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
