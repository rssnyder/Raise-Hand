<?php

	//require_once 'sql.php';
	//How to initiate a connection to the sql server
	//Define sql database information
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309sab3";
	$password="SD0wFGqd";
	$dbname="db309sab3";
	//Connect to database
	$db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
	$username = $_GET['username'];
	$pass = $_GET['pass'];
	$stmt = "SELECT * FROM users WHERE username = '$username'";
	$stmt = $db->query($stmt) or die($db->error);
	$response = $stmt->fetch_assoc();
	$pass = $response['password'];
	$password = $_GET['pass'];

	//If correct password
	if(password_verify($password, $pass)) {
		die('Correct password');
	}


	if($stmt->num_rows > 0){
		$user = array(
			'username'=>$username,
			'pass'=>$pass,
			'first_name'=>$first_name,
			'last_name'=>$last_name
		);

		$response['error'] = false;
		$response['message'] = 'Login successfull';
		$response['user'] = $user;
	}
	else{
		$response['error'] = false;
		$response['message'] = 'Invalid username or password';
	}
	die(json_encode($response));
?>
