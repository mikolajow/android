package com.example.lab4.d;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lab4.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddCatDataFragment extends Fragment {

    private final String CAT_DATA_FILE = "cat_data_file.txt";


    private EditText nameInput;
    private ToggleButton vaccinatedToggleButton;
    private CheckBox rasowyCheckbox;
    private Button saveDataButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_cat_data_fragment, container, false);

        nameInput = (EditText) view.findViewById(R.id.cat_name_input);
        vaccinatedToggleButton = (ToggleButton) view.findViewById(R.id.szczepiony_toggle_button);
        rasowyCheckbox = (CheckBox) view.findViewById(R.id.cat_checkbox);
        saveDataButton = (Button) view.findViewById(R.id.save_cat_data_button);
        saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }// onClick
        });

        saveDataButton.setEnabled(false);

        this.nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!nameInput.getText().toString().equals("")){
                    saveDataButton.setEnabled(true);
                }else{
                    saveDataButton.setEnabled(false);
                }
            }// after text changed
        });// add listener

        return view;
    }// on create view



    public void onSaveButtonClicked() {

        String data = "";
        data += nameInput.getText().toString();
        data += "/" + vaccinatedToggleButton.isChecked();
        data += "/" + rasowyCheckbox.isChecked();
        data += "\n";

        FileOutputStream fos = null;

        try {


            fos = getActivity().openFileOutput(CAT_DATA_FILE, Context.MODE_APPEND);
            fos.write(data.getBytes());

            nameInput.getText().clear();

            Toast.makeText(getContext(), getActivity().getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
        }// try
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } //catch
            }//if null
        }//finally
        saveDataButton.setEnabled(false);
    }// save

} // class