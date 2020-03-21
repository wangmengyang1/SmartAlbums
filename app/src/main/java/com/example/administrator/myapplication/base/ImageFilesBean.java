package com.example.administrator.myapplication.base;

import java.util.ArrayList;

public class ImageFilesBean {
    private ArrayList<String> parentsPthe = new ArrayList<>();
    private ArrayList<ImageEntry> pathMap = new ArrayList<>();

    public ArrayList<String> getParentsPthe() {
        return parentsPthe;
    }

    public ImageFilesBean setParentsPthe(ArrayList<String> parentsPthe) {
        this.parentsPthe = parentsPthe;
        return this;
    }

    public ArrayList<ImageEntry> getPathMap() {
        return pathMap;
    }

    public ImageFilesBean setPathMap(ArrayList<ImageEntry> pathMap) {
        this.pathMap = pathMap;
        return this;
    }

    public static ImageFilesBean returnBean(ArrayList<String> parentsPthe,ArrayList<ImageEntry> pathMap) {
        return new ImageFilesBean().setParentsPthe(parentsPthe).setPathMap(pathMap);
    }

}
