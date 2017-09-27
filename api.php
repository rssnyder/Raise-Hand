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
	$username = $_GET['username'];
	$pass = $_GET['pass'];
	$stmt = "SELECT * FROM users WHERE username = '$username'";
	$stmt = $db->query($stmt) or die($db->error);
	$response = $stmt->fetch_assoc();
	$password = $response['pass'];

	//If correct password
	if(password_verify($pass, $password)) {
		$user = array(
		    'logged_in'=>"true",
		    'id'=>$response['ID'],
		    'role_id'=> $response['role_id'],
		    'username'=>$response['username'],
		    'first_name'=>$response['first_name'],
		    'last_name'=>$response['last_name']
		);
		$response['error'] = false;
		$response['message'] = 'Login successfull';
		$response['user'] = $user;
	}
	else{
	    $user=array(
	        'logged_in'=>"false"
	   );
		$response['error'] = false;
		$response['message'] = 'Invalid username or password';
		$res['class_id']=0;
	}
	if($user['logged_in']){
	    $tempID= $response['ID'];
	    $stmt = "SELECT class_id FROM UserClasses WHERE user_id = '$tempID' ";
	    //TODO: Edit so that it isn't null if the select statement doesn't return anything
	    $stmt = $db->query($stmt) or die($db->error);
	    $res = $stmt->fetch_assoc();
	}
	$final = array_merge($user, $res);
	die(json_encode($final));
?>
