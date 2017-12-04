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
	$code=$_GET['code'];
	$userID= $_GET['userID'];
    $statemnt= "SELECT ID, class_name FROM classes WHERE access_code=$code";
    $statemnt= $db->query($statemnt) or die($db->error);
    $statement=$statemnt->fetch_assoc();
    $classID=$statement['ID'];
    //finally, insert into the database
	$stmt = "INSERT INTO userClasses(relationship, user_id, class_id) VALUES (4, $userID, $classID)";
	$stmt = $db->query($stmt) or die($db->error);
	echo "className= ". $statement['class_name'];
	echo "ID= ". $classID;

?>
