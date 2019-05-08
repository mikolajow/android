package com.example.lab5v2;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GyroscopeSensor extends AppCompatActivity implements MyOwnerAlertDialog.OwnerAlertDialogListener, SensorEventListener {

    private TextView scoreView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private double bestScore;
    private String bestScoreString;

    private ArrayList<String> scoreOwners;
    private ArrayList<String> scores;

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;
    private String jsonDefaultList;
    Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope_sensor);

        this.bestScore = 0.0;
        scoreView = findViewById(R.id.score_gyro);
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        this.sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        this.prefEditor = sharedPref.edit();
        this.gson = new Gson();

        ArrayList<String> defaultList = new ArrayList<>();
        this.jsonDefaultList = gson.toJson(defaultList);

        this.type = new TypeToken<ArrayList<String>>(){}.getType();

        this.scoreOwners = gson.fromJson(sharedPref.getString(getString(R.string.owners_list),jsonDefaultList), type);
        this.scores = gson.fromJson(sharedPref.getString(getString(R.string.scores_list),jsonDefaultList), type);
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensor);

        prefEditor.clear();
        String jsonOwnersList = gson.toJson(scoreOwners);
        String jsonScores = gson.toJson(scores);
        prefEditor.putString(getString(R.string.owners_list), jsonOwnersList);
        prefEditor.putString(getString(R.string.scores_list), jsonScores);
        prefEditor.commit();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        double sum = Math.abs(event.values[0]) + Math.abs(event.values[1]) + Math.abs(event.values[2]);

        if(sum > bestScore) {
            this.bestScore = sum;
            this.bestScoreString = Double.toString(sum);
            scoreView.setText(bestScoreString);
        }
    }// onSensorChanged

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResetScoreButton(View view) {
        this.bestScore = 0.0;
        this.bestScoreString = "0";
        scoreView.setText(bestScoreString);
    }


    public void onSaveButton(View view) {

        MyOwnerAlertDialog alertDialog = new MyOwnerAlertDialog();
        alertDialog.show(getSupportFragmentManager(), "owner_alert_dialog");
    }

    @Override
    public void passOwner(String owner) {

        this.scoreOwners.add(owner);
        this.scores.add(bestScoreString);
    }
} // class
