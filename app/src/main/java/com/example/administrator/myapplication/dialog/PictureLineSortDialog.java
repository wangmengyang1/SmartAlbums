package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class PictureLineSortDialog extends Dialog {

    private Context context;
    private int sortType;
    private PictureLineSortListener pictureLineSortListener;
    private TextView secondLine;
    private TextView threadLine;
    private TextView affirm;
    private TextView cancle;
    public interface PictureLineSortListener{
        void getOnClick(int sortType);
    }

    public PictureLineSortDialog(@NonNull Context context) {
        super(context);
    }

    public PictureLineSortDialog(@NonNull Context context, int themeResId ,  PictureLineSortListener pictureLineSortListener) {
        super(context, themeResId);
        this.pictureLineSortListener = pictureLineSortListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_sort_dialot);
        secondLine = findViewById(R.id.picture_line_two);
        threadLine = findViewById(R.id.picture_line_three);
        affirm = findViewById(R.id.picture_delete_dialog_affirm);
        cancle = findViewById(R.id.picture_delete_dialog_cancle);

        secondLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLine.setTextColor(Color.parseColor("#ff0000"));
                threadLine.setTextColor(Color.parseColor("#000000"));
                sortType = 0;
            }
        });
        threadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadLine.setTextColor(Color.parseColor("#ff0000"));
                secondLine.setTextColor(Color.parseColor("#000000"));
                sortType = 1;
            }
        });

        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureLineSortListener.getOnClick(sortType);
                dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureLineSortListener.getOnClick(sortType);
                dismiss();
            }
        });
    }
}
