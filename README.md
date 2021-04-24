Tracking criminal behaviour using mobile phones

This project includes the development of an application called Android Tracker.
----------Description of the system------------------------------------

Android Tracker extracts the location of the device and presents it in the form of latitude and longitude on the user interface. Once the submit location button is clicked, the application sends the latitude and longitude to a PHP script, which that inputs the data into a database called locationtracker inside a table called location_history, which allocates a unique identification key and a current timestamp.

-----------How to run on your device-----------------------------------

In order to run this project, the following software will be required: Android Studio, a web browser, a text editor and XAMPP. An empty project should be made in Android Studio. In this project, the following files need to be copied: AndroidManifest.xml, activity_main.xml and MainActivity.java. The '192.168.1.79:8080: section of the address in line 89 in MainActivity.java needs to be replaced with the IP address and port being used, as follows: 'IP address:port'. The 'location.php' section of the address must be the same as the filename of the PHP file.

In a text editor, the file location.php should be created copying the contents. In order for this file to function, the database must be created using the same host name, username, password, database name and table name. If these values are different, please adjust accordingly.

Using the XAMPP Control Panel, start Apache and MySQL. In my case, I have used port 8080 and 4433. In order for this to work, I altered the ports stated in the Apache config. To do this, click the config button for Apache and select httpd.conf. Here, find the line that states 'Listen 80' and change it to 'Listen 8080'. Also find the line that states 'ServerName localhost:80' and change it to 'SeverName localhost:8080'. From the Apache config select file httpd-ssl.conf. Find the line 'Listen 443' and change it to 'Listen 4433'. Find the line that states '<VirtualHost _default_:443>' and change the 443 to 4433.

Select the admin button for MySQL. This should bring up a page in your web browser. If does this not happen, search the following in your web browser: http://localhost:8080/phpmyadmin. Don't forget to change the '8080' to the port you are using. This should take you to phpMyAdmin. Here, create a new database called locationtracker, which includes a table called location_history. This table should have four fields; ID, Latitude, Longitude and Time. The ID field needs to be an integer and auto incremental. The latitude and longitude fields must be doubles. The time field needs to be of type timestamp and the default should be set to the current timestamp.

Now you are all set up. Create and run an emulator in android studio, ensure a location is set on the device. Click the submit location button in the application, update the web browser and a new entry with the latitude and longitude of the device will be added to the table.

------------Disclosure-------------------------------------------------
This project was developed as part of the Project unit for project RC.171 Tracking criminal behaviour using mobile phones at Manchester Metropolitan Unversity by Carmen Mae Levine, student number 18003552.

Github Repository
https://github.com/carmenlevine/DissTracking
