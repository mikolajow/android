package com.example.lab7;

import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemGridPresenter extends Presenter {

    private static final int GRID_ITEM_WIDTH = 300;
    private static final int GRID_ITEM_HEIGHT = 200;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        TextView view = new TextView(viewGroup.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(
                GRID_ITEM_WIDTH,
                GRID_ITEM_HEIGHT));
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setBackgroundColor(viewGroup.getResources().getColor(
                R.color.default_background));
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((TextView) viewHolder.view).setText((String) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
