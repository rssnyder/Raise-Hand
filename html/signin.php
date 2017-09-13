<?php
  session_start();
  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');
  //Print out host information
  //echo $db->host_info;
  //Make sure everything is there and then sign them up
  if("" == trim($_POST['username'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Username";
    header("Location: login.php");
  }
  else if("" == trim($_POST['password'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Password";
    header("Location: login.php");
  }
  else {
    $password = $_POST['password'];
    $username = $_POST['username'];
    signIn($username, $password, $db);
  }

  //Function to enter new user into database
  function signIn($username, $password, $db) {
    //Create sql command
    $insert = "SELECT * FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    $pass = $result->fetch_assoc();
    $admin = $pass["admin"];
    $pass = $pass["password"];
    if(password_verify($password, $pass)) {
      $_SESSION['loggedin'] = true;
      $_SESSION['username'] = $username;
      $_SESSION['error']  = false;
      $_SESSION['thread'] = "General";
      $_SESSION['admin'] = $admin;
      header("Location: index.php?thread=General");
    }
    else {
      $_SESSION['loggedin'] = false;
      $_SESSION['username'] = "";
      $_SESSION['thread'] = "";
      $_SESSION['admin'] = "";
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Sign in failed";
      header("Location: login.php");
    }
  }
?>
