package com.example.administrator.myapplication.base;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.dialog.PictureCategoryDialog;
import com.example.administrator.myapplication.dialog.PictureDeleteDialog;
import com.example.administrator.myapplication.dialog.PicturePreviewDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewAdapterHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<PictureData> arrayList;
    private ScrollLinearLayoutManager recyclerView;

    public RecyclerViewAdapter(Context context, ArrayList<PictureData> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter(Context context, ArrayList<PictureData> arrayList, ScrollLinearLayoutManager recyclerView) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.recycler_view_adapter, null);
        return new RecyclerViewAdapterHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterHolder recyclerViewAdapterHolder, @SuppressLint("RecyclerView") final int i) {
        if (recyclerViewAdapterHolder != null) {
            recyclerViewAdapterHolder.title.setText(arrayList.get(i).getTital() + "");
            GridViewAdapter gridViewAdapter = new GridViewAdapter(context, arrayList.get(i).getArrayList(), recyclerView);
            recyclerViewAdapterHolder.dragGridView.setNumColumns(ApplicationStatic.getPictureLineCount());
            recyclerViewAdapterHolder.dragGridView.setAdapter(gridViewAdapter);

            recyclerViewAdapterHolder.dragGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    //长按点击事件
                    PictureDeleteDialog pictureDeleteDialog = new PictureDeleteDialog(context, R.style.DialogTrangparent, new PictureDeleteDialog.PictureDeleteListener() {
                        @Override
                        public void getAffirm() {
                            //确认删除
                            Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
                            deletePicture(arrayList.get(i).getArrayList().get(position));
                            arrayList.get(i).getArrayList().remove(position);
                            notifyDataSetChanged();
                            EventBus.getDefault().post(new MainEvent(2));
                        }
                        @Override
                        public void getCancel() {
                            //取消删除
                            Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show();
                        }
                    }, false);
                    pictureDeleteDialog.setCancelable(false);
                    pictureDeleteDialog.show();
                    return true;
                }
            });
            recyclerViewAdapterHolder.dragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //单击事件
//                    Log.e("show" , "点击事件");
//                    PicturePreviewDialog picturePreviewDialog = new PicturePreviewDialog(context, R.style.DialogTrangparent, arrayList.get(i).getArrayList().get(position));
//                    picturePreviewDialog.setCancelable(false);
//                    picturePreviewDialog.show();

                    PictureCategoryDialog pictureCategoryDialog = new PictureCategoryDialog(context, R.style.DialogTrangparent, true,
                            arrayList.get(i).getArrayList().get(position), new PictureCategoryDialog.PictureCategoryListener() {
                        @Override
                        public void getListener(boolean isShowTipeState) {
                            ImageEntry imageEntry = arrayList.get(i).getArrayList().get(position);
                            ArrayList<ImageEntry> imageEntryArrayList;
                            if (ApplicationStatic.getCollectList() != null){
                                imageEntryArrayList = ApplicationStatic.getCollectList();
                                boolean isEntry = false;
                                for (int i = 0 ; i < imageEntryArrayList.size() ; i++){
                                    if (imageEntry.getCreaterTime() == imageEntryArrayList.get(i).getCreaterTime()){
                                        isEntry = true;
                                    }
                                }
                                if (isEntry){
                                    Toast.makeText(context , "该图片已加入收藏夹" , Toast.LENGTH_SHORT).show();
                                }else {

                                    imageEntryArrayList.add(0 , imageEntry);
                                    Toast.makeText(context , "添加成功" , Toast.LENGTH_SHORT).show();
                                }

                            }else {
                                imageEntryArrayList = new ArrayList<>();
                                imageEntryArrayList.add(imageEntry);
                            }
                            ApplicationStatic.saveCollectList(imageEntryArrayList);
                            EventBus.getDefault().post(new MainEvent(1));
                        }
                    });
                    pictureCategoryDialog.setCancelable(false);
                    pictureCategoryDialog.show();
                }
            });

        }
    }


    private void deletePicture(ImageEntry imageEntry) {
        String filePath = getRealFilePath(context, imageEntry.getUri());

        /** 删除单个文件
         * @param filePath$Name 要删除的文件的文件名
         * @return 单个文件删除成功返回true，否则返回false
         */
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + "成功！");
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePath)));

            } else {
                Toast.makeText(context, "删除单个文件" + "失败！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "删除单个文件失败：" + "不存在！", Toast.LENGTH_SHORT).show();

        }

    }

    //uri转化为图片路径
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


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewAdapterHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private MyGridView dragGridView;

        public RecyclerViewAdapterHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.recycler_view_adapter_title);
            dragGridView = itemView.findViewById(R.id.main_fraggridview);
        }
    }
}
