package com.example.administrator.myapplication.base;



import java.io.Serializable;

public class PictureClassifySecondData implements Serializable {
    private String tital;
    private String parentName;
    private String filePath;
    private long createrTime;
    private String uriPath;


    public PictureClassifySecondData() {
    }

    public PictureClassifySecondData(String tital, String parentName, String filePath, long createrTime, String uriPath) {
        this.tital = tital;
        this.parentName = parentName;
        this.filePath = filePath;
        this.createrTime = createrTime;
        this.uriPath = uriPath;
    }


    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
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

    public long getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(long createrTime) {
        this.createrTime = createrTime;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }
}
