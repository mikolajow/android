package com.example.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CatsDataFragment extends Fragment {

    private final String CAT_DATA_FILE = "cat_data_file.txt";

    private RecyclerView catsRecycleView;
    private MyRecycleViewAdapter myRecycleViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Boolean> vaccinatedArrayList_b;
    private ArrayList<Boolean> racialArrayList_b;
    private ArrayList<String> namesArrayList_s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadDataFromFile();

        View view = inflater.inflate(R.layout.cats_data_layout, container, false);
        catsRecycleView = (RecyclerView) view.findViewById(R.id.cats_recycle_view);
        myRecycleViewAdapter = new MyRecycleViewAdapter(vaccinatedArrayList_b, racialArrayList_b, namesArrayList_s);

        layoutManager = new LinearLayoutManager(container.getContext());
        catsRecycleView.setLayoutManager(layoutManager);
        catsRecycleView.setAdapter(myRecycleViewAdapter);

        return view;
    }// on create view


    private void loadDataFromFile() {
        vaccinatedArrayList_b = new ArrayList<>();
        racialArrayList_b = new ArrayList<>();
        namesArrayList_s = new ArrayList<>();

        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput(CAT_DATA_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String oneLine;

            while ((oneLine = br.readLine()) != null) {
                String[] splitedData = oneLine.split("/");

                namesArrayList_s.add(splitedData[0]);
                vaccinatedArrayList_b.add(Boolean.parseBoolean(splitedData[1]));
                racialArrayList_b.add(Boolean.parseBoolean(splitedData[2]));

            }//while not empty file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }//if not null
        }//finally
    }// loadDataFromFile
}// class


























