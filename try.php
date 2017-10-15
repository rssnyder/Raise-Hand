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
	    $stmt = "SELECT * FROM userClasses WHERE user_id = '$tempID' ";
	    $stmt = $db->query($stmt) or die($db->error);
	    if(mysqli_num_rows($stmt)>0){
	        Echo ' ';
	        while($row = $stmt->fetch_array()){
	             if(mysqli_num_rows($stmt)==1){
	                Echo ' '.$row['class_id'].'';
	                $que="SELECT * FROM topics WHERE class_id='$row[class_id]'";
                    $resu=$db->query($que) or die($db->error);
                     if(mysqli_num_rows($resu)>0){
                        while($roww = $resu->fetch_array()){
                            Echo 'NEWTOPIC ';
                            Echo 'CREATETIME ';
                            Echo ''.$roww['creation_time'].' ';
                            Echo 'TOPICNAME ';
                            Echo ''.$roww['topic_name'].' ';
                            Echo 'ID ';
                            Echo ''.$roww['ID'].' ';
                            Echo 'DESCRIPTION ';
                            Echo ''.$roww['description'].' ';
                        }
                     }
                  
	            }
	            else{
	                Echo ''.$row['class_id'].', ';
	                $que="SELECT * FROM topics WHERE class_id='$row[class_id]'";
                    $resu=$db->query($que) or die($db->error);
                    if(mysqli_num_rows($resu)>0){
                     while($roww = $resu->fetch_array()){
                        Echo 'NEWTOPIC ';
                        Echo 'CREATETIME ';
                        Echo ''.$roww['creation_time'].' ';
                        Echo 'TOPICNAME ';
                        Echo ''.$roww['topic_name'].' ';
                        Echo 'ID ';
                        Echo ''.$roww['ID'].' ';
                        Echo 'DESCRIPTION ';
                        Echo ''.$roww['description'].' ';
                    }
                    }
	            }
	        }
	    }
	   
	    else{
	        Echo ' '.'0';
	    }
	}
	else{
	    Echo '"logged_in ": false';
		$response['error'] = false;
		$response['message'] = 'Invalid username or password';
		$res['class_id']="0";
	}

?>
