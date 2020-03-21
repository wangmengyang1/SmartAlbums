package com.example.administrator.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.base.AddPictureCatagoryEntry;
import com.example.administrator.myapplication.base.ApplicationStatic;
import com.example.administrator.myapplication.base.ImageEntry;
import com.example.administrator.myapplication.base.PictureClassifyData;
import com.example.administrator.myapplication.base.RecyclerViewPictureAdapter;
import java.util.ArrayList;

public class AddPictureCatagoryDialog extends Dialog {

    private Context context;
    private ArrayList<AddPictureCatagoryEntry> arrayList = new ArrayList<>();
    private ImageEntry imageEntry;
    private AddPictureCatagoryDialogListener addPictureCatagoryDialogListener;
    private RecyclerView recyclerView;
    private TextView arrify;
    private RecyclerViewPictureAdapter recyclerViewPictureAdapter;
    private boolean isDismess = false;

    public interface AddPictureCatagoryDialogListener {
        void getAddPictureCataListener(ArrayList<AddPictureCatagoryEntry> arrayList);
    }

    public AddPictureCatagoryDialog(@NonNull Context context) {
        super(context);
    }

    public AddPictureCatagoryDialog(@NonNull Context context, int themeResId, ImageEntry imageEntry,
                                    AddPictureCatagoryDialogListener addPictureCatagoryDialogListener) {
        super(context, themeResId);
        this.context = context;
        this.imageEntry = imageEntry;
        this.addPictureCatagoryDialogListener = addPictureCatagoryDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_picture_catagory_dialog);
        recyclerView = findViewById(R.id.add_picture_recyclerview);
        arrify = findViewById(R.id.add_picture_arrify);
        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewPictureAdapter = new RecyclerViewPictureAdapter(context, arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewPictureAdapter);
        arrify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPictureCatagoryDialogListener.getAddPictureCataListener(arrayList);
                dismiss();
            }
        });
    }

    private void initData() {
        ArrayList<PictureClassifyData> pictureClassifyDataArrayList = ApplicationStatic.getUserCalssify();
        if (pictureClassifyDataArrayList.size() > 1) {
            for (int i = 1; i < pictureClassifyDataArrayList.size(); i++) {
                arrayList.add(new AddPictureCatagoryEntry(pictureClassifyDataArrayList.get(i).getFailIndex(), imageEntry, false));
            }
        }
    }
}
