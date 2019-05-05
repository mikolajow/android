package com.example.lab5v1;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        boolean enabled = !sm.getSensorList(Sensor.TYPE_LIGHT).isEmpty();
        TextView tv = (TextView) findViewById(R.id.lightinfo);
        String lightStatusText = getString(R.string.light_status) + " " + getString(enabled ? R.string.txt_avail : R.string.txt_unavail);
        tv.setText(lightStatusText);
        tv.setTextColor(enabled ? Color.GREEN : Color.RED);

        findViewById(R.id.button_light).setEnabled(enabled);
        enabled = !sm.getSensorList(Sensor.TYPE_ACCELEROMETER).isEmpty();

        tv = (TextView) findViewById(R.id.accelinfo);
        String accelerationStatusText = getString(R.string.accel_status) + " " + getString(enabled ? R.string.txt_avail : R.string.txt_unavail);
        tv.setText(accelerationStatusText);
        tv.setTextColor(enabled ? Color.GREEN : Color.RED);

        findViewById(R.id.button_accel).setEnabled(enabled);

        final LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        tv = (TextView) findViewById(R.id.gpsinfo);
        String locationStatusInfo = getString(R.string.gps_status) + " " + getString(enabled ? R.string.txt_avail : R.string.txt_unavail);
        tv.setText(locationStatusInfo);
        tv.setTextColor(enabled ? Color.GREEN : Color.RED);

        findViewById(R.id.button_gps).setEnabled(enabled);
    }



    public final void startAktywnosc(final View v) {
        Intent in;

        if (v.getId() == R.id.button_gps) in = new Intent(this, GPS.class);
        else {
            in = new Intent(this, ASensor.class);

            if (v.getId() == R.id.button_light)
                in.putExtra("sensorType", Sensor.TYPE_LIGHT);
            else if (v.getId() == R.id.button_accel)
                in.putExtra("sensorType", Sensor.TYPE_ACCELEROMETER);
        }
        startActivity(in);
    }

}// activity
