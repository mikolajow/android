package com.example.lab4v1;

import android.drm.DrmStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class Activity2 extends AppCompatActivity implements ActionBar.TabListener {

    Fragment11 f11;
    Fragment12 f12;
    FragmentTransaction transakcja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        f11 = new Fragment11();
        f12 = new Fragment12();
        transakcja = getSupportFragmentManager().beginTransaction();
        transakcja.add(R.id.container2, f11);
        transakcja.detach(f11);
        transakcja.add(R.id.container2, f12);
        transakcja.detach(f12);
        transakcja.commit();




        ActionBar.Tab tab;
        tab = actionBar.newTab();
        tab.setText("Tab 1");
        tab.setTabListener(this);
        actionBar.addTab(tab);

        ActionBar.Tab tab2;
        tab2 = actionBar.newTab();
        tab2.setText("Tab 2");
        tab2.setTabListener(this);
        actionBar.addTab(tab2);
    }// on create


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        switch (tab.getPosition()) {
            case 0:
                fragmentTransaction.attach(f11);
                break;
            case 1:
                fragmentTransaction.attach(f12);
                break;
        }//swicth
    }// on table selected

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        switch (tab.getPosition()) {
            case 0:
                fragmentTransaction.detach(f11);
                break;
            case 1:
                fragmentTransaction.detach(f12);
                break;
        }//swicth
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}// class
