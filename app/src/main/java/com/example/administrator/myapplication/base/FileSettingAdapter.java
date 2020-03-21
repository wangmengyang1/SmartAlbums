package com.example.administrator.myapplication.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import java.util.ArrayList;

public class FileSettingAdapter extends RecyclerView.Adapter<FileSettingAdapter.FileSettingHolder> {

    private Context context;
    private ArrayList<PictureTotalEntry> arrayList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FileSettingAdapter(Context context, ArrayList<PictureTotalEntry> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    public interface FileSettingListener{
        void getFileSet(ArrayList<PictureTotalEntry> arrayList);
    }

    @NonNull
    @Override
    public FileSettingHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.file_setting_adapter , null);
        return new FileSettingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FileSettingHolder fileSettingHolder, final int i) {
        if (fileSettingHolder != null){
            if (arrayList.get(i).isCheck()){
                fileSettingHolder.isCheckImage.setImageResource(R.drawable.icon_check);
            }else {
                fileSettingHolder.isCheckImage.setImageResource(R.drawable.icon_ischeck);
            }

            fileSettingHolder.tital.setText(arrayList.get(i).getPictureTotal().getFolderName() + "");
            fileSettingHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean pictureChect =  !(arrayList.get(i).isCheck());
                    if (pictureChect){
                        fileSettingHolder.isCheckImage.setImageResource(R.drawable.icon_check);
                    }else {
                        fileSettingHolder.isCheckImage.setImageResource(R.drawable.icon_ischeck);
                    }
                    PictureTotalEntry addPictureCatagoryEntry = arrayList.get(i);
                    addPictureCatagoryEntry.setCheck(pictureChect);
                    arrayList.set(i , addPictureCatagoryEntry);
                    notifyDataSetChanged();

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FileSettingHolder extends RecyclerView.ViewHolder{
        private ImageView isCheckImage;
        private TextView tital;
        public FileSettingHolder(@NonNull View itemView) {
            super(itemView);
            isCheckImage = itemView.findViewById(R.id.recycler_picture_adt_image);
            tital = itemView.findViewById(R.id.recycler_picture_adt_tital);
        }
    }
}
