<?php
  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $user="dbu309sab3";
  $password="SD0wFGqd";
  $dbname="db309sab3";
  //Connect to database
  $db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
  

    $password = $_GET['pass'];
    $temp = password_hash($password, PASSWORD_DEFAULT);
    $username = $_GET['username'];
    //Update the users account
    $ucheck = "UPDATE users SET pass = '$temp', reset = 0 WHERE username = '$username'";
    $result = $db->query($ucheck) or die($db->error);
    Echo 'Password has been changed';


?>
