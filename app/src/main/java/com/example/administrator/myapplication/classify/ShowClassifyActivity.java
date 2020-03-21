package com.example.administrator.myapplication.classify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.ImageFilesBean;
import com.example.administrator.myapplication.base.PictureClassifyData;
import com.example.administrator.myapplication.base.PictureData;
import com.example.administrator.myapplication.base.RecyclerViewAdapter;
import com.example.administrator.myapplication.base.ScrollLinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowClassifyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<ImageEntry>  pictureDataArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_classify_activity);
        recyclerView = findViewById(R.id.act_recyclerview);
        getInitIntent();
    }

    private void getInitIntent() {
        ArrayList<PictureData> arrayList = new ArrayList<>();
        pictureDataArrayList = ApplicationStatic.getPictureFileList();
        arrayList.addAll(getPictureArray(bubbleSort(pictureDataArrayList)));
        ScrollLinearLayoutManager linearLayoutManager = new ScrollLinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(this, arrayList, linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    //图片标题排序（时间）
    public ArrayList<PictureData> getPictureArray(ArrayList<ImageEntry> arrayList) {
        ArrayList<PictureData> pictureDataArrayList = new ArrayList<>();
        int i = 0;
        while (i <= arrayList.size() - 1) {
            if (i == 150) {
                break;
            }
            ImageEntry imageEntry = arrayList.get(i);
            @SuppressLint("SimpleDateFormat") String tital = new SimpleDateFormat("yyyy-MM-dd").format(new Date(imageEntry.getCreaterTime()));
            if (i == 0) {
                ArrayList<ImageEntry> firstList = new ArrayList<>();
                firstList.add(arrayList.get(0));
                pictureDataArrayList.add(new PictureData(tital, firstList));
            } else {
                boolean isEntry = false;
                int index = 0;
                ArrayList<ImageEntry> secondList = new ArrayList<>();
                for (int s = 0; s < pictureDataArrayList.size(); s++) {
                    //照片时间相同
                    if (pictureDataArrayList.get(s).getTital().equals(tital)) {
                        isEntry = true;
                        index = s;
                    }
                }
                if (isEntry) {
                    secondList = pictureDataArrayList.get(index).getArrayList();
                    secondList.add(arrayList.get(i));
                    pictureDataArrayList.set(index, new PictureData(pictureDataArrayList.get(index).getTital(), secondList));
                } else {
                    secondList.add(arrayList.get(i));
                    pictureDataArrayList.add(new PictureData(tital, secondList));
                }
            }
            i++;
        }

        return pictureDataArrayList;
    }

    //排序数据库（按时间排序）
    public ArrayList<ImageEntry> bubbleSort(ArrayList<ImageEntry> arrayList) {
        ImageEntry temp;
        for (int i = 0; i < arrayList.size() - 1; i++) {
            for (int j = 0; j < arrayList.size() - 1 - i; j++) {
                if (arrayList.get(j).getCreaterTime() < arrayList.get(j + 1).getCreaterTime()) {
                    temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, temp);
                }
            }
        }
        return arrayList;
    }

}
