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
	$university=$_GET['uniID'];
	$username = $_GET['username'];
    //TODO: Add in constraints
    
    $ucheck = "SELECT * FROM threads";
    //Excecute
    $result = $db->query($ucheck) or die($db->error);
	die("Done");

  


?>
