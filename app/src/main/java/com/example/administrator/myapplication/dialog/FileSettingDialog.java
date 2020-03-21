package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.FileSettingAdapter;
import com.example.administrator.myapplication.base.PictureTotalEntry;
import java.util.ArrayList;

public class FileSettingDialog extends Dialog {

    private Context context;
    private RecyclerView recyclerView;
    private TextView arrify;
    private FileSettingAdapter fileSettingAdapter;
    private ArrayList<PictureTotalEntry> arrayList = new ArrayList<>();

    public FileSettingDialog(@NonNull Context context) {
        super(context);
    }

    public FileSettingDialog(@NonNull Context context, int themeResId ) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_picture_catagory_dialog);
        recyclerView = findViewById(R.id.add_picture_recyclerview);
        arrify = findViewById(R.id.add_picture_arrify);
        initData();
        initRecyclerView();
        arrify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ApplicationStatic.savePictureTotal(arrayList);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        fileSettingAdapter = new FileSettingAdapter(context, arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(fileSettingAdapter);
    }

    private void initData() {
        if (ApplicationStatic.getPictureTotal() != null){
            arrayList.clear();
            arrayList.addAll(ApplicationStatic.getPictureTotal());
        }
    }
}
