package com.example.lab2v2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] spinnerOptions = {"Select Color Activity", "Show Photos Activity", "Show List Activity", "Add Place To Visit Activity"};
    private Class selectedActivity = ShowPhotosGridView.class;
    private TextView secretView;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.toggleButton = (ToggleButton) findViewById(R.id.featureToggleButton);
        this.secretView = (TextView) findViewById(R.id.featureTextView);
        Spinner spiner = (Spinner) findViewById(R.id.spinnerMain);
        spiner.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(spinnerAdapter);
    }// on create

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0 : { this.selectedActivity = SelectColorListView.class; break; }
            case 1 : { this.selectedActivity = ShowPhotosGridView.class; break; }
            case 2 : { this.selectedActivity = ShowCustomListActivity.class; break; }
            case 3 : { this.selectedActivity = AddPlaceToVisit.class; break; }
        }// switch
    }// on item selected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }// on nothing selected

    public void onSwitchActivityButtonClicked(View view){
        Intent intent = new Intent(this, selectedActivity);
        startActivity(intent);
    }// on switch activity button clicked

    public void onFindNewPlaces(View view){
        String url = "http://www.google.com";
        Intent openLinkIntent = new Intent(Intent.ACTION_VIEW);
        openLinkIntent.setData(Uri.parse(url));
        startActivity(openLinkIntent );
    }// on fing new places


    public void onSecretButtonClicked(View view) {
        if (!toggleButton.isChecked()) {
            Intent forResoultIntent = new Intent(this, SecretFeature.class);
            forResoultIntent.putExtra("text", "What are you looking for?");
            startActivityForResult(forResoultIntent, 1);
        }else {
            String akcja = "com.example.lab2v2.intent.action.RUN_SECOND_FEARTURE";
            Intent intencja = new Intent(akcja);
            startActivity(intencja);
        }//else toggle button on
    }// on secret button clicked

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.secretView.setText(data.getStringExtra("answer"));
    }// on activity result
}// class
