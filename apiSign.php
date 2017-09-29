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
	$first=$_GET['first'];
	$last=$_GET['last'];
	$pass=$_GET['pass'];
	$username = $_GET['username'];
	$email= $_GET['email'];
	$university= $_GET['university'];
	$statement="SELECT ID FROM universities WHERE name= '$university'";
	$statement=$db->query($statement) or die($db->error);
	$university_id=$statement->fetch_assoc();
	$stmt = "INSERT INTO users(role_id, first_name, last_name, pass, username, university_id, email) VALUES (4,'$first', '$last', '$pass', '$username', $university_id, '$email')";
	$stmt = $db->query($stmt) or die($db->error);
?>
