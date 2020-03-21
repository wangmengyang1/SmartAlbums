package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class PictureDeleteDialog extends Dialog {

    private TextView tipe;
    private TextView affirm;
    private TextView cancel;
    private PictureDeleteListener pictureDeleteListener;//确认取消回调
    private Context context;
    private boolean isAllDeleteType = false;//是否为批量删除

    public interface PictureDeleteListener {
        void getAffirm();

        void getCancel();
    }

    public PictureDeleteDialog(@NonNull Context context) {
        super(context);
    }

    public PictureDeleteDialog(@NonNull Context context, int themeResId, PictureDeleteListener pictureDeleteListener, boolean isAllDeleteType) {
        super(context, themeResId);
        this.context = context;
        this.pictureDeleteListener = pictureDeleteListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_delete_dialog);
        tipe = findViewById(R.id.picture_delete_dialog_tipe);
        affirm = findViewById(R.id.picture_delete_dialog_affirm);
        cancel = findViewById(R.id.picture_delete_dialog_cancle);
        if (isAllDeleteType) {
            tipe.setText("请注意，这将批量删除选中的照片，请确认。");
        } else {
            tipe.setText("请注意，这将从手机删除这张照片，请确认。");
        }

        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认删除
                if (pictureDeleteListener != null) {
                    pictureDeleteListener.getAffirm();
                    dismiss();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消操作
                if (pictureDeleteListener != null) {
                    pictureDeleteListener.getCancel();
                    dismiss();
                }
            }
        });


    }
}
