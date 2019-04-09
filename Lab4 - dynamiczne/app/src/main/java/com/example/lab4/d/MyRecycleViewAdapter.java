package com.example.lab4.d;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lab4.R;

import java.util.ArrayList;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    private ArrayList<Boolean> vaccinatedArrayList_b;
    private ArrayList<Boolean> racialArrayList_b;
    private ArrayList<String> namesArrayList_s;

    public MyRecycleViewAdapter(ArrayList<Boolean> vaccinatedArrayList_b,
                                ArrayList<Boolean> racialArrayList_b, ArrayList<String> namesArrayList_s) {
        this.vaccinatedArrayList_b = vaccinatedArrayList_b;
        this.racialArrayList_b = racialArrayList_b;
        this.namesArrayList_s = namesArrayList_s;
    }// constructor

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.animal_data_layout, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.nameTextView.setText(namesArrayList_s.get(i));
        myViewHolder.vaccinatedCheckBox.setChecked(vaccinatedArrayList_b.get(i));
        myViewHolder.racialCheckBox.setChecked(racialArrayList_b.get(i));
    }// onBindViewHolder

    @Override
    public int getItemCount() {
        return vaccinatedArrayList_b.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CheckBox vaccinatedCheckBox;
        public CheckBox racialCheckBox;
        public TextView nameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccinatedCheckBox = (CheckBox) itemView.findViewById(R.id.szczepiony_checkbox);
            racialCheckBox = (CheckBox) itemView.findViewById(R.id.rasowy_checkbox);
            nameTextView = (TextView) itemView.findViewById(R.id.imie_ziwerzaka_text_view);
        }// constructor
    }// myViewHolder class

}// adapter class




























