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
	$class_id=$_GET['classId'];
	$thread_id = $_GET['threadId'];
    //TODO: help this
    //TODO: Add in constraints, probably username and universityID, class ID? thread ID?
    $question = "SELECT * FROM threads WHERE class_id='$class_id'";
    //Excecute
    $result = $db->query($question) or die($db->error);
	die($result);

  


?>
