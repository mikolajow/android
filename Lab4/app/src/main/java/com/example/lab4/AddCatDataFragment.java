package com.example.lab4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class AddCatDataFragment extends Fragment {

    private EditText nameInput;
    private Switch vaccinatedSwitch;
    private CheckBox rasowyCheckbox;
    private Button saveDataButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_cat_data_fragment, container, false);

        nameInput = (EditText) view.findViewById(R.id.cat_name_input);
        vaccinatedSwitch = (Switch) view.findViewById(R.id.cat_switch);
        rasowyCheckbox = (CheckBox) view.findViewById(R.id.cat_checkbox);
        saveDataButton = (Button) view.findViewById(R.id.save_cat_data_button);
        saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked(saveDataButton);
            }// onClick
        });

        return view;
    }// on create view


    private void onSaveButtonClicked(View view) {
        String message = "";
        message += nameInput.getText().toString();
        message += "/" + vaccinatedSwitch.isChecked();
        message += "/" + rasowyCheckbox.isChecked();
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    } // on save button click

} // class