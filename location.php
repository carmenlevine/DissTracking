<?php
$host = "localhost";
$user = "root";
$pass = "";
$db = "locationtracker";

$conn = mysqli_connect($host,$user,$pass,$db);

$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

$Sql_query = "insert into location_history (latitude,longitude) value ('$latitude','$longitude')";

if(mysqli_query($conn,$Sql_query)){

    echo 'Data Submit Successfully';

}
else{

    echo 'Try again';

}
mysqli_close($conn);
?>
