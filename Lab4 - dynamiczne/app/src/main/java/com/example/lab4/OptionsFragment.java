package com.example.lab4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class OptionsFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton dogRadio;
    private RadioButton catRadio;
    private OnFragmentSelection activityConector;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_fragment, container, false);
        dogRadio = (RadioButton) view.findViewById(R.id.radio_button_dog);
        catRadio = (RadioButton) view.findViewById(R.id.radio_button_cat);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);

        setRadioGroupListener();

        return view;
    }// on create view


    public interface OnFragmentSelection {
        public void onFragmentSelectionRadioButtonClick(int opcja);
    }


    private void setRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_button_dog:
                        activityConector.onFragmentSelectionRadioButtonClick(0);
                        break;
                    case R.id.radio_button_cat:
                        activityConector.onFragmentSelectionRadioButtonClick(1);
                        break;
                }//switch
            }// on chexked change
        });// listener constructor
    }// set radio group loistener



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityConector = (OnFragmentSelection) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("musi implementować OnFragmentSelection");
        }//catch
    }// on attach


} // class
