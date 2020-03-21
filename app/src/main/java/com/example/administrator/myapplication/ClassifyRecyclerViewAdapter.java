package com.example.administrator.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.myapplication.base.PictureClassifyData;


import java.util.ArrayList;

public class ClassifyRecyclerViewAdapter extends RecyclerView.Adapter<ClassifyRecyclerViewAdapter.ClassifyRecyclerViewHolder> {

    private Context context;
    private ArrayList<PictureClassifyData> arrayList;
    private LayoutInflater inflater;
    private ClassifyRecyclerViewListener classifyRecyclerViewListener;
    public interface ClassifyRecyclerViewListener{
        void onItemListener(int index);
    }
    private ClassiFyRecyclerListener classiFyRecyclerListener;

    public interface ClassiFyRecyclerListener{
        void itemViewListener(PictureClassifyData pictureClassifyData);
    }
    public ClassifyRecyclerViewAdapter(Context context, ArrayList<PictureClassifyData> arrayList ,
                                       ClassifyRecyclerViewListener classifyRecyclerViewListener,ClassiFyRecyclerListener classiFyRecyclerListener) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        this.classifyRecyclerViewListener = classifyRecyclerViewListener;
        this.classiFyRecyclerListener = classiFyRecyclerListener;
    }

    @NonNull
    @Override
    public ClassifyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.classify_recyclerview_adapter , null);
        return new ClassifyRecyclerViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClassifyRecyclerViewHolder classifyRecyclerViewHolder, @SuppressLint("RecyclerView") final int i) {
        if (classifyRecyclerViewHolder != null){
            classifyRecyclerViewHolder.tital.setText(arrayList.get(i).getFailIndex() + "");

            classifyRecyclerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    classifyRecyclerViewListener.onItemListener(i);
                    return true;
                }
            });

            classifyRecyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classiFyRecyclerListener.itemViewListener(arrayList.get(i));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ClassifyRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView tital;

        public ClassifyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tital = itemView.findViewById(R.id.classify_recyclerview_adt_tital);

        }
    }
}
