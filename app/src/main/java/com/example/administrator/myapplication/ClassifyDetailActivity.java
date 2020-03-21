package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.administrator.myapplication.base.AddPictureCatagoryEntry;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ClassifyDetailAdapter;
import com.example.administrator.myapplication.base.DragGridView;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.IntentSkip;
import com.example.administrator.myapplication.base.PictureClassifyData;
import com.example.administrator.myapplication.dialog.AddPictureCatagoryDialog;
import com.example.administrator.myapplication.dialog.PictureCategoryDialog;
import java.util.ArrayList;

/**
 * 项目分类详情页面
 * wmy
 * */
public class ClassifyDetailActivity extends AppCompatActivity {

    private DragGridView recyclerView;
    private String pictureClassifyData;
    private ArrayList<ImageEntry> arrayList = new ArrayList<>();
    private ClassifyDetailAdapter classifyDetailAdapter;
    private boolean ifAllPicture = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_detail_activity);
        recyclerView = findViewById(R.id.classify_detail_act_recyclerview);
        initData();
        initGridView();
    }

    private void initGridView() {
        classifyDetailAdapter = new ClassifyDetailAdapter(this, arrayList);
        recyclerView.setNumColumns(ApplicationStatic.getPictureLineCount());
        recyclerView.setAdapter(classifyDetailAdapter);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                PictureCategoryDialog pictureCategoryDialog;
                if (ifAllPicture) {//确认为所有图片文件
                    pictureCategoryDialog = new PictureCategoryDialog(ClassifyDetailActivity.this,
                            R.style.DialogTrangparent, true, arrayList.get(position), new PictureCategoryDialog.PictureCategoryListener() {
                        @Override
                        public void getListener(boolean isShowTipeState) {
                            if (ApplicationStatic.getUserCalssify().size() >1){
                                //确认添加到其他文件夹
                                AddPictureCatagoryDialog addDialog = new AddPictureCatagoryDialog(ClassifyDetailActivity.this, R.style.DialogTrangparent,
                                        arrayList.get(position), new AddPictureCatagoryDialog.AddPictureCatagoryDialogListener() {
                                    @Override
                                    public void getAddPictureCataListener(ArrayList<AddPictureCatagoryEntry> arrayList) {
                                        //获取到了需要添加图片的文件夹
                                        ArrayList<PictureClassifyData> pictureClassifyDataArrayList = ApplicationStatic.getUserCalssify();
                                        for (int i = 0; i < arrayList.size(); i++) {
                                            for (int s = 0; s < pictureClassifyDataArrayList.size(); s++) {
                                                if (arrayList.get(i).getTital().equals(pictureClassifyDataArrayList.get(s).getFailIndex())) {//如果文件名相同则进行添加操作
                                                    PictureClassifyData pictureClassifyData1 = pictureClassifyDataArrayList.get(s);
                                                    ArrayList<ImageEntry> imageEntryArrayList = pictureClassifyData1.getArrayList();
                                                    imageEntryArrayList.add(0, arrayList.get(i).getImageEntry());
                                                    pictureClassifyDataArrayList.set(s, pictureClassifyData1);
                                                }
                                            }
                                        }
                                        //添加完成
                                        Toast.makeText(ClassifyDetailActivity.this, "已添加到相应文件夹", Toast.LENGTH_SHORT).show();
                                        ApplicationStatic.saveUserClassify(pictureClassifyDataArrayList);
                                    }
                                });
                                addDialog.setCancelable(false);
                                addDialog.show();
//                                EventBus.getDefault().post(new MainEvent(""));
                            }else {
                                Toast.makeText(ClassifyDetailActivity.this , "清先创建分类文件" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {//不是所有图片文件
                    pictureCategoryDialog = new PictureCategoryDialog(ClassifyDetailActivity.this,
                            R.style.DialogTrangparent, false, arrayList.get(position), new PictureCategoryDialog.PictureCategoryListener() {
                        @Override
                        public void getListener(boolean isShowTipeState) {
                            //确认从此文件删除
                            arrayList.remove(position);
                            classifyDetailAdapter.notifyDataSetChanged();
                            Toast.makeText(ClassifyDetailActivity.this , "已删除图片资源" , Toast.LENGTH_SHORT).show();

                            ArrayList<PictureClassifyData> pcd = ApplicationStatic.getUserCalssify();
                            boolean isDetale = false;
                            int index = 0;
                            for (int i = 0 ; i < pcd.size() ; i++){
                                if (pictureClassifyData.equals(pcd.get(i).getFailIndex())){
                                    isDetale = true;
                                    index = i;
                                }
                            }

                            if (isDetale){
                                pcd.set(index , new PictureClassifyData(pictureClassifyData , arrayList));
                                ApplicationStatic.saveUserClassify(pcd);
                            }
                        }
                    });
                }
                pictureCategoryDialog.setCancelable(false);
                pictureCategoryDialog.show();

            }
        });
    }

    private void initData() {
        if (getIntent() != null) {
            pictureClassifyData = (String) getIntent().getSerializableExtra(IntentSkip.INTENT_BUILD);
            ArrayList<PictureClassifyData> al = ApplicationStatic.getUserCalssify();
            for (int i = 0; i < al.size(); i++) {
                if (pictureClassifyData.equals(al.get(i).getFailIndex())) {
                    arrayList.addAll(al.get(i).getArrayList());
                    if (pictureClassifyData.equals("所有图片")) {
                        ifAllPicture = true;
                    }
                }
            }
        }
    }
}
