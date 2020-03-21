package com.example.administrator.myapplication.base;

import java.io.Serializable;
import java.util.ArrayList;

public class PictureClassifyData implements Serializable {
    public String failIndex;
    public ArrayList<ImageEntry> arrayList;

    public PictureClassifyData(String failIndex, ArrayList<ImageEntry> arrayList) {
        this.failIndex = failIndex;
        this.arrayList = arrayList;
    }


    public String getFailIndex() {
        return failIndex;
    }

    public void setFailIndex(String failIndex) {
        this.failIndex = failIndex;
    }

    public ArrayList<ImageEntry> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ImageEntry> arrayList) {
        this.arrayList = arrayList;
    }
}
