package com.example.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    } // on Create


    public void runXML(View view) {
        Intent intent = new Intent(this, xmlMenu.class);
        startActivity(intent);
    }// runXML


    public void runJava (View view) {
        Intent intent = new Intent(this, javaMenu.class);
        startActivity(intent);
    }// runJava


} // class
