package com.example.lab4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsFragment extends Fragment {


    private OptionFragmentCommunication activityCommunicator;
    private boolean wasDogFragmentLast;

    private RadioGroup radioGroup;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_fragment, container, false);

        Bundle bundle = getArguments();

        //TODO zmienic flage na psa
        if (bundle == null) {
            wasDogFragmentLast = false;
            onit();
        }
        else {
            inflateFragmentWithOldData(bundle);
        }


        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        setupRadioGroupListener();
        return view;
    }// on create view



    //TODO na podstawie Bundla - flagi zaladowac odpowiedni layout i wypelnic go danymi
    private void inflateFragmentWithOldData(Bundle bundle) {
        wasDogFragmentLast = bundle.getBoolean(getString(R.string.was_last_fragment_dof_flag_key));

        if (wasDogFragmentLast) {
            AddDogDataFragment addDogDataFragment = new AddDogDataFragment();
            performTransaction(addDogDataFragment, getString(R.string.add_dog_fragment_tag), bundle);
        } else {
            AddCatDataFragment addCatDataFragment = new AddCatDataFragment();
            performTransaction(addCatDataFragment, getString(R.string.add_cat_fragment_tag), bundle);
        }


        //Toast.makeText(getContext(), bundle.getString(getString(R.string.cat_name_key)), Toast.LENGTH_LONG ).show();

    }




    //TODO zmienić domyslne ladowanie dla psa
    private void onit() {
        AddCatDataFragment addCatDataFragment = new AddCatDataFragment();
        performTransaction(addCatDataFragment, getString(R.string.add_cat_fragment_tag), null);
    }




    private void performTransaction(Fragment fragment, String tag, @Nullable Bundle additionalData) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        if (additionalData != null) {
            fragment.setArguments(additionalData);
        }

        transaction.replace(R.id.options_fragment_container, fragment, tag);

        transaction.commit();
    }// preformTransaction


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityCommunicator.wasLastFragmentDogFragment(wasDogFragmentLast);
    }

    public interface OptionFragmentCommunication {
        public void wasLastFragmentDogFragment(boolean wasDog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCommunicator = (OptionFragmentCommunication) getActivity();
    }



    private void setupRadioGroupListener() {
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.radio_button_dog:
                        wasDogFragmentLast = true;
                        performTransaction(new AddDogDataFragment(), getString(R.string.add_dog_fragment_tag), null);
                        break;
                    case R.id.radio_button_cat:
                        wasDogFragmentLast = false;
                        performTransaction(new AddCatDataFragment(), getString(R.string.add_dog_fragment_tag), null);
                        break;
                }//swicth
                transaction.commit();
            }// onCheckedChange
        });// listener constructor
    }//setup metod

} // class
