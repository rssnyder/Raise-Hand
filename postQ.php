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
	//all spaces are encoded as "+"
	$description=$_GET['desc'];
	$description=str_replace("+"," ", $description);
	$title=$_GET['title'];
	$title=str_replace("+"," ", $title);
	$ownerID= $_GET['OID'];
    $topicID=$_GET['TID'];
    $user_name= $_GET['username'];
    
    //finally, insert into the database
    // is the VALUES supposed to have 'points' like the insert into does?
	$stmt = "INSERT INTO threads(topic_id, owner_id, title, description, user_name) VALUES ('$topicID', '$ownerID', '$title', '$description', '$user_name')";
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

  


?>