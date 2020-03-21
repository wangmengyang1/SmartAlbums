package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ClassifyDetailAdapter;
import com.example.administrator.myapplication.base.DragGridView;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.IntentSkip;
import com.example.administrator.myapplication.base.PictureClassifyData;
import java.util.ArrayList;

@SuppressLint("Registered")
public class ClassifyDetailSecondActivity  extends AppCompatActivity {
    private DragGridView recyclerView;
    private String pictureClassifyData;
    private ArrayList<ImageEntry> arrayList = new ArrayList<>();
    private ClassifyDetailAdapter classifyDetailAdapter;
    private boolean ifAllPicture = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_detail_activity_second);
        recyclerView = findViewById(R.id.classify_detail_act_recyclerview);
        initData();
        initGridView();
    }

    private void initGridView() {
        classifyDetailAdapter = new ClassifyDetailAdapter(this , arrayList);
        recyclerView.setNumColumns(4);
        recyclerView.setAdapter(classifyDetailAdapter);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ifAllPicture){//确认为所有图片文件
//                    PictureCategoryDialog pictureCategoryDialog = new PictureCategoryDialog();
                }else {//不是所有图片文件

                }
                //图片点击事件
//                PicturePreviewDialog picturePreviewDialog = new PicturePreviewDialog(ClassifyDetailSecondActivity.this,
//                        R.style.DialogTrangparent, arrayList.get(position));
//                picturePreviewDialog.setCancelable(false);
//                picturePreviewDialog.show();
            }
        });
    }

    private void initData() {
        if (getIntent()!= null){
            pictureClassifyData = (String) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            ArrayList<PictureClassifyData> al = ApplicationStatic.getUserCalssify();
            for (int i = 0 ; i< al.size() ; i++){
                if (pictureClassifyData.equals(al.get(i).getFailIndex())){
                    arrayList.addAll(al.get(i).getArrayList());
                    if (pictureClassifyData.equals("所有图片")){
                        ifAllPicture = true;
                    }
                }
            }
        }
    }
}
