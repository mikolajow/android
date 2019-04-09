package com.example.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsFragment extends Fragment {

    private static final String ADD_DOG_FRAGMENT_TAG = "addDog";
    private static final String ADD_CAT_FRAGMENT_TAG = "addCat";


    private RadioGroup radioGroup;

    private AddDogDataFragment addDogDataFragment;
    private AddCatDataFragment addCatDataFragment;

    private FragmentManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_fragment, container, false);

        layoutManager = getActivity().getSupportFragmentManager();

        addDogDataFragment = new AddDogDataFragment();
        addCatDataFragment = new AddCatDataFragment();


        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        setupRadioGroupListener();

        return view;
    }// on create view



    private void setupRadioGroupListener() {
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentTransaction transaction = layoutManager.beginTransaction();
                switch (checkedId) {
                    case R.id.radio_button_dog:
                        transaction.replace(R.id.options_fragment_container, addDogDataFragment);
                        break;
                    case R.id.radio_button_cat:
                        transaction.replace(R.id.options_fragment_container, addCatDataFragment);
                        break;
                }//swicth
                transaction.commit();
            }// onCheckedChange
        });// listener constructor
    }//setup metod

} // class
