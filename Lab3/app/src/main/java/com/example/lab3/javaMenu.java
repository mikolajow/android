package com.example.lab3;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.SubMenuBuilder;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;

public class javaMenu extends AppCompatActivity {

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            menu.add(CONTEXTUAL_ACTION_MODE_GROUP, CONTEXTUAL_ACTION_MODE_ITEM_1, menu.NONE, R.string.font_color_1);
            menu.add(CONTEXTUAL_ACTION_MODE_GROUP, CONTEXTUAL_ACTION_MODE_ITEM_2, menu.NONE, R.string.font_color_2);
            menu.add(CONTEXTUAL_ACTION_MODE_GROUP, CONTEXTUAL_ACTION_MODE_ITEM_3, menu.NONE, R.string.font_color_3);

            menu.getItem(0).setShowAsAction(0);
            menu.getItem(1).setShowAsAction(0);
            menu.getItem(2).setShowAsAction(0);
            return true;
        }// on create

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case CONTEXTUAL_ACTION_MODE_ITEM_1:
                    editText5.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
                    return true;
                case CONTEXTUAL_ACTION_MODE_ITEM_2:
                    editText5.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    return true;
                case CONTEXTUAL_ACTION_MODE_ITEM_3:
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




    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;


    private final int OPTION_MENU_FONT_COLOR_1 = 1;
    private final int OPTION_MENU_FONT_COLOR_2 = 2;
    private final int OPTION_MENU_FONT_COLOR_3 = 3;


    private final int OPTION_MENU_SUBMENU = 4;
    private final int OPTION_MENU_SUBMENU_GROUP = 100;
    private final int OPTION_MENU_FONT_SIZE_1 = 5;
    private final int OPTION_MENU_FONT_SIZE_2 = 6;
    private final int OPTION_MENU_FONT_SIZE_3 = 7;

    private final int CHECKBOX_MENU_ITEM = 8;
    private final int CHECKBOX_MENU_ITEM_GROUP = 200;
    private final int CHECKBOX_MENU_FONT_COLOR = 9;
    private final int CHECKBOX_MENU_FONT_SIZE = 10;
    private final int CHECKBOX_MENU_BACKGROUND_COLOR = 11;

    private final int CONTEXT_MENU_ITEM_1 = 12;
    private final int CONTEXT_MENU_ITEM_2 = 13;
    private final int CONTEXT_MENU_ITEM_3 = 14;


    private final int CONTEXTUAL_ACTION_MODE_GROUP= 300;
    private final int CONTEXTUAL_ACTION_MODE_ITEM_1= 16;
    private final int CONTEXTUAL_ACTION_MODE_ITEM_2= 17;
    private final int CONTEXTUAL_ACTION_MODE_ITEM_3= 18;



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
        menu.add(menu.NONE, OPTION_MENU_FONT_COLOR_1, menu.NONE, R.string.font_color_1);
        menu.add(menu.NONE, OPTION_MENU_FONT_COLOR_2, menu.NONE, R.string.font_color_2);
        menu.add(menu.NONE, OPTION_MENU_FONT_COLOR_3, menu.NONE, R.string.font_color_3);

        menu.addSubMenu(OPTION_MENU_SUBMENU_GROUP, OPTION_MENU_SUBMENU, menu.NONE, R.string.wielkosc_czcionki);
        MenuItem subMenu = (MenuItem) menu.getItem(3);

        subMenu.getSubMenu().add(OPTION_MENU_SUBMENU_GROUP, OPTION_MENU_FONT_SIZE_1, menu.NONE, R.string.wielkosc_czcionki_1);
        subMenu.getSubMenu().add(OPTION_MENU_SUBMENU_GROUP, OPTION_MENU_FONT_SIZE_2, menu.NONE, R.string.wielkosc_czcionki_2);
        subMenu.getSubMenu().add(OPTION_MENU_SUBMENU_GROUP, OPTION_MENU_FONT_SIZE_3, menu.NONE, R.string.wielkosc_czcionki_3);

        menu.addSubMenu(CHECKBOX_MENU_ITEM_GROUP, CHECKBOX_MENU_ITEM, menu.NONE, R.string.checkbox);

        MenuItem subMenu2 = (MenuItem) menu.getItem(4);
        subMenu2.setShowAsAction(1);
        subMenu2.setIcon(R.drawable.baseline_check_box_black_18dp);

        subMenu2.getSubMenu().add(CHECKBOX_MENU_ITEM_GROUP, CHECKBOX_MENU_FONT_COLOR, menu.NONE, R.string.red_blue_font);
        subMenu2.getSubMenu().add(CHECKBOX_MENU_ITEM_GROUP, CHECKBOX_MENU_FONT_SIZE, menu.NONE, R.string.big_or_small);
        subMenu2.getSubMenu().add(CHECKBOX_MENU_ITEM_GROUP, CHECKBOX_MENU_BACKGROUND_COLOR, menu.NONE, R.string.background);

        subMenu2.getSubMenu().getItem(0).setCheckable(true);
        subMenu2.getSubMenu().getItem(1).setCheckable(true);
        subMenu2.getSubMenu().getItem(2).setCheckable(true);
        return true;
    } // on create options menu




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //zmiana koloru czcionki - główne menu
            case OPTION_MENU_FONT_COLOR_1:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.blue));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.blue));
                return true;
            case OPTION_MENU_FONT_COLOR_2:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.red));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.red));
                return true;
            case OPTION_MENU_FONT_COLOR_3:
                editText1.setTextColor(ContextCompat.getColor(this, R.color.green));
                editText2.setTextColor(ContextCompat.getColor(this, R.color.green));
                return true;

            //zmiana czcionki - główne menu
            case OPTION_MENU_FONT_SIZE_1:
                editText1.setTextSize(10);
                editText2.setTextSize(10);
                return true;
            case OPTION_MENU_FONT_SIZE_2:
                editText1.setTextSize(20);
                editText2.setTextSize(20);
                return true;
            case OPTION_MENU_FONT_SIZE_3:
                editText1.setTextSize(30);
                editText2.setTextSize(30);
                return true;

            // checkbox menu
            case CHECKBOX_MENU_FONT_COLOR:
                if(item.isChecked()){
                    item.setChecked(false);
                    editText4.setTextColor(ContextCompat.getColor(this, R.color.red));
                    return true;
                } else {
                    item.setChecked(true);
                    editText4.setTextColor(ContextCompat.getColor(this, R.color.blue));
                    return true;
                } // else
            case CHECKBOX_MENU_FONT_SIZE:
                if(item.isChecked()){
                    item.setChecked(false);
                    editText4.setTextSize(10);
                    return true;
                } else {
                    item.setChecked(true);
                    editText4.setTextSize(25);
                    return true;
                } // else
            case CHECKBOX_MENU_BACKGROUND_COLOR:
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
            menu.add(menu.NONE, CONTEXT_MENU_ITEM_1, menu.NONE, R.string.font_color_1);
            menu.add(menu.NONE, CONTEXT_MENU_ITEM_2, menu.NONE, R.string.font_color_2);
            menu.add(menu.NONE, CONTEXT_MENU_ITEM_3, menu.NONE, R.string.font_color_3);
        }// if
    } // on create context menu


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case CONTEXT_MENU_ITEM_1:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.blue));
                return true;
            case CONTEXT_MENU_ITEM_2:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.red));
                return true;
            case CONTEXT_MENU_ITEM_3:
                editText3.setTextColor(ContextCompat.getColor(this, R.color.green));
                return true;
            default:
                return super.onContextItemSelected(item);
        }// switch
    } // on ocntext item selected


}// class
