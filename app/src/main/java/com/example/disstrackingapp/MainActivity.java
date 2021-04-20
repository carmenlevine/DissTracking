package com.example.disstrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lon;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button SubButton;
        SubButton = (Button) findViewById(R.id.submitButton);
        SubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData(latitude, longitude);
            }
        });

        String[] requiredPermissions = {
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean ok = true;
        for (int i = 0; i < requiredPermissions.length; i++) {
            int result = ActivityCompat.checkSelfPermission(this, requiredPermissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
                ok = false;
            } else { //hits this
                //Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                ok = true;
            }
        }
        if (ok) { //getting GPS location through latitude and longitude
            //Toast.makeText(this, "Hits if ok function", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, requiredPermissions, 1);
            LocationManager locationManager = (LocationManager) getSystemService((LOCATION_SERVICE));
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
    String ServerURL = "jdbc:mysql://localhost:3306/locationtracker";

    public void InsertData ( final String latitude, final String longitude){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String LatHolder = latitude;
                String LonHolder = longitude;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("latitude", LatHolder));
                nameValuePairs.add(new BasicNameValuePair("longitude", LonHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }

                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(MainActivity.this, "Data Submitted Successfully", Toast.LENGTH_LONG).show();
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(latitude, longitude);

    }
}
