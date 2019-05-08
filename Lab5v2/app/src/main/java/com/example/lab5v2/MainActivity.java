package com.example.lab5v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView sensorAvailability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sensorAvailability = findViewById(R.id.sensor_availability_text);
    }


    @Override
    protected void onResume() {
        super.onResume();

        final SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        boolean enabled = !sm.getSensorList(Sensor.TYPE_GYROSCOPE).isEmpty();
        String statusText = getString(R.string.sensor_status) + " " + getString(enabled ? R.string.available : R.string.unavailable);
        sensorAvailability.setText(statusText);
        sensorAvailability.setTextColor(enabled ? Color.GREEN : Color.RED);

        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Toast.makeText(this, Integer.toString(sharedPref.getInt("a", 0)),Toast.LENGTH_LONG).show();

        findViewById(R.id.go_to_gyroscope).setEnabled(enabled);
    } // on resume


    public void onActivitySwitch(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.go_to_gyroscope:
                intent = new Intent(this, GyroscopeSensor.class);
                break;
            case R.id.go_to_ranking:
                intent = new Intent(this, Ranking.class);
                break;
        }// switch
        startActivity(intent);
    }

} // class
