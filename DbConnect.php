<?php

    $servername="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$username="dbu309sab3";
	$pass="SD0wFGqd";
	$database="db309sab3";
    $conn = new mysqli($servername, $username, $pass, $database, $port, $socket);
 
//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error 
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

?>