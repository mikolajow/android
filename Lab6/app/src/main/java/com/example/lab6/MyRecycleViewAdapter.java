package com.example.lab6;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {



    private ArrayList<RecordingData> recordings;

    private Activity activity;

    private OnRecordingItemClickListener recordingListener;


    public MyRecycleViewAdapter(ArrayList<RecordingData> recordingData, OnRecordingItemClickListener recordingListener, Activity activity) {

        this.recordings = recordingData;
        this.recordingListener = recordingListener;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_layout, viewGroup, false);
        return new MyViewHolder(view, this.recordingListener, this.activity);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.titleTextview.setText(recordings.get(position).title);
        myViewHolder.nameTextview.setText(recordings.get(position).nameAndSurname);
        myViewHolder.dateTextview.setText(recordings.get(position).date);

        myViewHolder.activity = activity;
    }


    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener {

        TextView titleTextview;
        TextView nameTextview;
        TextView dateTextview;

        OnRecordingItemClickListener recordingClickListener;
        ConstraintLayout constraintLayout;
        Activity activity;

        public MyViewHolder(@NonNull View itemView, OnRecordingItemClickListener recordingListener,
                            Activity activity) {
            super(itemView);

            this.titleTextview = (TextView) itemView.findViewById(R.id.title_text_view);
            this.nameTextview = (TextView) itemView.findViewById(R.id.name_and_surname_textview);
            this.dateTextview = (TextView) itemView.findViewById(R.id.date_textview);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item_constraint_layout);
            constraintLayout.setOnCreateContextMenuListener(this);

            this.recordingClickListener = recordingListener;

            itemView.setOnClickListener(this);

        } // constructor


        @Override
        public void onClick(View v) {
            this.recordingClickListener.onRecordingClick(getAdapterPosition());
        } // onclick

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Przy łączeniu:");
            menu.add(getAdapterPosition(), 121, 0, "główne");
            menu.add(getAdapterPosition(), 122, 1, "poboczne");
        }


    }// MyViewHolder class



    public interface OnRecordingItemClickListener {
        void onRecordingClick(int position);
    }


    public void deleteItem(int position) {
        File wav = recordings.get(position).wavFile;
        wav.delete();

        recordings.remove(position);
        notifyItemRemoved(position);
    }

}// MyRecycleViewAdapter class
