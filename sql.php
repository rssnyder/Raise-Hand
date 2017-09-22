<?php
//How to initiate a connection to the sql server
//Define sql database information
$host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$user="dbu309sab3";
$password="SD0wFGqd";
$dbname="db309sab3";
//Connect to database
$db = mysql_connect($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
//mysql_select_db($mysql_database, $db) or die (“Could not connect”);
?>
