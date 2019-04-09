package com.example.lab4;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity implements AddCatDataFragment.AddCatDataFragmentCommunication,
        OptionsFragment.OptionFragmentCommunication {

    private BottomNavigationView bottomNavigation;

    //flag to know latest fragment
    private boolean wasDogFlag;

    //cat data
    private String catName;
    private boolean catVaccinated;
    private boolean catRacial;

    //dog data
    private String dogName;
    private boolean dogVaccinated;
    private boolean dogRacial;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            setupDataFromBundle(savedInstanceState);
            inflateConfiguredOptionsFragment();
        }
        else {
            init();
           // Toast.makeText(this, "nowa aktywnosc", Toast.LENGTH_LONG ).show();
        }

        this.bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation_menu);
        setBottomNavigationListener();




    }







    //TODO na podstawie flagi określic ktory fragment zaladowac
    private void inflateConfiguredOptionsFragment() {
        Bundle bundle = new Bundle();

        //TODO uzupełnić dla psa
        if (wasDogFlag) {
            bundle.putString(getString(R.string.dog_name_key), dogName);
        }
        else {
            bundle.putString(getString(R.string.cat_name_key), catName);
            bundle.putBoolean(getString(R.string.cat_vaccinated_key), catVaccinated);
            bundle.putBoolean(getString(R.string.cat_racial_key), catRacial);
            OptionsFragment optionsFragment = new OptionsFragment();

            performTransaction(optionsFragment, getString(R.string.add_cat_fragment_tag), bundle, false);
        }
    }




    private void init() {
        OptionsFragment optionsFragment = new OptionsFragment();

        performTransaction(optionsFragment, getString(R.string.options_fragment_tag), null, false);
    }


    //TODO dodac nawigacje
    private void setBottomNavigationListener() {
        this.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_menu_item_home:


                        return true;
                    case R.id.nav_menu_item_dogs :

                        return true;
                    case R.id.nav_menu_item_cats :

                        return true;
                }//switch
                return false;
            }
        });
    }



    private void performTransaction(Fragment fragment, String tag, @Nullable Bundle additionalData, boolean addToBackStack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (additionalData != null) {
            fragment.setArguments(additionalData);
        }

        transaction.replace(R.id.main_fragment_container, fragment, tag);

        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }// preformTransaction

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.cat_name_key), catName);
        outState.putBoolean(getString(R.string.cat_vaccinated_key), catVaccinated);
        outState.putBoolean(getString(R.string.cat_racial_key), catRacial);

        outState.putString(getString(R.string.dog_name_key), dogName);
        outState.putBoolean(getString(R.string.dog_vaccinated_key), dogVaccinated);
        outState.putBoolean(getString(R.string.dog_racial_key), dogRacial);

        outState.putBoolean(getString(R.string.was_last_fragment_dof_flag_key), wasDogFlag);
    }


    private void setupDataFromBundle(Bundle savedInstanceState) {
        wasDogFlag = savedInstanceState.getBoolean(getString(R.string.was_last_fragment_dof_flag_key));

        catName = savedInstanceState.getString(getString(R.string.cat_name_key));
        catVaccinated = savedInstanceState.getBoolean(getString(R.string.cat_vaccinated_key));
        catRacial = savedInstanceState.getBoolean(getString(R.string.cat_racial_key));

        dogName = savedInstanceState.getString(getString(R.string.dog_name_key));
        dogVaccinated = savedInstanceState.getBoolean(getString(R.string.dog_vaccinated_key));
        dogRacial = savedInstanceState.getBoolean(getString(R.string.dog_racial_key));
    }

    @Override
    public void UpdateCatData(String name, boolean vaccinated, boolean racial) {
        this.catVaccinated = vaccinated;
        this.catRacial = racial;
        this.catName = name;
    }

    @Override
    public void wasLastFragmentDogFragment(boolean wasDog) {
        this.wasDogFlag = wasDog;
    }

























/// OLD MANIN @@@@@@@@@@@@@@@@@@@@@



//    private BottomNavigationView bottomNavigationView;
//    private ViewPager viewPager;
//
//     Fragment optionsFragment;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        viewPager = (ViewPager) findViewById(R.id.main_fragment_container);
//        configureViewPager();
//
//
//        this.bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_menu);
//        configureBottomNavigationListener();
//
//    } // on create



//    private void configureViewPager() {
//        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
//
//        optionsFragment = new OptionsFragment();
//
//        myPagerAdapter.addFragment( optionsFragment, getString(R.string.optionsFragment));
//        myPagerAdapter.addFragment(new DogDataFragment(), "dog_data_fragment");
//        myPagerAdapter.addFragment(new CatsDataFragment(), "cats_data_fragment");
//
//        this.viewPager.setAdapter(myPagerAdapter);
//    }// configure view pager
//
//
//
//    private void configureBottomNavigationListener() {
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.nav_menu_item_home:
//                        viewPager.setCurrentItem(0);
//                        return true;
//                    case R.id.nav_menu_item_dogs :
//                        viewPager.setCurrentItem(1);
//                        return true;
//                    case R.id.nav_menu_item_cats :
//                        viewPager.setCurrentItem(2);
//                        return true;
//                }//switch
//                return false;
//            } // on nav item selected
//        });
//    } // bottom navigation listener configuration
}// class
