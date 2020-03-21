package com.example.administrator.myapplication.base;

import com.mylhyl.pickpicture.PictureTotal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//保存全局变量
public class ApplicationStatic {
    public static final String USER_PICTURE_LIST = "user_picture_list";
    public static final String USER_PICTURE_CALSSIFY = "user_picture_calssify";
    public static final String PICTURE_GRIDVIEW_LINE_COUNT = "picture_gridview_line_count";
    public static final String PICTURE_FILE_LIST = "picture_file_list";
    public static final String SORT_LIST = "sort_list";
    public static final String MY_COLLECT = "my_collect";
    public static final String PICTURE_TOAL = "picture_toal";
    public static HashMap<String , Object> hashMap = new HashMap();


    //保存排序方式
    public static void saveSortList(int sort){
        hashMap.put(SORT_LIST , sort);
    }

    //获取排序方式
    public static int getSortList(){
        if (hashMap.get(SORT_LIST) != null){
            return (int) hashMap.get(SORT_LIST);
        }else {
            return 0;
        }
    }

    //保存当前文件下所有图片路径
    public static void savePictureFileList(ArrayList<ImageEntry> imageEntries){
        hashMap.put(PICTURE_FILE_LIST , imageEntries);
    }
    //获取当前文件下所有图片路径
    public static ArrayList<ImageEntry> getPictureFileList(){
        ArrayList<ImageEntry> arrayList = new ArrayList<>();
        if (hashMap.get(PICTURE_FILE_LIST) != null){
            return (ArrayList<ImageEntry>) hashMap.get(PICTURE_FILE_LIST);
        }else {
            return arrayList;
        }
    }

    //保存GridView列
    public static void savePictureLineCount(int count){
        hashMap.put(PICTURE_GRIDVIEW_LINE_COUNT , count);
    }
    //获取GridView列
    public static int getPictureLineCount(){
        if (hashMap.get(PICTURE_GRIDVIEW_LINE_COUNT) != null){
            return (int) hashMap.get(PICTURE_GRIDVIEW_LINE_COUNT);
        }else {
            return 4;
        }
    }

    //保存用户图片
    public static void saveUserPictureList(ArrayList<PictureData> pictureDataArrayList){
        hashMap.put(USER_PICTURE_LIST , pictureDataArrayList);
    }

    //获取用户图片
    public static ArrayList<PictureData> getUserPictureList(){
        ArrayList<PictureData> pictureDataArrayList = new ArrayList<>();
        if (hashMap.get(USER_PICTURE_LIST) != null){
            return (ArrayList<PictureData>) hashMap.get(USER_PICTURE_LIST);
        }else {
            return pictureDataArrayList;
        }
    }


    //保存用户分类图片
    public static void saveUserClassify(ArrayList<PictureClassifyData> pictureDataArrayList){
        hashMap.put(USER_PICTURE_CALSSIFY , pictureDataArrayList);
    }

    //获取用户分类图片
    public static ArrayList<PictureClassifyData> getUserCalssify(){
        ArrayList<PictureClassifyData> pictureDataArrayList = new ArrayList<>();
        if (hashMap.get(USER_PICTURE_CALSSIFY) != null){
            return (ArrayList<PictureClassifyData>) hashMap.get(USER_PICTURE_CALSSIFY);
        }else {
            return pictureDataArrayList;
        }
    }


    //获取我的收藏文件夹图片
    public static ArrayList<ImageEntry> getCollectList() {
        if (hashMap.get(MY_COLLECT) != null){
            return (ArrayList<ImageEntry>) hashMap.get(MY_COLLECT);
        }else {
            return null;
        }
    }

    //保存我的收藏文件夹
    public static void saveCollectList(ArrayList<ImageEntry> arrayList){
        hashMap.put(MY_COLLECT , arrayList);
    }

    //保存获取到的含有图片的文件名称
    public static void savePictureTotal(List<PictureTotalEntry> list) {
        hashMap.put(PICTURE_TOAL , list);
    }

    //获取含有图片的文件岷城
    public static ArrayList<PictureTotalEntry> getPictureTotal() {
        if (hashMap.get(PICTURE_TOAL) != null){
            return (ArrayList<PictureTotalEntry>) hashMap.get(PICTURE_TOAL);
        }else {
            return null;
        }
    }
}
