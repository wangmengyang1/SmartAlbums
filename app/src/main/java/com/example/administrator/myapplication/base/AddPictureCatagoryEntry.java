package com.example.administrator.myapplication.base;

import java.io.Serializable;

public class AddPictureCatagoryEntry implements Serializable {
    private String tital;
    private ImageEntry imageEntry;
    private boolean isCheck;

    public AddPictureCatagoryEntry(String tital, ImageEntry imageEntry) {
        this.tital = tital;
        this.imageEntry = imageEntry;
    }

    public AddPictureCatagoryEntry(String tital, ImageEntry imageEntry, boolean isCheck) {
        this.tital = tital;
        this.imageEntry = imageEntry;
        this.isCheck = isCheck;
    }


    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public ImageEntry getImageEntry() {
        return imageEntry;
    }

    public void setImageEntry(ImageEntry imageEntry) {
        this.imageEntry = imageEntry;
    }
}
