package com.example.lab2v2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddPlaceToVisit extends AppCompatActivity {

    private static final String FILE_NAME = "placesToVisitFile.txt";

    private SharedPreferences preferences;
    private SeekBar seekBar;
    private TextView seekBarText;
    private Button addPlaceButton;

    private ImageView picture;
    private EditText editTextNameInput;
    private Switch colorSwitch;

    private String choosenPriority = "Choosen Priority = ";
    private int max = 100;
    private int current = 50;
    private int selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_to_visit);

        this.addPlaceButton = (Button) findViewById(R.id.addNewPlaceButton);
        this.addPlaceButton.setEnabled(false);

        this.preferences = getSharedPreferences(getString(R.string.aplication_options), MODE_PRIVATE);
        this.seekBar = (SeekBar) findViewById(R.id.prioritySeekBar);
        this.seekBarText = (TextView) findViewById(R.id.priorityTextView);
        this.picture = (ImageView) findViewById(R.id.pictureOfSelectedPlace);

        this.editTextNameInput = (EditText) findViewById(R.id.placeNameInput);
        this.editTextNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!editTextNameInput.getText().toString().equals("")){
                    addPlaceButton.setEnabled(true);
                }else{
                    addPlaceButton.setEnabled(false);
                }
            }// after text changed
        });// add listener
        this.colorSwitch = (Switch) findViewById(R.id.colorSwitch);


        this.seekBarText.setText(choosenPriority + Integer.toString(current));
        int prefImageId = preferences.getInt(getString(R.string.pref_image_id_key), R.drawable.bud1);
        this.picture.setImageResource(prefImageId);


        this.selectedColor = preferences.getInt(getString(R.string.pref_color_key), R.color.blood);
        this.colorSwitch.setTextColor(getColor(selectedColor));

        this.seekBar.setMax(max);
        this.seekBar.setProgress(current);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                current = progress;
                seekBarText.setText(choosenPriority + Integer.toString(current));
            }// on progress changed

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });// seek bar listener

    }// on create





    public void save(View v) {
        String oneItemInfo = Integer.toString(preferences.getInt(getString(R.string.pref_image_id_key), R.drawable.bud1));
        oneItemInfo += "/" + Integer.toString(preferences.getInt(getString(R.string.pref_color_key), R.color.blood));
        oneItemInfo += "/" + editTextNameInput.getText().toString();
        boolean wanaColor = colorSwitch.isChecked();
        oneItemInfo += "/" + Boolean.toString(wanaColor);
        oneItemInfo += "/" + Integer.toString(this.current);
       oneItemInfo += "\n";

        FileOutputStream fos = null;

        try {


            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos.write(oneItemInfo.getBytes());

            editTextNameInput.getText().clear();

            Toast.makeText(this, getFilesDir().getAbsolutePath(), Toast.LENGTH_LONG).show();
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
        addPlaceButton.setEnabled(false);
    }// save




}// class
