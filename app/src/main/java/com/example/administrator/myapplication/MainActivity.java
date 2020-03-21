package com.example.administrator.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.ImageFilesBean;
import com.example.administrator.myapplication.base.IntentSkip;
import com.example.administrator.myapplication.base.MainEvent;
import com.example.administrator.myapplication.base.PictureClassifyData;
import com.example.administrator.myapplication.base.PictureClassifySecondData;
import com.example.administrator.myapplication.base.PictureData;
import com.example.administrator.myapplication.base.PictureTotalEntry;
import com.example.administrator.myapplication.base.RecyclerViewAdapter;
import com.example.administrator.myapplication.base.ScrollLinearLayoutManager;
import com.example.administrator.myapplication.classify.ClassifyActivity;
import com.example.administrator.myapplication.dialog.FileSettingDialog;
import com.example.administrator.myapplication.dialog.PictureLineNumDialog;
import com.example.administrator.myapplication.dialog.PictureLineSortDialog;
import com.example.administrator.myapplication.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mylhyl.pickpicture.PickPictureCallback;
import com.mylhyl.pickpicture.PickPictureHelper;
import com.mylhyl.pickpicture.PictureTotal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Type;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目首页
 * wmy
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PickPictureHelper pickPictureHelper;
    private ImageFilesBean imageFilesBean;
    private ArrayList<PictureData> pictureData = new ArrayList<>();
    private TextView classify;
    private TextView line;
    private TextView sort;
    private DrawerLayout drawerLayout;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView failSetting;


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receiveStudentEventBus(MainEvent student) {
        pictureData.clear();
        getPictuie();
    }


    @Override
    protected void onDestroy() {
        //移除粘性事件
        EventBus.getDefault().removeAllStickyEvents();
        //注销注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        recyclerView = findViewById(R.id.act_recyclerview);
        classify = findViewById(R.id.act_image_classify);
        line = findViewById(R.id.act_image_set_line);
        sort = findViewById(R.id.act_image_set_sort);
        failSetting = findViewById(R.id.act_image_set_fail);
        drawerLayout = findViewById(R.id.act_drawlayout);

        //动态申请权限
        applyPermission();
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                //跳转到文件分类页面
                IntentSkip.startIntent(MainActivity.this, new ClassifyActivity(), null);
            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                //弹窗调整表格布局列数
                PictureLineNumDialog pictureLineNumDialog = new PictureLineNumDialog(MainActivity.this, R.style.DialogTrangparent, new PictureLineNumDialog.PictureLineNumListener() {
                    @Override
                    public void getAffirm(int lineCount) {
                        ApplicationStatic.savePictureLineCount(lineCount);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void canclel() {
                        Toast.makeText(MainActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                    }
                });
                pictureLineNumDialog.setCancelable(false);
                pictureLineNumDialog.show();
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                //弹窗调整文件排序
                PictureLineSortDialog pictureLineSortDialog = new PictureLineSortDialog(MainActivity.this, R.style.DialogTrangparent, new PictureLineSortDialog.PictureLineSortListener() {
                    @Override
                    public void getOnClick(int sortType) {
                        pictureData.clear();
                        ApplicationStatic.saveSortList(sortType);
                        applyPermission();
                    }
                });
                pictureLineSortDialog.setCancelable(false);
                pictureLineSortDialog.show();
            }
        });
        failSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                FileSettingDialog fileSettingDialog = new FileSettingDialog(MainActivity.this,
                        R.style.DialogTrangparent);
                fileSettingDialog.setCancelable(false);
                fileSettingDialog.show();
            }
        });

    }

    /**
     * @param requestCode 请求码
     * @param permissions 申请的权限
     * @param grantResults 请求结果的集合
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            //申请权限成功
            //获取图片
            getPictuie();
            //轻量级存储
            getSp();
        }else {
            //申请权限失败
            Toast.makeText(this,"权限获取失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private void applyPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            //获取图片
            getPictuie();
            //轻量级存储
            getSp();
        }
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

    //排序数据库（按名称排序）
    public ArrayList<ImageEntry> nameSort(ArrayList<ImageEntry> arrayList) {
        ImageEntry temp;
        Collator cmp = Collator.getInstance(java.util.Locale.CHINA);
        for (int i = 0; i < arrayList.size() - 1; i++) {
            for (int j = 0; j < arrayList.size() - 1 - i; j++) {
                String s1 = getPictureName(arrayList.get(j).getFilePath());
                String s2 = getPictureName(arrayList.get(j + 1).getFilePath());
                if (cmp.compare(s1, s2) == 1) {
                    temp = arrayList.get(j);
                    arrayList.set(j, arrayList.get(j + 1));
                    arrayList.set(j + 1, temp);
                }
            }
        }

        return arrayList;
    }

    //图片标题排序（按名称）
    public ArrayList<PictureData> getPictureName(ArrayList<ImageEntry> arrayList) {
        ArrayList<PictureData> pictureDataArrayList = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++) {
            String tag = getImageString(arrayList.get(i).getFilePath());
            if (i == 0) {
                ArrayList<ImageEntry> imageEntryArrayList = new ArrayList<>();
                imageEntryArrayList.add(arrayList.get(0));
                pictureDataArrayList.add(new PictureData(tag, imageEntryArrayList));
            }

            boolean isEntry = false;
            int index = 0;
            for (int s = 0; s < pictureDataArrayList.size(); s++) {
                if (pictureDataArrayList.get(s).getTital().equals(tag)) {
                    isEntry = true;
                    index = s;
                }
            }

            if (isEntry) {//使用现有tag
                ArrayList<ImageEntry> ie = new ArrayList<>();
                ie = pictureDataArrayList.get(index).getArrayList();
                ie.add(arrayList.get(i));
                pictureDataArrayList.set(index, new PictureData(tag, ie));
            } else {//创建新tag
                ArrayList<ImageEntry> tagIe = new ArrayList<>();
                tagIe.add(arrayList.get(i));
                pictureDataArrayList.add(new PictureData(tag, tagIe));
            }
        }

        return pictureDataArrayList;
    }


    //截取字符串第一个字符并返回
    public String getImageString(String path) {
        int index = path.lastIndexOf("/");
        String s = path.substring(index + 1);
        return s;
    }


    //获取图片名称
    public String getPictureName(String pictureName) {
        int index = pictureName.lastIndexOf("/");
        String tarString = pictureName.substring(index + 1, pictureName.length());
        return tarString;
    }


    public void getPictuie() {
        pickPictureHelper = PickPictureHelper.readPicture(this, new PickPictureCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<PictureTotal> list) {
                ArrayList<PictureTotalEntry> arrayList= new ArrayList<>();
                for (int i = 0 ; i < list.size() ; i++){
                    arrayList.add(new PictureTotalEntry(true , list.get(i)));
                }
                ApplicationStatic.savePictureTotal(arrayList);
                onlist(list);
            }

            @Override
            public void onError() {
            }
        });

    }


    public void onlist(List<PictureTotal> list) {
        ArrayList<String> parentsPthe = new ArrayList<>();
        ArrayList<ImageEntry> pathMap = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            arrayList.addAll(pickPictureHelper.getChildPathList(i));
        }
        for (int s = 0; s < arrayList.size(); s++) {
            ImageEntry imageEntry = getMediaUriFromPath(this, arrayList.get(s));
            if (imageEntry == null){
                continue;
            }
            pathMap.add(imageEntry);
            parentsPthe.add(imageEntry.getParentName());
        }

        if (ApplicationStatic.getSortList() == 0) {//按照时间排序
//            imageFilesBean = ImageFilesBean.returnBean(parentsPthe, bubbleSort(pathMap));
            //图片排序（时间/名称）
            pictureData.addAll(getPictureArray(bubbleSort(pathMap)));
        } else {//按照名称排序
            imageFilesBean = ImageFilesBean.returnBean(parentsPthe, nameSort(pathMap));
            //图片排序（时间/名称）
            pictureData.addAll(getPictureName(imageFilesBean.getPathMap()));
        }


        ApplicationStatic.saveUserPictureList(pictureData);
        ScrollLinearLayoutManager linearLayoutManager = new ScrollLinearLayoutManager(this);
        recyclerViewAdapter = new RecyclerViewAdapter(this, pictureData, linearLayoutManager);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public ImageEntry getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String parentName = null;
        Long pictureTime = null;
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
        return new ImageEntry(parentName, path, pictureTime, uri);
    }

    //轻量级存储
    public void getSp() {
        ArrayList<ImageEntry> al = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("DemoBeansList", MODE_PRIVATE);
        String json = preferences.getString("demoBeanJson", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ImageEntry>>() {}.getType();
            ArrayList<ImageEntry> pictureClassifySecondData = new ArrayList<>();
            pictureClassifySecondData = gson.fromJson(json, type);
            for (int i = 0; i < pictureClassifySecondData.size(); i++) {
                    al.add(new ImageEntry(pictureClassifySecondData.get(i).getParentName()
                            , pictureClassifySecondData.get(i).getCreaterTime()
                            , getUri(pictureClassifySecondData.get(i).getFilePath())));
            }
            ApplicationStatic.saveCollectList(al);
        }
    }

    public Uri getUri(String path) {
        String ImagePath = "file://" + path;
        Uri uri = Uri.parse(ImagePath);
        return uri;
    }


}
