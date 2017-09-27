<?php
  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 1) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Not Permitted";
      header("Location: ../../login.php");
      die("oops");
    }
  } else {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Session Expired";
    header("Location: ../../login.php?event=logout");
  }
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
  //echo $db->host_info;
  //Make sure everything is there and then sign them up
  if("" == trim($_POST['first'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "First Name Required";
    header("Location: ../pages.php?page=createUser");
  }
  else if("" == trim($_POST['last'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Last Name Required";
    header("Location: ../pages.php?page=createUser");
  }
  else if("" == trim($_POST['username'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Username Required";
    header("Location: ../pages.php?page=createUser");
  }
  /*
  else if("" == trim($_POST['email'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Email Required (it can be fake)";
    header("Location: login.php");
  }
  */
  else if("" == trim($_POST['password'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Password Required";
    header("Location: ../pages.php?page=createUser");
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
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Username Taken";
      header("Location: ../pages.php?page=createUser");
      die("User exists");
    }
    $password = $_POST['password'];
    $password = password_hash($password, PASSWORD_DEFAULT);
    signUp($username, $_POST['first'], $_POST['last'], $password, $db);
  }

  //Function to enter new user into database
  function signUp($username, $first, $last, $password, $db) {
    //Create sql command
    $insert = "INSERT INTO users(role_id, first_name, last_name, pass, username) VALUES (4, '$first', '$last', '$password', '$username')";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Account Created";
    header("Location: ../pages.php?page=createUser");
    die("done");
  }
?>
