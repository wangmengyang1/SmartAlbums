package com.example.administrator.myapplication;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.IntentSkip;
import com.example.administrator.myapplication.base.PictureClassifyData;
import com.example.administrator.myapplication.base.PictureClassifySecondData;
import com.example.administrator.myapplication.dialog.DeleteFileDialog;
import com.example.administrator.myapplication.dialog.InputFileNameDialog;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * 项目分类
 * wmy
 * */
public class ClassifyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView addFail;
    ArrayList<PictureClassifyData> arrayList = new ArrayList<>();
    ClassifyRecyclerViewAdapter classifyRecyclerViewAdapter;
    private ArrayList<PictureClassifySecondData> pictureClassifySecondData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_activity);
        recyclerView = findViewById(R.id.classify_act_recyclervew);
        addFail = findViewById(R.id.classify_act_add_file);
        initData();
        initRecyclerView();
        addFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputFileNameDialog inputFileNameDialog = new InputFileNameDialog(ClassifyActivity.this, R.style.DialogTrangparent, new InputFileNameDialog.InputFileNameListener() {
                    @Override
                    public void getAffirm(String filename) {
                        boolean isRepetition = false;
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (filename.equals(arrayList.get(i).getFailIndex())) {
                                isRepetition = true;
                            }
                        }
                        if (isRepetition) {
                            Toast.makeText(ClassifyActivity.this, "文件夹已经存在！", Toast.LENGTH_SHORT).show();
                        } else {
                            arrayList.add(new PictureClassifyData(filename, new ArrayList<ImageEntry>()));
                            ApplicationStatic.saveUserClassify(arrayList);
                        }
                    }

                    @Override
                    public void getCancle() {

                    }
                });
                inputFileNameDialog.setCancelable(false);
                inputFileNameDialog.show();
            }
        });
    }

    //页面适配
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        classifyRecyclerViewAdapter = new ClassifyRecyclerViewAdapter(this, arrayList, new ClassifyRecyclerViewAdapter.ClassifyRecyclerViewListener() {
            @Override
            public void onItemListener(final int index) {
                DeleteFileDialog deleteFileDialog = new DeleteFileDialog(ClassifyActivity.this, R.style.DialogTrangparent, new DeleteFileDialog.DeleteFileListener() {
                    @Override
                    public void getAffirm() {
                        //确认删除分类文件
                        arrayList.remove(index);
//                        ArrayList<PictureClassifyData> te = ApplicationStatic.getUserCalssify();
//                        te.remove(index);
                        ApplicationStatic.saveUserClassify(arrayList);
                        classifyRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void getCancel() {
                        //取消删除分类文件
                    }
                });
                deleteFileDialog.setCancelable(false);
                deleteFileDialog.show();
            }
        }, new ClassifyRecyclerViewAdapter.ClassiFyRecyclerListener() {
            @Override
            public void itemViewListener(PictureClassifyData pictureClassifyData) {
                //单击事件
                IntentSkip.startIntent(ClassifyActivity.this, new ClassifyDetailActivity(), pictureClassifyData.getFailIndex());
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(classifyRecyclerViewAdapter);

    }







    @SuppressLint("ApplySharedPref")
    @Override
    protected void onPause() {
        super.onPause();
        ArrayList<PictureClassifyData> as = ApplicationStatic.getUserCalssify();
        pictureClassifySecondData = new ArrayList<>();
//        Log.e("show" , as.size() + "");
        for (int i = 0; i < as.size(); i++) {
//            Log.e("show" , as.get(i).getFailIndex());
            if (as.get(i).getArrayList().size() >0){
                for (int s = 0; s < as.get(i).getArrayList().size(); s++) {
                    PictureClassifySecondData pd = new PictureClassifySecondData();
                    String tital = as.get(i).getFailIndex();
                    String parentName = as.get(i).getArrayList().get(s).getParentName();
                    String filePath = as.get(i).getArrayList().get(s).getFilePath();
                    long createrTime = as.get(i).getArrayList().get(s).getCreaterTime();
                    String uriPath = getRealFilePath(ClassifyActivity.this, as.get(i).getArrayList().get(s).getUri());
                    pd.setTital(tital);
                    pd.setParentName(parentName);
                    pd.setFilePath(filePath);
                    pd.setCreaterTime(createrTime);
                    pd.setUriPath(uriPath);
                    pictureClassifySecondData.add(pd);
                }
            }else {
                PictureClassifySecondData pd = new PictureClassifySecondData();
                String tital = as.get(i).getFailIndex();
                pd.setTital(tital);
                pictureClassifySecondData.add(pd);
            }

        }
        SharedPreferences.Editor editor = getSharedPreferences("DemoBeansList", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(pictureClassifySecondData);
        editor.putString("demoBeanJson", json);
        editor.commit();
    }

    //Uri转化为地址值
    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    //获取数据
    private void initData() {
        arrayList.clear();

        if (ApplicationStatic.getUserCalssify().size() <= 0) {
            ArrayList<ImageEntry> imageEntryArrayList = new ArrayList<>();
            for (int i = 0; i < ApplicationStatic.getUserPictureList().size(); i++) {
                imageEntryArrayList.addAll(ApplicationStatic.getUserPictureList().get(i).getArrayList());
            }
            arrayList.add(new PictureClassifyData("所有图片", imageEntryArrayList));
            arrayList.add(new PictureClassifyData("我的收藏", new ArrayList<ImageEntry>()));
            ApplicationStatic.saveUserClassify(arrayList);
        } else {
            arrayList.addAll(ApplicationStatic.getUserCalssify());
        }
    }



    public Uri getUri(String path){
        String ImagePath = "file://" + path;
        Uri uri = Uri.parse(ImagePath);
        return uri;
    }

}
