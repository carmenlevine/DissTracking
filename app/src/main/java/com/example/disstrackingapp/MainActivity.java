package com.example.disstrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lon;
    String provider;
    String latitude, longitude;


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
        if (ok){ //getting GPS location through latitude and longitude
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null){
                lat = location.getLatitude();
                lon = location.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);

                TextView textLat = (TextView) findViewById(R.id.LatText);
                textLat.setText("Latitude = " + latitude);

                TextView textLon = (TextView) findViewById(R.id.LongText);
                textLon.setText("Longitude = " + longitude);
            }

        } else { //error - doesn't compile from this point
            Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();
            //ActivityCompat.requestPermissions(this, requiredPermissions, 1);
            System.exit(0);
        }
    }
}
