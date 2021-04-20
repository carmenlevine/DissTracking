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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lon;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] requiredPermissions = {
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
        boolean ok = true;
        for (int i=0; i<requiredPermissions.length; i++){
            int result = ActivityCompat.checkSelfPermission(this, requiredPermissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
                ok = false;
            } else { //hits this
                //Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                ok = true;
            }
        }
           if (ok){ //getting GPS location through latitude and longitude
               //Toast.makeText(this, "Hits if ok function", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, requiredPermissions, 1);
            LocationManager locationManager = (LocationManager)getSystemService((LOCATION_SERVICE));
            //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();

                    latitude = String.valueOf(lat);
                    longitude = String.valueOf(lon);

                    TextView textLat = (TextView) findViewById(R.id.LatText);
                    textLat.setText("Latitude = " + latitude);

                    TextView textLon = (TextView) findViewById(R.id.LongText);
                    textLon.setText("Longitude = " + longitude);
                }
            });

        } else { //error
            Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();

            System.exit(0);
        }

        }
    }
