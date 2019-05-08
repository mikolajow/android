package com.example.lab5v2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Ranking extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<String> scoreOwners;
    private ArrayList<String> scores;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;
    private String jsonDefaultList;
    Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        this.prefEditor = sharedPref.edit();
        this.gson = new Gson();

        ArrayList<String> defaultList = new ArrayList<>();
        this.jsonDefaultList = gson.toJson(defaultList);

        this.type = new TypeToken<ArrayList<String>>(){}.getType();

        this.scoreOwners = gson.fromJson(sharedPref.getString(getString(R.string.owners_list), jsonDefaultList), type);
        this.scores = gson.fromJson(sharedPref.getString(getString(R.string.scores_list), jsonDefaultList), type);

        this.recyclerView = findViewById(R.id.score_recycle_view);
        MyRecycleViewAdapter myAdapter = new MyRecycleViewAdapter(scoreOwners, scores, this);

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
        String jsonOwnersList = gson.toJson(scoreOwners);
        String jsonScores = gson.toJson(scores);
        prefEditor.putString(getString(R.string.owners_list), jsonOwnersList);
        prefEditor.putString(getString(R.string.scores_list), jsonScores);
        prefEditor.commit();
    }
} // class
