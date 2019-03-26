package com.example.lab2v2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShowPhotosGridView extends AppCompatActivity {

    private class MyGridAdapter extends BaseAdapter {

        private Context context;
        private SharedPreferences.Editor prefEditor;
        private SharedPreferences pref;

        private int[] images = {
                R.drawable.bud1, R.drawable.bud2, R.drawable.bud3,
                R.drawable.bud4, R.drawable.bud5, R.drawable.bud6,
                R.drawable.bud7, R.drawable.bud8, R.drawable.bud9,
                R.drawable.bud10, R.drawable.bud11, R.drawable.bud12,
                R.drawable.bud13, R.drawable.bud14, R.drawable.bud15,
        };

        public MyGridAdapter(Context c){
            this.context=c;
            this.pref = getSharedPreferences(getString(R.string.aplication_options), MODE_PRIVATE);
            this.prefEditor= pref.edit();
        }


        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageButton imageButton;
            if(convertView == null){
                imageButton = new ImageButton(context);
                imageButton.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageButton.setScaleType(ImageButton.ScaleType.CENTER_CROP);
                imageButton.setPadding(8, 8, 8, 8);
            } else{
                imageButton = (ImageButton) convertView; }

            imageButton.setImageResource(images[position]);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prefEditor.putInt(getString(R.string.pref_image_id_key), images[position]);
                    prefEditor.commit();
                    Toast.makeText(context, "Wybrano obraz numer: " + (position+1), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, pref.getInt(getString(R.string.pref_image_id_key),-22), Toast.LENGTH_SHORT).show();
                }// on click
            });// set listener
            return imageButton;
        }// get view
    }// class my grid adapter



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photos_grid_view);

        GridView gridView = findViewById(R.id.photosGridView);
        gridView.setAdapter(new MyGridAdapter(this));
    }// on create
}// class



















