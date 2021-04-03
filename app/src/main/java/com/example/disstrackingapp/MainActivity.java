package com.example.disstrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] requiredPermissions = {
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean ok = true;
        for (int i=0; i<requiredPermissions.length; i++){
            int result = ActivityCompat.checkSelfPermission(this, requiredPermissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED){
                ok = false;
            } else {
                ok = true;
            }
        }
        if (!ok){ //error - doesn't compile from this point
            ActivityCompat.requestPermissions(this,requiredPermissions,1);
            System.exit(0);
        } else { //getting GPS location through latitude and longitude
            LocationManager locationManager = (LocationManager) getSystemService((LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
            });

            }
        TextView textLat = (TextView) findViewById(R.id.LatText);
        textLat.setText("Latitude = " + (String.valueOf(lat)), TextView.BufferType.EDITABLE);

        TextView textLon = (TextView) findViewById(R.id.LongText);
        textLon.setText("Longitude = " + (String.valueOf(lon)), TextView.BufferType.EDITABLE);
    }
}