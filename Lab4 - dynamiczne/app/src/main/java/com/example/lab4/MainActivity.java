package com.example.lab4;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.lab4.d.AddCatDataFragment;
import com.example.lab4.d.AddDogDataFragment;
import com.example.lab4.d.CatsDataFragment;
import com.example.lab4.d.DogDataFragment;

public class MainActivity extends AppCompatActivity implements OptionsFragment.OnFragmentSelection {

    private static final String DOG_FRAGMENT_TAG = "AddDogFragment";
    private static final String CAT_FRAGMENT_TAG = "AddCatFragment";

    AddDogDataFragment addDogDataFragment;
    AddCatDataFragment addCatDataFragment;
    FragmentTransaction trensaction;

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.frameLayout = (FrameLayout) findViewById(R.id.options_fragment_container);

        viewPager = (ViewPager) findViewById(R.id.main_fragment_container);
        configureViewPager();

        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_menu);
        configureBottomNavigationListener();



        if (savedInstanceState == null) {
            addDogDataFragment = new AddDogDataFragment();
            addCatDataFragment = new AddCatDataFragment();
            trensaction = getSupportFragmentManager().beginTransaction();
            trensaction.add(R.id.options_fragment_container, addDogDataFragment, DOG_FRAGMENT_TAG);
            trensaction.detach(addDogDataFragment);
            trensaction.add(R.id.options_fragment_container, addCatDataFragment, CAT_FRAGMENT_TAG);
            trensaction.detach(addCatDataFragment);
            trensaction.commit();
        }// if savedInstanceState == null
        else {
            addDogDataFragment = (AddDogDataFragment) getSupportFragmentManager().findFragmentByTag(DOG_FRAGMENT_TAG);
            addCatDataFragment = (AddCatDataFragment) getSupportFragmentManager().findFragmentByTag(CAT_FRAGMENT_TAG);
        }

    } // on create


    private void configureViewPager() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        myPagerAdapter.addFragment(new OptionsFragment(), "menu_fragment");
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


    @Override
    public void onFragmentSelectionRadioButtonClick(int opcja) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (opcja){
            case 0 :
                transaction.detach(addCatDataFragment);
                transaction.attach(addDogDataFragment);
                break;
            case 1 :
                transaction.detach(addDogDataFragment);
                transaction.attach(addCatDataFragment);
                break;
        }//switch
        transaction.commit();
    }
}// class
