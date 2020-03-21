package com.example.administrator.myapplication.base;

import android.annotation.SuppressLint;
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

public class RecyclerViewPictureAdapter extends RecyclerView.Adapter<RecyclerViewPictureAdapter.RecyclerViewPictureHolder> {


    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<AddPictureCatagoryEntry> arrayList;


    public RecyclerViewPictureAdapter(Context context, ArrayList<AddPictureCatagoryEntry> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewPictureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.recyclerview_picture_adapter , null);
        return new RecyclerViewPictureHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewPictureHolder recyclerViewPictureHolder, @SuppressLint("RecyclerView") final int i) {
        if (recyclerViewPictureHolder != null){
            if (arrayList.get(i).isCheck()){
                recyclerViewPictureHolder.isCheckImage.setImageResource(R.drawable.icon_check);
            }else {
                recyclerViewPictureHolder.isCheckImage.setImageResource(R.drawable.icon_ischeck);
            }
            recyclerViewPictureHolder.tital.setText(arrayList.get(i).getTital() + "");
            recyclerViewPictureHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   boolean pictureChect =  !(arrayList.get(i).isCheck());
                    if (pictureChect){
                        recyclerViewPictureHolder.isCheckImage.setImageResource(R.drawable.icon_check);
                    }else {
                        recyclerViewPictureHolder.isCheckImage.setImageResource(R.drawable.icon_ischeck);
                    }
                    AddPictureCatagoryEntry addPictureCatagoryEntry = arrayList.get(i);
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

    public class RecyclerViewPictureHolder extends RecyclerView.ViewHolder{
        private ImageView isCheckImage;
        private TextView tital;

        public RecyclerViewPictureHolder(@NonNull View itemView) {
            super(itemView);
            isCheckImage = itemView.findViewById(R.id.recycler_picture_adt_image);
            tital = itemView.findViewById(R.id.recycler_picture_adt_tital);
        }
    }
}
