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
	$topic_id=$_GET['topicID'];
	$stmt = "SELECT * FROM threads WHERE topic_id='$topic_id'";
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");
?>
