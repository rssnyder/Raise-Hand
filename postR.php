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
	$txt=$_GET['txt'];
	$txt=str_replace("+"," ", $txt);
	$ownerID= $_GET['OID'];
    $threadID=$_GET['TID'];
    $user_name= $_GET['username'];
    $parent= $_GET['replyParent'];
    
    //finally, insert into the database
    // is the VALUES supposed to have 'points' like the insert into does?
	$stmt = "INSERT INTO replies(thread_id, owner_id, txt, user_name, parent) VALUES ('$threadID', '$ownerID', '$txt', '$user_name', '$parent')";
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

  


?>
