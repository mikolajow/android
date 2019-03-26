package com.example.lab2v1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class myAdapter extends BaseAdapter {

    private Context ctx;

    public Integer[] id_obrazkow = {
            R.drawable.biedronka1, R.drawable.biedronka2, R.drawable.biedronka3,
            R.drawable.biedronka2, R.drawable.biedronka3, R.drawable.biedronka1,
            R.drawable.biedronka3, R.drawable.biedronka1, R.drawable.biedronka2};

    public myAdapter(Context c) {
        ctx = c;
    }

    public int getCount() {
        return id_obrazkow.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int pos, View cView, ViewGroup parent) {
        ImageView mV;
        if (cView == null) {
            mV = new ImageView(ctx);
            mV.setLayoutParams(new GridView.LayoutParams(200, 200));
            mV.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mV.setPadding(8, 8, 8, 8);
        } else
            mV = (ImageView) cView;
        mV.setImageResource(id_obrazkow[pos]);
        return mV;
    }//get view
}//class
