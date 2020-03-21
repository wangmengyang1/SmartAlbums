package com.example.administrator.myapplication.base;

import java.io.Serializable;

public class MainEvent implements Serializable {
    private int index;

    public MainEvent(int index) {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
