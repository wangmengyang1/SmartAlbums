package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class PictureLineNumDialog extends Dialog {

    private Context context;
    private int lineCount;
    private PictureLineNumListener pictureLineNumListener;
    private TextView secondLine;
    private TextView threadLine;
    private TextView fourLine;
    private TextView affirm;
    private TextView cancle;

    public interface PictureLineNumListener{
        void getAffirm(int lineCount);
        void canclel();
    }

    public PictureLineNumDialog(@NonNull Context context) {
        super(context);
    }

    public PictureLineNumDialog(@NonNull Context context, int themeResId , PictureLineNumListener pictureLineNumListener) {
        super(context, themeResId);
        this.context = context;
        this.pictureLineNumListener = pictureLineNumListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_line_num_dialog);
        secondLine = findViewById(R.id.picture_line_two);
        threadLine = findViewById(R.id.picture_line_three);
        fourLine = findViewById(R.id.picture_line_four);
        affirm = findViewById(R.id.picture_delete_dialog_affirm);
        cancle = findViewById(R.id.picture_delete_dialog_cancle);

        secondLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLine.setTextColor(Color.parseColor("#ff0000"));
                threadLine.setTextColor(Color.parseColor("#000000"));
                fourLine.setTextColor(Color.parseColor("#000000"));
                lineCount = 2;
            }
        });
        threadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadLine.setTextColor(Color.parseColor("#ff0000"));
                secondLine.setTextColor(Color.parseColor("#000000"));
                fourLine.setTextColor(Color.parseColor("#000000"));
                lineCount = 3;
            }
        });
        fourLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fourLine.setTextColor(Color.parseColor("#ff0000"));
                threadLine.setTextColor(Color.parseColor("#000000"));
                secondLine.setTextColor(Color.parseColor("#000000"));
                lineCount = 4;
            }
        });
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureLineNumListener.getAffirm(lineCount);
                dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureLineNumListener.canclel();
                dismiss();
            }
        });
    }
}
