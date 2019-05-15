package com.example.lab6;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<String> namesAndSurnames;
    private ArrayList<String> dates;
    private ArrayList<String> pcmFilepathList;
    private ArrayList<String> wavFilepathList;

    public MyRecycleViewAdapter(ArrayList<String> titles, ArrayList<String> namesAndSurnames,
                                ArrayList<String> dates, ArrayList<String> pcmList, ArrayList<String> wavList) {
        this.titles = titles;
        this.namesAndSurnames = namesAndSurnames;
        this.dates = dates;
        this.pcmFilepathList = pcmList;
        this.wavFilepathList = wavList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.titleTextview.setText(titles.get(position));
        myViewHolder.nameTextview.setText(namesAndSurnames.get(position));
        myViewHolder.dateTextview.setText(dates.get(position));

        myViewHolder.pcmFilepath = pcmFilepathList.get(position);
        myViewHolder.wavFilepath = wavFilepathList.get(position);
    }


    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTextview;
        public TextView nameTextview;
        public TextView dateTextview;

        public String pcmFilepath;
        public String wavFilepath;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.titleTextview = (TextView) itemView.findViewById(R.id.title_text_view);
            this.nameTextview = (TextView) itemView.findViewById(R.id.name_and_surname_textview);
            this.dateTextview = (TextView) itemView.findViewById(R.id.date_textview);

            itemView.setOnClickListener(this);
        } // constructor


        //todo odtwarzanie audio
        @Override
        public void onClick(View v) {

        }
    }// MyViewHolder class


    public void deleteItem(int position) {
        titles.remove(position);
        namesAndSurnames.remove(position);
        dates.remove(position);

        String pcmFilepath = pcmFilepathList.get(position);
        String wavFilepath = wavFilepathList.get(position);

        File pcm = new File(pcmFilepath);
        File wav = new File(wavFilepath);
        pcm.delete();
        wav.delete();

        pcmFilepathList.remove(position);
        wavFilepathList.remove(position);
        notifyItemRemoved(position);
    }

}// MyRecycleViewAdapter class
