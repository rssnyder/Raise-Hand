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
	
	//start to read from the url
	$description=$_GET['desc'];
	$title=$_GET['title'];
	$username = $_GET['username'];
	$ownerID= $_GET['OID'];
	$classID= $_GET['CID'];
	$universityID=$_GET['UID'];
    $ucheck = "SELECT username FROM users WHERE username = '$username' ";
    
    //finally, insert into the database
	$stmt = "INSERT INTO threads(topic_id, owner_id, title, description, endorsed_user_id, flagged,points, user_name) VALUES (4,'$first', '$last', '$password', '$username', '$university', '$email')";
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

  


?>