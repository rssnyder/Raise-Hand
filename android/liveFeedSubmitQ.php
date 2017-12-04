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

// 	if("" == trim($_GET['comment'])) {
//     	die("Comment field is empty");
//   	}	

	//start to read from the url
	//all spaces are encoded as "+"
	$txt=$_GET['comment'];
	$txt=str_replace("+"," ", $txt);
    $class=$_GET['class'];
    $username= $_GET['username'];

	$stmt = "INSERT INTO liveQueue" . $class . "(username, class_id, txt) VALUES ('$username', '$class','$txt')";
	Echo $stmt;
	Echo "\r\n";
	Echo $txt;
	Echo "\r\n";
	Echo $class;
	Echo "\r\n";
	Echo $username;
	Echo "\r\n";

	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

?>