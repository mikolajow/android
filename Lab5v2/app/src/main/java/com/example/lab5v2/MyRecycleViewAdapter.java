package com.example.lab5v2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private ArrayList<String> scoreOwners;
    private ArrayList<String> scoreValues;
    private Activity activity;

    public MyRecycleViewAdapter(ArrayList<String> owners, ArrayList<String> scores, Activity activity) {
        this.scoreValues = scores;
        this.scoreOwners = owners;
        this.activity = activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.score_layout, viewGroup, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.scoreOwnerTextView.setText(scoreOwners.get(position));
        myViewHolder.scoreTextView.setText(scoreValues.get(position));
    }


    @Override
    public int getItemCount() {
        return scoreOwners.size();
    }


    public void deleteItem(int position) {
        String recentyDeletedOwner = scoreOwners.get(position);
        String recentyDeletedScore = scoreValues.get(position);
        scoreOwners.remove(position);
        scoreValues.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar(recentyDeletedOwner, recentyDeletedScore, position);
    }

    private void showUndoSnackbar(final String owner, final String score, final int position) {
        View view = activity.findViewById(R.id.coordinator_layout);
        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
                Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.snack_bar_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreOwners.add(position, owner);
                scoreValues.add(position, score);
                notifyItemInserted(position);
            }
        });
        snackbar.show();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView scoreOwnerTextView;
        public TextView scoreTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.scoreOwnerTextView = (TextView) itemView.findViewById(R.id.score_owner);
            this.scoreTextView = (TextView) itemView.findViewById(R.id.saved_score_text);
        } // constructor

    }// MyViewHolder class


}// MyRecycleViewAdapter class
