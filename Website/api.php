<?php 
	 //Start a session
  session_start();
  //Define paramiters and initalize connection
  $host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309sab3";
	$password="SD0wFGqd";
	$dbname="db309sab3";
  $db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());

  //Check and make sure they entered a username
  if("" == trim($_GET[‘username'])) {
$_SESSION['loggedin’]=false;
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Username";
    die("No username.");
  }
  //Check and make sure they entered a password
  else if("" == trim($_GET[‘password'])) {
$_SESSION['loggedin’]=false;
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Password";
    die("No password.");
  }
  //If both fields are populated correctly then execute login function
  else {
    //Get post variables and send to function
    $password = $_GET[‘password'];
    $username = $_GET[‘username'];
    signIn($username, $password, $db);
  }

  //Function to enter new user into database
  function signIn($username, $password, $db) {
    //Create sql command
    $insert = "SELECT pass FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    if($password==$result) {
      $_SESSION['loggedin'] = true;
      $_SESSION['username'] = $username;
      $_SESSION['error']  = false;
      $_SESSION['thread'] = "General";
      $_SESSION['role'] = $role;
    }
    else {
      //Set session varibales to notify user
      $_SESSION['loggedin'] = false;
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Sign in failed";
    }	
    Echo $_SESSION['loggedin’];
  }
	
?>