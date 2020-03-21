package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class DeleteFileDialog extends Dialog {

    private Context context;
    private DeleteFileListener deleteFileListener;
    private TextView affirm;
    private TextView cancle;

    public interface DeleteFileListener{
        void getAffirm();
        void getCancel();
    }

    public DeleteFileDialog(@NonNull Context context) {
        super(context);
    }

    public DeleteFileDialog(@NonNull Context context, int themeResId , DeleteFileListener deleteFileListener) {
        super(context, themeResId);
        this.context = context;
        this.deleteFileListener = deleteFileListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_file_dialog);
        affirm = findViewById(R.id.picture_delete_dialog_affirm);
        cancle = findViewById(R.id.picture_delete_dialog_cancle);

        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认删除
                if (deleteFileListener != null) {
                    deleteFileListener.getAffirm();
                    dismiss();
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消操作
                if (deleteFileListener != null) {
                    deleteFileListener.getCancel();
                    dismiss();
                }
            }
        });

    }
}
