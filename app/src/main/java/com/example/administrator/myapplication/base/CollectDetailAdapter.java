package com.example.administrator.myapplication.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.dialog.PictureCategoryDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CollectDetailAdapter extends RecyclerView.Adapter<CollectDetailAdapter.CollectDetailHolder> {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ImageEntry> arrayList = new ArrayList<>();

    public CollectDetailAdapter(Context context, ArrayList<ImageEntry> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CollectDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.gird_view_adapter, null);
        return new CollectDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectDetailHolder collectDetailHolder, final int i) {
        if (collectDetailHolder != null) {
            Glide.with(context)
                    .load(arrayList.get(i).getUri())
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.tu_jiazai)
                    .into(collectDetailHolder.imageView);

            collectDetailHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureCategoryDialog pictureCategoryDialog = new PictureCategoryDialog(context, R.style.DialogTrangparent, false,
                            arrayList.get(i), new PictureCategoryDialog.PictureCategoryListener() {
                        @Override
                        public void getListener(boolean isShowTipeState) {

                            arrayList.remove(i);
                            ApplicationStatic.saveCollectList(arrayList);
                            notifyDataSetChanged();
                            EventBus.getDefault().post(new MainEvent(0));
                        }
                    });
                    pictureCategoryDialog.setCancelable(false);
                    pictureCategoryDialog.show();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CollectDetailHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public CollectDetailHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gird_view_adapter_image);
        }
    }
}
