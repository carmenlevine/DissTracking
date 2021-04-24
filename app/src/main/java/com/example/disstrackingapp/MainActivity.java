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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    double lat;
    double lon;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ensures the required permissions have been enabled by the device
        String[] requiredPermissions = {
                Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean ok = true;
        for (int i = 0; i < requiredPermissions.length; i++) {
            int result = ActivityCompat.checkSelfPermission(this, requiredPermissions[i]);
            if (result != PackageManager.PERMISSION_GRANTED) {
                ok = false; //if permissions haven't been granted, assign ok as false
            } else {
                ok = true; // if permissions have been granted, assign ok as true
            }
        }
        if (ok) { //getting GPS location through latitude and longitude
            ActivityCompat.requestPermissions(this, requiredPermissions, 1);
            LocationManager locationManager = (LocationManager) getSystemService((LOCATION_SERVICE));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    //When location is changed, extract the latitude and longitude and assign it to the values
                    //of lat and lon then assign it to latitude and longitude values as strings so they can be
                    //printed to the screen and passed to the database.
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

        } else { //if the permissions haven't been granted
            Toast.makeText(this, "Unable to find location", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        //Identifies the submit button, so that when this is clicked, the latitude and longitude will be
        //sent to the php file, which will then forward it onto the php my admin database as a new record.
        Button SubButton;
        SubButton = (Button) findViewById(R.id.submitButton);
        SubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //php file containing connection to the database
                String connstr = "http://192.168.1.79:8080/location.php";

                class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                    @Override
                    protected String doInBackground(String... params) {

                        try {
                            URL url = new URL(connstr); //converts the link from string to url
                            HttpURLConnection http = (HttpURLConnection) url.openConnection(); //starts connection

                            //sets the nature of the request to post, so the lat and long can be posted into the database
                            http.setRequestMethod("POST");
                            http.setDoInput(true);
                            http.setDoOutput(true);

                            //Setting up the output stream and buffered writer. Here the latitude and longitude is encoded using UTF-8
                            //so that the data can be sent over the web securely.
                            OutputStream outputStream = http.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            String postData = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8") + "&"
                                    + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
                            bufferedWriter.write(postData);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();

                            //Setting up the input stream and buffered reader. Here defines that each record will be separated by a line
                            //so that it can be classified as two separate records in the database.
                            InputStream inputStream = http.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                            String result = "";
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                result += line;
                            }
                            bufferedReader.close();
                            inputStream.close();
                            http.disconnect(); //closes the connection

                            return result;


                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return "Data Inserted Successfully";
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        //Notifies the user that their data has been uploaded successfully so that multiple records aren't added
                        super.onPostExecute(result); //result is passed from the do in background method
                        Toast.makeText(MainActivity.this, "Data Submitted Successfully", Toast.LENGTH_LONG).show();
                    }
                }

                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

                sendPostReqAsyncTask.execute(latitude, longitude);

                }
            });
        }
    }
