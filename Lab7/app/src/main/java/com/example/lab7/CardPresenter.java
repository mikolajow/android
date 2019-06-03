package com.example.lab7;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;

public class CardPresenter extends Presenter {
    private static final String TAG = "TAG1";
    private static Context mContext;
    private static int C_WIDTH = 300;
    private static int C_HEIGHT = 200;
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "*** onCreateViewHolder - wywolano ***");
        mContext = parent.getContext();
        ImageCardView cardView = new ImageCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(mContext.getResources().
                getColor(R.color.header_background));
        return new ViewHolder(cardView);
    }
    public void onBindViewHolder(ViewHolder viewHolder,
                                 Object item) {
        Log.d(TAG, "onBindViewHolder");
        Card card = (Card) item;
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setTitleText(card.title);
        cardView.setContentText(card.description);
        cardView.setMainImage(card.image);
        cardView.setMainImageDimensions(C_WIDTH, C_HEIGHT);
    }
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        Log.d(TAG, "*** onUnbindViewHolder - wywolano ***");
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setMainImage(null);
    }
}
