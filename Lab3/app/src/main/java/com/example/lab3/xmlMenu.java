package com.example.lab3;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class xmlMenu extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_floating_menu, menu);
            return true;
        }// on create

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.contextualItem1:
                    editText5.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    return true;
                case R.id.contextualItem2:
                    editText5.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    return true;
                case R.id.contextualItem3:
                    editText5.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    return true;
                default:
                    return false;
            }// switch
        }// on Action clicked

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }; // action mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_menu);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        registerForContextMenu(editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        registerForContextMenu(editText4);
        editText5 = (EditText) findViewById(R.id.editText5);

        editText5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActionMode(actionModeCallback);
                return true;
            }// on long click
        });
    } // on Create

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.aplication_menu, menu);
        return true;
    } // on create options menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //zmiana koloru czcionki - główne menu
            case R.id.color1menuItem:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.blue));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.blue));
                return true;
            case R.id.color2menuItem:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.red));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.red));
                return true;
            case R.id.color3menuItem:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.green));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.green));
                return true;

            //zmiana czcionki - główne menu
            case R.id.fontSizeMenuItem1:
                editText1.setTextSize(10);
                editText2.setTextSize(10);
                return true;
            case R.id.fontSizeMenuItem2:
                editText1.setTextSize(20);
                editText2.setTextSize(20);
                return true;
            case R.id.fontSizeMenuItem3:
                editText1.setTextSize(30);
                editText2.setTextSize(30);
                return true;

            // checkbox menu
            case R.id.checkboxMenuItem1:
                if(item.isChecked()){
                    item.setChecked(false);
                    editText4.setTextColor(ContextCompat.getColor(this, R.color.red));
                    return true;
                } else {
                    item.setChecked(true);
                    editText4.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    return true;
                } // else
            case R.id.checkboxMenuItem2:
                if(item.isChecked()){
                    item.setChecked(false);
                    editText4.setTextSize(10);
                    return true;
                } else {
                    item.setChecked(true);
                    editText4.setTextSize(25);
                    return true;
                } // else
            case R.id.checkboxMenuItem3:
                if(item.isChecked()){
                    item.setChecked(false);
                    editText4.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    return true;
                } else {
                    item.setChecked(true);
                    editText4.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
                    return true;
                } // else
            default:
                return super.onOptionsItemSelected(item);
        }// switch
    }// on options item selected




    @Override
    public void onCreateContextMenu (ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view ,menuInfo);
        if(view.getId() == R.id.editText3) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextual_floating_menu, menu);
        }// if
    } // on create context menu


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextualItem1:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.blue));
                return true;
            case R.id.contextualItem2:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.red));
                return true;
            case R.id.contextualItem3:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.green));
                return true;
            default:
                return super.onContextItemSelected(item);
        }// switch
    } // on ocntext item selected





}// class


















