package com.example.administrator.myapplication.classify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.mylhyl.pickpicture.PictureTotal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassifyActivityAdapter extends RecyclerView.Adapter<ClassifyActivityAdapter.ClassifyActivityHolder> {

    private Context context;
    private ArrayList<PictureTotal> pictureTotals;
    private LayoutInflater layoutInflater;
    private ClassifyActivityListener classifyActivityListener;
    public interface ClassifyActivityListener{
        void getClassify(int index);
    }

    public ClassifyActivityAdapter(Context context, ArrayList<PictureTotal> pictureTotals , ClassifyActivityListener classifyActivityListener) {
        this.context = context;
        this.pictureTotals = pictureTotals;
        layoutInflater = LayoutInflater.from(context);
        this.classifyActivityListener = classifyActivityListener;
    }

    @NonNull
    @Override
    public ClassifyActivityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.classify_activity_adapter , null);
        return new ClassifyActivityHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClassifyActivityHolder classifyActivityHolder, @SuppressLint("RecyclerView") final int i) {
        if (classifyActivityHolder != null){
            Glide.with(context).load(pictureTotals.get(i).getTopPicturePath() + "").into(classifyActivityHolder.imageView);
            classifyActivityHolder.tital.setText(pictureTotals.get(i).getFolderName() + "");
            classifyActivityHolder.count.setText(pictureTotals.get(i).getPictureCount() + "");
            if (pictureTotals.get(i).getTopPicturePath() == null){
                classifyActivityHolder.address.setText("");
            }else {
                int count = pictureTotals.get(i).getTopPicturePath().lastIndexOf( "/");
                String address =  pictureTotals.get(i).getTopPicturePath().substring(0 , count) + "";
                classifyActivityHolder.address.setText(address);
            }
            classifyActivityHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classifyActivityListener.getClassify(i);
                }
            });
        }
    }

    /**
     * 按文件名排序
     * @param filePath
     */
    public ArrayList<String> orderByName(String filePath) {
        ArrayList<String> FileNameList = new ArrayList<String>();
        File file = new File(filePath);
        File[] files = file.listFiles();
        List fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    ;
                if (o1.isFile() && o2.isDirectory())
                    ;
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (File file1 : files) {
            if (file1.isDirectory()) {
                FileNameList.add(file1.getName());
            }
        }
        for (int i = 0 ; i < FileNameList.size() ; i++){
            Log.e("show" , FileNameList.get(i) + "");
        }
        return FileNameList;
    }


    @Override
    public int getItemCount() {
        return pictureTotals.size();
    }


    public class ClassifyActivityHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView tital;
        private TextView count;
        private TextView address;

        public ClassifyActivityHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.classify_act_adapter_image);
            tital = itemView.findViewById(R.id.classify_act_adapter_tital);
            count = itemView.findViewById(R.id.classify_act_adapter_count);
            address = itemView.findViewById(R.id.classify_act_adapter_address);
        }
    }
}
