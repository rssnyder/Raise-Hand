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
  if("" == trim($_POST['email'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Email Required";
    header("Location: ../forgotPassword.php");
  }
  else {
    $email = $_POST['email'];
    $ucheck = "SELECT users.ID, users.reset, users.email FROM users WHERE  email = '$email' ";
    //Excecute
    $result = $db->query($ucheck) or die($db->error);
    if($uname = $result->fetch_assoc()) {
      $uid = $uname['ID'];
      //Hash the email
      $temp = password_hash($uname['email'], PASSWORD_DEFAULT);
      //Make the temp password
      $tempP = password_hash(substr($pass, 10, 8), PASSWORD_DEFAULT);
      //Update the users account
      $ucheck = "UPDATE users SET password = '$tempP', reset = 1 WHERE ID = '$uid'";
      $result = $db->query($ucheck) or die($db->error);
      //Now need to send user email with temp password
      
    }
    else {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Email not found";
      header("Location: ../forgotPassword.php");
      die("No found email.");
    }
  }
?>
