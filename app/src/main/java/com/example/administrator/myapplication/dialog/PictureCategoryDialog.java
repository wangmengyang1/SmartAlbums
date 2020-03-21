package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.ImageEntry;

public class PictureCategoryDialog extends Dialog {
    private Context context;
    private boolean isShowTipeState = false;
    private PictureCategoryListener pictureCategoryListener;
    private TextView textView;
    private ImageEntry pictureData;
    private ImageView imageView;
    public interface PictureCategoryListener{
        void getListener(boolean isShowTipeState);
    }

    public PictureCategoryDialog(@NonNull Context context) {
        super(context);
    }

    public PictureCategoryDialog(@NonNull Context context, int themeResId ,
                                 boolean isShowTipeState ,ImageEntry pictureData,
                                 PictureCategoryListener pictureCategoryListener) {
        super(context, themeResId);
        this.context = context;
        this.isShowTipeState = isShowTipeState;
        this.pictureCategoryListener = pictureCategoryListener;
        this.pictureData = pictureData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_category_dialog);
        textView = findViewById(R.id.picture_category_textview);
        imageView = findViewById(R.id.picture_preview_dialog_image);

        if (pictureData != null){
            Glide.with(context)
                    .load(pictureData.getUri())
                    .into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (isShowTipeState){
            textView.setText("添加图片到收藏");
        }else {
            textView.setText("从收藏中删除");
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureCategoryListener.getListener(isShowTipeState);
                dismiss();
            }
        });
    }
}
