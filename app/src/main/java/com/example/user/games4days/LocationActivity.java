package com.example.user.games4days;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean net = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.v("TAG","GPS:"+String.valueOf(gps)+" Net:"+String.valueOf(net));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==PackageManager.PERMISSION_GRANTED) {

            if(net)
               lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 5, this);
            if(gps)
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }else{
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
            if(net)
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 5, this);
            if(gps)
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location l) {
        Log.v("TAG",String.format("Location changed: Long:%f, Lat:%f",(float)l.getLongitude(),(float)l.getLatitude()));
        TextView textView = findViewById(R.id.text_view_location);
        String toShow = String.format("Location changed: Long:%f, Lat:%f", (float)l.getLongitude(), (float)l.getLatitude());
        textView.setText(toShow);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.v("TAG","Status Changed");
        TextView textView = findViewById(R.id.text_view_status);
        String toShow = "Status changed.";
        textView.setText(toShow);
        Toast.makeText(LocationActivity.this, toShow, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.v("TAG",String.format("Provider enabled: %s", s));
        TextView textView = findViewById(R.id.text_view_provider);
        String toShow = String.format("Provider enabled: %s", s);
        textView.setText(toShow);
        Toast.makeText(LocationActivity.this, toShow, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.v("TAG",String.format("Provider disabled: %s", s));
        TextView textView = findViewById(R.id.text_view_provider);
        String toShow = String.format("Provider disabled: %s", s);
        textView.setText(toShow);
        Toast.makeText(LocationActivity.this, toShow, Toast.LENGTH_SHORT).show();
    }
}
