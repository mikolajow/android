package com.example.lab2v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ShowCustomListActivity extends AppCompatActivity {

    private static final String FILE_NAME = "placesToVisitFile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_custom_list);

        ListView customListView = findViewById(R.id.customListView);
        customListView.setAdapter(new MyListItemAdapter());
    }// on create

    public class MyListItemAdapter extends BaseAdapter {


        private class SingleItemData {
            public int imageId;
            public int colorId;
            public String placeName;
            public boolean changeColor;
            public int priority;
        }//class single list item data

        private ArrayList<SingleItemData> itemsData;

        public MyListItemAdapter() {
            this.itemsData = new ArrayList<>();
            loadData();
        }//constructor


        @Override
        public int getCount() {
            return itemsData.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.row_item, null);
            }//if

            ImageView picture = (ImageView) convertView.findViewById(R.id.list_row_image);
            TextView nameText = (TextView) convertView.findViewById(R.id.list_row_name);
            TextView priorityText = (TextView) convertView.findViewById(R.id.list_row_priority);

            SingleItemData currenDataInfo = itemsData.get(position);

            picture.setImageResource(currenDataInfo .imageId);
            nameText.setText(currenDataInfo .placeName);

            if(currenDataInfo.changeColor){
                nameText.setTextColor(getColor(currenDataInfo.colorId));
            }

            priorityText.setText( Integer.toString(itemsData.get(position).priority));

            return convertView;
        }// get view



        private void loadData() {
            FileInputStream fis = null;

            try {
                fis = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String oneLine;

                while ((oneLine = br.readLine()) != null) {
                    String[] splitedData = oneLine.split("/");
                    SingleItemData newDataItem = new SingleItemData();
                    newDataItem.imageId = Integer.parseInt(splitedData[0]);
                    newDataItem.colorId = Integer.parseInt(splitedData[1]);
                    newDataItem.placeName = splitedData[2];
                    newDataItem.changeColor = Boolean.parseBoolean(splitedData[3]);
                    newDataItem.priority = Integer.parseInt(splitedData[4]);
                    this.itemsData.add(newDataItem);
                }//while not empty file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//if not null
            }//finally
        }//load data



    }// class my list item adapter

}//class











