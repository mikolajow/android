package com.example.lab2v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Lista2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] tablica;
    private ListView lista2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista2);

        this.tablica = new String[12];
        this.tablica[0] = "banan";
        this.tablica[1] = "gruszka";
        this.tablica[2] = "cytryna";
        this.tablica[3] = "biedronka";
        this.tablica[4] = "biedronak";
        this.tablica[5] = "biedrobestia";
        this.tablica[6] = "biedronator";
        this.tablica[7] = "biedrobanana";
        this.tablica[8] = "biedrocytrynator";
        this.tablica[9] = "ziemniak";
        this.tablica[10] = "pomidor";
        this.tablica[11] = "stonka";

        this.lista2 = (ListView) findViewById(R.id.listViewLista2);
        lista2.setOnItemClickListener(this);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, this.tablica);
        lista2.setAdapter(adapter2);

    }//koniec on create


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String napis = "";
        SparseBooleanArray zaznaczone = lista2.getCheckedItemPositions();
        for(int i = 0; i < zaznaczone.size(); i++)
        {
            if(zaznaczone.valueAt(i)) {
                int indeks = zaznaczone.keyAt(i);
                napis += (" " + String.valueOf(indeks+1));
            }
        }
        Toast.makeText(this,
                "Wybrałeś:" + napis,
                Toast.LENGTH_SHORT).show();

    }//on item click




}//class
