package com.example.administrator.myapplication.base;

import java.io.Serializable;
import java.util.ArrayList;

public class PictureData implements Serializable {
    private String tital;
    private ArrayList<ImageEntry> arrayList;

    public PictureData(String tital, ArrayList<ImageEntry> arrayList) {
        this.tital = tital;
        this.arrayList = arrayList;
    }

    public String getTital() {
        return tital;
    }

    public void setTital(String tital) {
        this.tital = tital;
    }

    public ArrayList<ImageEntry> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ImageEntry> arrayList) {
        this.arrayList = arrayList;
    }
}
