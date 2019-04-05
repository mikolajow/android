package com.example.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OptionsFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton dogRadio;
    private RadioButton catRadio;
    private ViewPager viewPager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_fragment, container, false);
        dogRadio = (RadioButton) view.findViewById(R.id.radio_button_dog);
        catRadio = (RadioButton) view.findViewById(R.id.radio_button_cat);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button_dog :
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radio_button_cat :
                        viewPager.setCurrentItem(2);
                        break;
                }// switch
            }// listener
        });// set listener

        viewPager = view.findViewById(R.id.options_fragment_container);
        configViewPagerAdapter();

        return view;
    }// on create view

    //TODO dodać nawigacje do add cat data fragment
    private void configViewPagerAdapter() {
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new WellcomeFragment(), "Welcome");
        adapter.addFragment(new AddDogDataFragment(), "add_dog_data");
        adapter.addFragment(new AddCatDataFragment(), "Add Cat Data");
        this.viewPager.setAdapter(adapter);
    }// config view pager adapter

} // class
