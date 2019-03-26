package com.example.lab2v1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Lista3 extends AppCompatActivity {

    public String[] ltxt1 = {"Pozycja nr 1", "Pozycja nr 2", "Pozycja nr 3", "Pozycja nr 4",
            "Pozycja nr 5", "Pozycja nr 6", "Pozycja nr 7", "Pozycja nr 8", "Pozycja nr 9", "Pozycja nr 10"};
    public String[] ltxt2 = {"Text 1", "Text 2", "Text 3", "Text 4", "Text 5", "Text 6",
            "Text 7", "Text 8", "Text 9", "Text 10"};

    private class LVitem {
        TextView tv1;
        TextView tv2;
        ImageView img;
        CheckBox cBox;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista3);

        myAdapter adapter = new myAdapter(ltxt1);
        ListView lista3 = (ListView) findViewById(R.id.listViewLista3);
        lista3.setAdapter(adapter);

        SharedPreferences sp = getSharedPreferences("dane_apki", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        int nru = sp.getInt("nr_uruchomienia", 0);
        nru++;
        Toast.makeText(getApplicationContext(), "Uruchomienie nr " + nru,
                Toast.LENGTH_SHORT).show();
        spe.putInt("nr_uruchomienia", nru);
        spe.commit();

    }//on create


    public class myAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;
        boolean[] zazn_pozycje;
        LVitem myLVitem;

        myAdapter(String[] lista) {
            super();
            zazn_pozycje = new boolean[ltxt1.length];
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }//konstruktor

        public int getCount() {
            return ltxt1.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(final int position, View listItemView, ViewGroup parent) {
            if (listItemView == null) {
                listItemView = inflater.inflate(R.layout.list_row, null);
                myLVitem = new LVitem();
                myLVitem.tv1 = (TextView) listItemView.findViewById(R.id.lrow_tv1);
                myLVitem.tv2 = (TextView) listItemView.findViewById(R.id.lrow_tv2);
                myLVitem.img = (ImageView) listItemView.findViewById(R.id.lrow_image);
                myLVitem.cBox = (CheckBox) listItemView.findViewById(R.id.lrow_checkBox);
                listItemView.setTag(myLVitem);
            } else {
                myLVitem = (LVitem) listItemView.getTag();
            }//else

            // tu ustawienia komponentów bieżącego elemementu listy
            myLVitem.tv1.setText(ltxt1[position]);
            myLVitem.tv2.setText(ltxt2[position]);
            myLVitem.cBox.setChecked(zazn_pozycje[position]);

            // słuchacze checkboxów zapamiętujące ich stan w tablicy:
            myLVitem.cBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // zapamiętanie stanu w tablicy:
                    if (((CheckBox) v).isChecked())
                        zazn_pozycje[position] = true;
                    else
                        zazn_pozycje[position] = false;

                    Toast.makeText(getApplicationContext(),
                            "Zanaczyłeś/odznaczyłeś: " + (position + 1),
                            Toast.LENGTH_SHORT).show();
                }//on click
            });
            return listItemView;
        }//get view
    }// class myAdapter

}//class
