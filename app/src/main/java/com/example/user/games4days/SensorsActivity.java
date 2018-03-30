package com.example.user.games4days;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SensorsActivity extends Activity implements SensorEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        String toShow = "";
        if(sensorList != null) {
            for (Sensor s: sensorList)
            {
                toShow += "Name : " + s.getName() + "\nType : " + s.getStringType() + "\nVendor : " + s.getVendor() + "\n\n";
                sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
        TextView textView = findViewById(R.id.text_view_sensors);
        textView.setText(toShow);
    }

    @Override
    public void onSensorChanged(SensorEvent s) {
        String toShow = "";
        for (int tr = 0; tr < s.values.length; tr++)
            toShow += "Sensor = " + s.sensor.getName() + "\n" + String.format("%f,", s.values[tr]) + "\n";
        TextView textView = findViewById(R.id.text_view_sensors_values);
        textView.setText(String.format("%s%s", textView.getText(), toShow));
    }

    @Override
    public void onAccuracyChanged(Sensor s, int i) {

    }
}
