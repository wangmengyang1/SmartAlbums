package com.example.administrator.myapplication.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

public class InputFileNameDialog extends Dialog {
    private Context context;
    private EditText inputEdit;
    private TextView affirm;
    private TextView cancle;
    private InputFileNameListener inputFileNameListener;

    public interface InputFileNameListener{
        void getAffirm(String filename);
        void getCancle();
    }

    public InputFileNameDialog(@NonNull Context context) {
        super(context);
    }

    public InputFileNameDialog(@NonNull Context context, int themeResId ,  InputFileNameListener inputFileNameListener) {
        super(context, themeResId);
        this.context = context;
        this.inputFileNameListener = inputFileNameListener;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_file_name_dialog);
        inputEdit = findViewById(R.id.input_file_name_dialog_edittext);
        affirm = findViewById(R.id.picture_delete_dialog_affirm);
        cancle = findViewById(R.id.picture_delete_dialog_cancle);

        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputFileNameListener != null){
                    if (inputEdit.getText().toString().length() > 0){
                        inputFileNameListener.getAffirm(inputEdit.getText().toString() + "");
                    }else {
                        Toast.makeText(context , "文件名不能为空" , Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputFileNameListener != null){
                    inputFileNameListener.getCancle();
                }
                dismiss();
            }
        });

    }
}
