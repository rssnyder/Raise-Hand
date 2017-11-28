<?php
    //require_once 'sql.php';
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309sab3";
	$password="SD0wFGqd";
	$dbname="db309sab3";
	//Connect to database
	$db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());

	$query = "SELECT * FROM liveQueue" . $_GET['class'] . " WHERE classID = " . $_GET['class'];
  	$result = $db->query($query) or die($db->error);

  	Echo '{"live_session": true';
  	Echo ', "ID":';
  	Echo ' '.$response['ID'].' ';
  	Echo ', "username":';
  	Echo ' '.$response['username'].' ';
  	Echo ', "class_id":';
  	Echo ' '.$response['class_id'].' ';
  	Echo ', "creation":';
  	Echo ' '.$response['creation'].' ';
  	Echo ', "txt":';
  	Echo ' '.$response['txt'].' ';
  	Echo '}';

?>