package com.example.lab2v2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectColorListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] colorNames = {"Blue", "Green", "Yellow", "Pink", "Dark Blue", "Blood", "Orange", "Purple", "Azure"};
    private ListView listView;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_color_list_view);

        this.pref = getSharedPreferences(getString(R.string.aplication_options), MODE_PRIVATE);
        this.prefEditor = this.pref.edit();
        prefEditor.putInt(getString(R.string.pref_color_key), R.color.blood);
        prefEditor.commit();

        this.listView = (ListView) findViewById(R.id.colorsListView);
        listView.setOnItemClickListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.colorNames);
        listView.setAdapter(adapter);

    }// on create

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int choosenColorId = R.color.blood;
        switch (position){
            case 0: {choosenColorId = R.color.blue; break;}
            case 1: {choosenColorId = R.color.green; break;}
            case 2: {choosenColorId = R.color.yellow; break;}
            case 3: {choosenColorId = R.color.pink; break;}
            case 4: {choosenColorId = R.color.dark_blue; break;}
            case 5: {choosenColorId = R.color.blood; break;}
            case 6: {choosenColorId = R.color.orange; break;}
            case 7: {choosenColorId = R.color.purple; break;}
            case 8: {choosenColorId = R.color.azure; break;}
        }//switch
        prefEditor.putInt(getString(R.string.pref_color_key), choosenColorId);
        prefEditor.commit();

        Toast.makeText(this, pref.getInt(getString(R.string.pref_color_key), 0), Toast.LENGTH_SHORT).show();
    }// on item clicked
}// class
