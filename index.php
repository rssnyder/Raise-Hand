<?php
	//TODO Grab all this from a file
	//Define sql database information
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309sab3";
	$password="SD0wFGqd";
	$dbname="db309sab3";
	//Connect to database
	$db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
	//Print out host information
  echo $db->host_info;
	/*
	//Define session, check for existing user
	session_start();
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_GET['thread'] === null) {
      header("Location: index.php?thread=General");
      die("oops");
    }
    $_SESSION['thread'] = $_GET['thread'];
  } else {
    header("Location: login.php");
  }
  if($_GET['thread'] === null) {
    header("Location: index.php?thread=General");
    die("oops");
  }
	*/

?>
