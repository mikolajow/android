package com.example.lab4;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_menu);
        configureBottomNavigationListener();

        viewPager = (ViewPager) findViewById(R.id.main_fragment_container);
        configureViewPager();
    } // on create




    //TODO dodoać kolejne 2 fragmenty nawigaci do adaptera
    private void configureViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addFragment(new OptionsFragment(), "menu_fragment");
        this.viewPager.setAdapter(myPagerAdapter);
    }// configure view pager


    private void configureBottomNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_menu_item_home:
                        // odpal fragmenty menu
                        return true;
                    case R.id.nav_menu_item_dogs :
                        // odpal fragment o psach
                        return true;
                    case R.id.nav_menu_item_cats :
                        // odpal fragment o kotach
                        return true;
                }//switch
                return false;
            } // on nav item selected
        });
    } // bottom navigation listener configuration
}// class
