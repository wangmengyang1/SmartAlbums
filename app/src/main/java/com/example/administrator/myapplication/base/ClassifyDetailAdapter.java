package com.example.administrator.myapplication.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;

import java.util.List;

public class ClassifyDetailAdapter extends BaseAdapter {

    private Context context;
    private List<ImageEntry> strList;
    private int hidePosition = AdapterView.INVALID_POSITION;
//
    public ClassifyDetailAdapter(Context context, List<ImageEntry> strList) {
        this.context = context;
        this.strList = strList;
    }
//
//    @Override
//    public int getCount() {
//        return strList.size();
//    }
//
//    @Override
//    public ImageEntry getItem(int position) {
//        return strList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ImageView imageView;
//        View view1 = LayoutInflater.from(context).inflate(R.layout.gird_view_adapter, null);
//        imageView = view1.findViewById(R.id.gird_view_adapter_image);
//        Glide.with(context)
//                .load(strList.get(position).getUri())
//                .thumbnail(0.1f)
//                .into(imageView);
//        //hide时隐藏Text
//        if (position != hidePosition) {
//            view1.setVisibility(View.VISIBLE);
//        } else {
//            view1.setVisibility(View.GONE);
//        }
//        view1.setId(position);
//
//        return view1;
//    }


//    public void hideView(int pos) {
//        hidePosition = pos;
//        notifyDataSetChanged();
//    }
//
//    public void showHideView() {
//        hidePosition = AdapterView.INVALID_POSITION;
//        notifyDataSetChanged();
//    }
//
//    public void removeView(int pos) {
//        strList.remove(pos);
//        notifyDataSetChanged();
//    }
//
//    //更新拖动时的gridView
//    public void swapView(int draggedPos, int destPos) {
//        //从前向后拖动，其他item依次前移
//        if (draggedPos < destPos) {
//            strList.add(destPos + 1, getItem(draggedPos));
//            strList.remove(draggedPos);
//        }
//        //从后向前拖动，其他item依次后移
//        else if (draggedPos > destPos) {
//            strList.add(destPos, getItem(draggedPos));
//            strList.remove(draggedPos + 1);
//        }
//        hidePosition = destPos;
//        notifyDataSetChanged();
//    }






        @Override
        public int getCount() {
            return strList.size();
        }

        @Override
        public ImageEntry getItem(int position) {
            return strList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            @SuppressLint("ViewHolder") View view1 = LayoutInflater.from(context).inflate(R.layout.gird_view_adapter, null);
            imageView = view1.findViewById(R.id.gird_view_adapter_image);
            Glide.with(context)
                    .load(strList.get(position).getUri())
                    .thumbnail(0.1f)
                    .into(imageView);

            //hide时隐藏Text
            if(position != hidePosition) {
//                view.setText(strList.get(position));
                view1.setVisibility(View.VISIBLE);

            }
            else {
//                view.setText("");
                view1.setVisibility(View.INVISIBLE);
            }
            view1.setId(position);

            return view1;
        }

        public void hideView(int pos) {
            hidePosition = pos;
            notifyDataSetChanged();
        }

        public void showHideView() {
            hidePosition = AdapterView.INVALID_POSITION;
            notifyDataSetChanged();
        }

        public void removeView(int pos) {
            strList.remove(pos);
            notifyDataSetChanged();
        }

        //更新拖动时的gridView
        public void swapView(int draggedPos, int destPos) {
            //从前向后拖动，其他item依次前移
            if(draggedPos < destPos) {
                strList.add(destPos+1, getItem(draggedPos));
                strList.remove(draggedPos);
            }
            //从后向前拖动，其他item依次后移
            else if(draggedPos > destPos) {
                strList.add(destPos, getItem(draggedPos));
                strList.remove(draggedPos+1);
            }
            hidePosition = destPos;
            notifyDataSetChanged();
        }



}
