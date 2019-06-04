package com.example.lab7;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


public class BlankFragment extends BrowseFragment {


    public BlankFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_blank, container, false);
//    }



    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("mainFragment", "MainF - onActivityCreated");

        setupUIElements();
        loadRows();
        setupEventListeners();
    }



    private void setupUIElements() {
        setTitle("My First TV App");
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(getResources().getColor(
                R.color.header_background));
        setSearchAffordanceColor(getResources().getColor(
                R.color.search_color));
    }

    private void loadRows(){
        int NUM_ROWS = 2;
        ArrayObjectAdapter myRowsAdapter = new ArrayObjectAdapter(new
                ListRowPresenter());
        for (int i = 0; i < NUM_ROWS; ++i) {
            /* GridItemPresenter */
            HeaderItem gridItemPresenterHeader =
                    new HeaderItem(0, "Text Presenter "+i);
            ItemGridPresenter myGridPresenter = new
                    ItemGridPresenter();
            ArrayObjectAdapter gridRowAdapter = new
                    ArrayObjectAdapter(myGridPresenter);
            gridRowAdapter.add("ITEM "+i+1);
            gridRowAdapter.add("ITEM "+i+2);
            gridRowAdapter.add("ITEM "+i+3);
            myRowsAdapter.add(new
                    ListRow(gridItemPresenterHeader, gridRowAdapter));
        }
        List<Card> cardList = CardList.buildCardList(getContext(), 3);
        HeaderItem cardPresenterHeader = new HeaderItem(1,
                "Moj CardPresenter");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new
                ArrayObjectAdapter(cardPresenter);
        for(int i=0; i<3; i++)
            cardRowAdapter.add(cardList.get(i));
        myRowsAdapter.add(new ListRow(cardPresenterHeader,
                cardRowAdapter));
        setAdapter(myRowsAdapter);
    }


    private final class ItemViewClickedListener implements
            OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder
                                          itemViewHolder,
                                  Object item,
                                  RowPresenter.ViewHolder
                                          rowViewHolder,
                                  Row row)
        {
            if (item instanceof String) {
                Toast.makeText(getActivity(), ((String) item),
                        Toast.LENGTH_SHORT).show();
            }
        }
    } // ItemViewClickedListener class


    private void setupEventListeners()
    {
        setOnSearchClickedListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Implement own search ",
                        Toast.LENGTH_LONG).show();
            }
        });
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

}// BlankFragment class


































