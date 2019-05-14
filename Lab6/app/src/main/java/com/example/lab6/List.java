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

    //TODO odtwarzanie przy kliknięciu
    //TODO połącznie dwóch notatek w jedną


    private RecyclerView recyclerView;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;


    private ArrayList<String> titles;
    private ArrayList<String> namesAndSurnames;
    private ArrayList<String> dates;

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

        this.titles = gson.fromJson(sharedPref.getString(getString(R.string.titles_emblem), jsonDefaultList), typeArrayList_String);
        this.namesAndSurnames = gson.fromJson(sharedPref.getString(getString(R.string.names_emblem), jsonDefaultList), typeArrayList_String);
        this.dates = gson.fromJson(sharedPref.getString(getString(R.string.dates_emblem), jsonDefaultList), typeArrayList_String);
//        titles.add("a");
//        titles.add("b");
//        titles.add("c");
//        titles.add("d");
//        titles.add("e");
//        titles.add("f");
//
//        namesAndSurnames.add("a");
//        namesAndSurnames.add("b");
//        namesAndSurnames.add("c");
//        namesAndSurnames.add("d");
//        namesAndSurnames.add("e");
//        namesAndSurnames.add("f");
//
//        dates.add("a");
//        dates.add("b");
//        dates.add("c");
//        dates.add("d");
//        dates.add("e");
//        dates.add("f");
        this.recyclerView = findViewById(R.id.recycle_view);
        MyRecycleViewAdapter myAdapter = new MyRecycleViewAdapter(this.titles, this.namesAndSurnames, this.dates);

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
        String jsonTitlesList = gson.toJson(titles);
        String jsonNamesList = gson.toJson(namesAndSurnames);
        String jsonDatesList = gson.toJson(dates);
        prefEditor.putString(getString(R.string.titles_emblem), jsonTitlesList);
        prefEditor.putString(getString(R.string.names_emblem), jsonNamesList);
        prefEditor.putString(getString(R.string.dates_emblem), jsonDatesList);
        prefEditor.commit();
    }






}
