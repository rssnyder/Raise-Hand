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
		Echo '{"logged_in": true';
		Echo ', "reset":';
		Echo ' '.$response['reset'].' ';
		Echo ',"id":';
		Echo ' '.$response['ID'].' ';
		Echo ', "role_id":';
	    Echo ' '.$response['role_id'].' ';
	    Echo ',"username":';
	    Echo ' '.$response['username'].' ';
	    Echo ', "first_name":';
	    Echo ' '.$response['first_name'].' ';
	    Echo ', "last_name":';
	    Echo ' '.$response['last_name'].' ';
	    Echo ', "class_id":';
		$response['error'] = false;
		$response['message'] = 'Login successfull';
		$tempID= $response['ID'];
	    $stmt = "SELECT class_id, class_name FROM userClasses WHERE user_id = '$tempID' ";
	    $stmt = $db->query($stmt) or die($db->error);
	    if(mysqli_num_rows($stmt)>0){
	        Echo ' ';
	        
	        while($row = $stmt->fetch_array()){
	             if(mysqli_num_rows($stmt)==1){
	                Echo ' '.$row['class_id'].'';
	                Echo ' '.$row['class_name'];
                  
	            }
	            else{
	                Echo ''.$row['class_id'].' ';
	                Echo ' '.$row['class_name'].', ';
	               
	            }
	        }
	        Echo '}';
	    }
	   
	    else{
	        Echo ' '.'0'.'}';
	    }
	}
	else{
	    Echo '"logged_in ": false';
		$response['error'] = false;
		$response['message'] = 'Invalid username or password';
		$res['class_id']="0";
	}
?>
