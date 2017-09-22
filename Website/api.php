<?php 
  $host="mysql.cs.iastate.edu";
$port=3306;
$socket="";
$user="dbu309sab3";
$password="SD0wFGqd";
$dbname="db309sab3";
//Connect to database
$db = mysql_connect($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
mysql_select_db($mysql_database, $db) or die (“Could not connect”);

  $response=array();
  $result = mysql_query(“select * from users where username= tester“);
  $response["success"] = 1;
  $response["message"] = "Sucessfull.";
  echo json_encode($response);

 }
?>
