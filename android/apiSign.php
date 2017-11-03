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
	$first=$_GET['first'];
	$last=$_GET['last'];
	$pass=$_GET['pass'];
	//hash the password
    $password = password_hash($pass, PASSWORD_DEFAULT);
	
	$username = $_GET['username'];
    //Check for existing username
    $ucheck = "SELECT username FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($ucheck) or die($db->error);
    $uname = $result->fetch_assoc();
    $uname = $uname['username'];
    if($uname) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Username Taken";
      die("User exists");
    }
	$email_user= $_GET['emailU'];
	$email_domain=$_GET['emailD'];
	$email=$email_user."@".$email_domain;
	$university= $_GET['university'];

    //finally, insert into the database
	$stmt = "INSERT INTO users(role_id, first_name, last_name, pass, username, university_id, email) VALUES (4,'$first', '$last', '$password', '$username', '$university', '$email')";
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

  


?>
