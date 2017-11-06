<?php
  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $user="dbu309sab3";
  $password="SD0wFGqd";
  $dbname="db309sab3";
  //Connect to database
  $db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
  
  
  if("" == trim($_POST['password1'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Password Required";
    header("Location: ../resetPassword.php");
  }
  else {
    $password = $_POST['password1'];
    $temp = password_hash($password, PASSWORD_DEFAULT);
    $uid = $_SESSION['id'];
    //Update the users account
    $ucheck = "UPDATE users SET pass = '$temp', reset = 0 WHERE ID = '$uid'";
    $result = $db->query($ucheck) or die($db->error);
  }
?>
