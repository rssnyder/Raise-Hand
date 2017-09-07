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
    header("Location: login.php");
  }
  else if("" == trim($_POST['email'])) {
    header("Location: login.php");
  }
  else if("" == trim($_POST['password'])) {
    header("Location: login.php");
  }
  else {
    $username = $_POST['username'];
    //Check for existing username
    $ucheck = "SELECT username FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($ucheck) or die($db->error);
    $uname = $result->fetch_assoc();
    $uname = $uname['username'];
    if($uname) {
      header("Location: login.php");
      die("User exists");
    }

    $password = $_POST['password'];
    $password = password_hash($password, PASSWORD_DEFAULT);
    signUp($username, $_POST['email'], $password, $db);
  }

  //Function to enter new user into database
  function signUp($username, $email, $password, $db) {
    //Create sql command
    $insert = "INSERT INTO users (username, email, password, points) VALUES ('$username', '$email', '$password', '0')";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    $_SESSION['loggedin'] = true;
    $_SESSION['username'] = $username;
    header("Location: index.php");
  }
?>
