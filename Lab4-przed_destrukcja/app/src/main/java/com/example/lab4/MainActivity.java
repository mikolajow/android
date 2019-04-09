package com.example.lab4;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

     Fragment optionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.main_fragment_container);
        configureViewPager();


        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_menu);
        configureBottomNavigationListener();

    } // on create



    private void configureViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        optionsFragment = new OptionsFragment();

        myPagerAdapter.addFragment( optionsFragment, getString(R.string.optionsFragment));
        myPagerAdapter.addFragment(new DogDataFragment(), "dog_data_fragment");
        myPagerAdapter.addFragment(new CatsDataFragment(), "cats_data_fragment");

        this.viewPager.setAdapter(myPagerAdapter);
    }// configure view pager



    private void configureBottomNavigationListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_menu_item_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.nav_menu_item_dogs :
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.nav_menu_item_cats :
                        viewPager.setCurrentItem(2);
                        return true;
                }//switch
                return false;
            } // on nav item selected
        });
    } // bottom navigation listener configuration
}// class
