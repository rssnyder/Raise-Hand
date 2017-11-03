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
	$user_name= $_GET['username'];
	$ownerID= $_GET['OID'];
    $threadID=$_GET['TID'];
    $parent= $_GET['replyParent'];
    
    //finally, insert into the database
    if($parent == 0){
       $stmt = "INSERT INTO replies(thread_id, owner_id, txt, user_name, parent) VALUES ('$threadID', '$ownerID', '$txt', '$user_name', 0)";
    }
    else {
        $thread = "SELECT DISTINCT thread_id FROM replies WHERE ID='$parent'";
        $result = $db->query($thread) or die($db->error);
	    $stmt = "INSERT INTO replies(thread_id, owner_id, txt, user_name, parent) VALUES ('$result', '$ownerID', '$txt', '$user_name', '$parent')";
    }
	$stmt = $db->query($stmt) or die($db->error);
	die("Done");

  


?>
