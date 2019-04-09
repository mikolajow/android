package com.example.lab4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> myFragmentList;
    private ArrayList<String> myFragmentTitleList;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        this.myFragmentList = new ArrayList<>();
        this.myFragmentTitleList = new ArrayList<>();
    } // constructor

    public void addFragment(Fragment fragment, String title) {
        this.myFragmentList.add(fragment);
        this.myFragmentTitleList.add(title);
    }// add fragment

    @Override
    public Fragment getItem(int i) {
        return myFragmentList.get(i);
    } // get item

    @Override
    public int getCount() {
        return myFragmentList.size();
    } // get count
}//class
