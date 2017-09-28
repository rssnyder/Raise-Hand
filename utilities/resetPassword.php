<?php
  session_start();
  
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
  //Make sure everything is there and then reset password
  if("" == trim($_POST['password1'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Password Required";
    header("Location: ../forgotPassword.php");
  }
  else if($_POST['password1'] != $_POST['password2']) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Passwords do not match";
    header("Location: ../forgotPassword.php");
  }
  else {
    $password = $_POST['password1'];
    $uid = $_SESSION['id'];
    //Update the users account
    $ucheck = "UPDATE users SET password = '$password', reset = 0 WHERE ID = '$uid'";
    $result = $db->query($ucheck) or die($db->error);
    if($_SESSION['role'] == 1) {
      header("Location: ../admin/home.php");
    }
    else if($_SESSION['role'] == 2) {
      header("Location: ../teacher/home.php");
    }
    else {
      header("Location: ../student/home.php");
    }
  }
?>
