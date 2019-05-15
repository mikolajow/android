package com.example.lab6;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class List extends AppCompatActivity {

    //TODO odtwarzanie przy kliknięciu -> guziki pauzy, wznowienia, zakonczenia
    //TODO połącznie dwóch notatek w jedną


    private RecyclerView recyclerView;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;


    private ArrayList<String> titles;
    private ArrayList<String> namesAndSurnames;
    private ArrayList<String> dates;
    private ArrayList<String> pcmFilepathList;
    private ArrayList<String> wavFilepathList;

    Type typeArrayList_String;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        this.prefEditor = sharedPref.edit();
        this.gson = new Gson();

        ArrayList<String> defaultList = new ArrayList<>();
        String jsonDefaultList = gson.toJson(defaultList);

        this.typeArrayList_String = new TypeToken<ArrayList<String>>(){}.getType();

        this.titles = gson.fromJson(sharedPref.getString(getString(R.string.titles_list), jsonDefaultList), typeArrayList_String);
        this.namesAndSurnames = gson.fromJson(sharedPref.getString(getString(R.string.names_surnames_list), jsonDefaultList), typeArrayList_String);
        this.dates = gson.fromJson(sharedPref.getString(getString(R.string.time_list), jsonDefaultList), typeArrayList_String);
        this.pcmFilepathList = gson.fromJson(sharedPref.getString(getString(R.string.pcm_filepath_list), jsonDefaultList), typeArrayList_String);
        this.wavFilepathList = gson.fromJson(sharedPref.getString(getString(R.string.wav_filepath_list), jsonDefaultList), typeArrayList_String);

        this.recyclerView = findViewById(R.id.recycle_view);
        MyRecycleViewAdapter myAdapter = new MyRecycleViewAdapter(this.titles, this.namesAndSurnames, this.dates, this.pcmFilepathList, this.wavFilepathList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MySwipeToDeleteCallback(myAdapter, getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    } // consctuctor



    @Override
    protected void onPause() {
        super.onPause();
        prefEditor.clear();
        String jsonTitlesList = gson.toJson(this.titles);
        String jsonNameSurnameList = gson.toJson(this.namesAndSurnames);
        String jsonTimeList = gson.toJson(this.dates);
        String jsonPCMFilepathList = gson.toJson(this.pcmFilepathList);
        String jsonWAVFilepathList = gson.toJson(this.wavFilepathList);

        prefEditor.putString(getString(R.string.titles_list), jsonTitlesList);
        prefEditor.putString(getString(R.string.names_surnames_list), jsonNameSurnameList);
        prefEditor.putString(getString(R.string.time_list), jsonTimeList);
        prefEditor.putString(getString(R.string.pcm_filepath_list), jsonPCMFilepathList);
        prefEditor.putString(getString(R.string.wav_filepath_list), jsonWAVFilepathList);
        prefEditor.commit();
    }

}
