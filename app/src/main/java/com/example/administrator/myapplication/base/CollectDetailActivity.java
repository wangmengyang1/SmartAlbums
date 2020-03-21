package com.example.administrator.myapplication.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.administrator.myapplication.R;
import java.util.ArrayList;


/**
 * 我的收藏详情
 * wmy
 * */
public class CollectDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImageEntry> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_detail_activity);
        recyclerView = findViewById(R.id.collect_detail_activtiy);
        initData();
        initRecycleView();
    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this , ApplicationStatic.getPictureLineCount());
        CollectDetailAdapter collectDetailAdapter = new CollectDetailAdapter(this , arrayList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(collectDetailAdapter);
    }

    private void initData() {
        if (ApplicationStatic.getCollectList() != null){
            arrayList.addAll(ApplicationStatic.getCollectList());
        }
    }
}
