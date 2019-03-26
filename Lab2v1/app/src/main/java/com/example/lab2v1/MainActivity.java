package com.example.lab2v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] lista = {"Pozycja 1", "Pozycja 2", "Pozycja 3"};
    private String[] p = { "1", "2", "3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner opcje = (Spinner) findViewById(R.id.spinner1);
        if (opcje != null) {
            opcje.setOnItemSelectedListener(this);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            opcje.setAdapter(adapter);
        }//if

    }//on create


    public void runSecondActivity(View view){
        Intent intent = new Intent(this, Lista1.class);
        startActivity(intent);
    }//run second act


    public void runThirdActivity(View view){
        Intent intent = new Intent(this, Lista2.class);
        startActivity(intent);
    }//run second act


    public void runGridActivity(View view){
        Intent intent = new Intent(this, Grid1.class);
        startActivity(intent);
    }//run second act

    public void runCustomizedListActivity(View view){
        Intent intent = new Intent(this, Lista3.class);
        startActivity(intent);
    }//run second act

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Wybrałeś " + p[position], Toast.LENGTH_LONG).show();
    }//on item selected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this,"nic nie wybrano", Toast.LENGTH_LONG).show();
    }//on nothing selected

}//class
