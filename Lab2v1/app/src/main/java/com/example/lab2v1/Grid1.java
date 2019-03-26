package com.example.lab2v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class Grid1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid1);

        GridView gridView = (GridView) findViewById(R.id.gridViewActiv4);
        gridView.setAdapter(new myAdapter(this));



    }//on create


}// class
