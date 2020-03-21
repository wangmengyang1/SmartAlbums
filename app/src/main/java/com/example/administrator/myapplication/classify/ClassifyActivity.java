package com.example.administrator.myapplication.classify;


import android.content.ContentResolver;
import android.content.ContentUris;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.CollectDetailActivity;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.IntentSkip;
import com.example.administrator.myapplication.base.MainEvent;
import com.google.gson.Gson;
import com.mylhyl.pickpicture.PickPictureCallback;
import com.mylhyl.pickpicture.PickPictureHelper;
import com.mylhyl.pickpicture.PictureTotal;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassifyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<PictureTotal> pictureTotals = new ArrayList<>();
    private ClassifyActivityAdapter classifyActivityAdapter;
    private PickPictureHelper pickPictureHelper;
    private int indexCount = 0;
    private TextView collectCount;
    private ImageView imageCollect;
    private LinearLayout clooectLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_fail_activity);
        EventBus.getDefault().register(this);
        recyclerView = findViewById(R.id.classify_fail_recyclerview);
        //图片文件获取
        getPictuie();
        //图片适配
        collectCount = findViewById(R.id.classify_act_adapter_count);
        imageCollect = findViewById(R.id.classify_act_adapter_image);
        clooectLayout = findViewById(R.id.my_collect_layout);
        initCollect();
    }

    private void initCollect() {
        if (ApplicationStatic.getCollectList() != null && ApplicationStatic.getCollectList().size() > 0){
            collectCount.setText(ApplicationStatic.getCollectList().size() + "");
            Glide.with(this).load(ApplicationStatic.getCollectList().get(0).getUri()).into(imageCollect);
        }else {
            collectCount.setText("0");
            Glide.with(this).load(R.drawable.icon_entry).into(imageCollect);
        }

        clooectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的收藏详情页面
                IntentSkip.startIntent(ClassifyActivity.this , new CollectDetailActivity() , null);
            }
        });
    }

    //页面显示
    private void initRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        classifyActivityAdapter = new ClassifyActivityAdapter(this, pictureTotals, new ClassifyActivityAdapter.ClassifyActivityListener() {
            @Override
            public void getClassify(int index) {
                indexCount = index;
                //文件详情图库
                onlist(pictureTotals , index);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(classifyActivityAdapter);
        //目录排序
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveStudentEventBus(MainEvent student) {

        if (student.getIndex() == 0){
            if (ApplicationStatic.getCollectList() != null && ApplicationStatic.getCollectList().size() > 0){
                collectCount.setText(ApplicationStatic.getCollectList().size() + "");
                Glide.with(this).load(ApplicationStatic.getCollectList().get(0).getUri()).into(imageCollect);
            }else {
                collectCount.setText("0");
                Glide.with(this).load(R.drawable.icon_entry).into(imageCollect);
            }
        }else if (student.getIndex() == 1){
            if (ApplicationStatic.getCollectList() != null && ApplicationStatic.getCollectList().size() > 0){
                collectCount.setText(ApplicationStatic.getCollectList().size() + "");
                Glide.with(this).load(ApplicationStatic.getCollectList().get(0).getUri()).into(imageCollect);
            }else {
                collectCount.setText("0");
                Glide.with(this).load(R.drawable.icon_entry).into(imageCollect);
            }
        }else if (student.getIndex() == 2){
            if (ApplicationStatic.getPictureFileList().size() == 1){
                pictureTotals.remove(indexCount);
                classifyActivityAdapter.notifyDataSetChanged();
            }
        }


    }

    @Override
    protected void onDestroy() {
        //移除粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //注销注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();

        //保存数据到本地
        saveDatatoLocality();
    }

    private void saveDatatoLocality() {
        ArrayList<ImageEntry> tagImageList = new ArrayList<>();
        if (ApplicationStatic.getCollectList() == null){
            return;
        }
        ArrayList<ImageEntry> imageEntryArrayList = ApplicationStatic.getCollectList();
        for (int i = 0 ; i < imageEntryArrayList.size() ; i++){
            String parentName = imageEntryArrayList.get(i).getParentName();
            String filePath = imageEntryArrayList.get(i).getFilePath();
            long createrTime = imageEntryArrayList.get(i).getCreaterTime();
            String uriPath = getRealFilePath(this, imageEntryArrayList.get(i).getUri());
            tagImageList.add(new ImageEntry(parentName , uriPath , createrTime));
        }
        SharedPreferences.Editor editor = getSharedPreferences("DemoBeansList", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(tagImageList);
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

    //抽取指定文件夹下所有图片路径
    public void onlist(List<PictureTotal> list, int count) {
        ArrayList<String> parentsPthe = new ArrayList<>();
        ArrayList<ImageEntry> pathMap = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(pickPictureHelper.getChildPathList(count));
//        Log.e("show" , arrayList.size() + "");
        for (int s = 0; s < arrayList.size(); s++) {
            ImageEntry imageEntry = getMediaUriFromPath(this, arrayList.get(s));
            if (imageEntry == null){
                continue;
            }
            pathMap.add(imageEntry);
            parentsPthe.add(imageEntry.getParentName());
        }

        ApplicationStatic.savePictureFileList(pathMap);
        //页面跳转
        IntentSkip.startIntent(ClassifyActivity.this , new ShowClassifyActivity() , null);
    }

    public ImageEntry getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String parentName = null;
        Long pictureTime = null;
        ArrayList<ImageEntry> pathMap = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[]{path.substring(path.lastIndexOf("/") + 1)},
                null);
        Uri uri = null;
        if (cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
            int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String img_path = cursor.getString(img_index);
            File file = new File(img_path);
            pictureTime = file.lastModified();
            parentName = file.getParentFile().getAbsolutePath();
        }
        cursor.close();
        if (pictureTime == null){
            return null;
        }
        return new ImageEntry(parentName, pictureTime, uri);
    }

    //获取所有文件中的图片
    public void getPictuie() {

        pickPictureHelper = PickPictureHelper.readPicture(this, new PickPictureCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<PictureTotal> list) {
//                if (ApplicationStatic.getPictureTotal() != null){
                    pictureTotals.clear();
//                    for (int i = 0 ; i < ApplicationStatic.getPictureTotal().size() ; i++){
//                        if (ApplicationStatic.getPictureTotal().get(i).isCheck()){
//                            pictureTotals.add(ApplicationStatic.getPictureTotal().get(i).getPictureTotal());
//                        }
//                    }
//                }else {
//                    pictureTotals.clear();
//                }

                pictureTotals.addAll(list);
                initRecyclerview();
            }
            @Override
            public void onError() {
            }
        });

    }

}
