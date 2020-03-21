package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.ImageEntry;

public class PicturePreviewDialog extends Dialog {
    Context context;
    private ImageView imageView;
    private ImageEntry pictureData;
    public PicturePreviewDialog(@NonNull Context context) {
        super(context);
    }

    public PicturePreviewDialog(@NonNull Context context, int themeResId , ImageEntry pictureData) {
        super(context, themeResId);
        this.context = context;
        this.pictureData = pictureData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prcture_preview_dialog);
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
    }
}
