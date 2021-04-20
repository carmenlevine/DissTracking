<?php

include 'location.php' ;

$con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];
$time = $_POST['time'];

$Sql_query = "insert into location_history (latitude,longitude,time) value ('$latitude','$longitude','$time')";

if(mysqli_query($con,$Sql_query)){

    echo 'Data Submit Successfully';

}
else{

    echo 'Try again';

}
mysqli_close($con);
?>
