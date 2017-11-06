<?php
  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $user="dbu309sab3";
  $password="SD0wFGqd";
  $dbname="db309sab3";
  //Connect to database
  $db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());

    $email= $_GET['email'];
    $temp = password_hash($email, PASSWORD_DEFAULT);
    //make the temp pass
    $p = substr($temp, 10, 8);
    //hash the temp password
    $tempP = password_hash($p, PASSWORD_DEFAULT);
    //Update the users account
    $ucheck = "UPDATE users SET pass = '$tempP', reset = 1 WHERE ID = '$email'";
    $result = $db->query($ucheck) or die($db->error);
      //Now need to send user email with temp password
      $to = $email;
      $subject = "Your password has been reset";
      $txt = "Hello,

        You recently requested a password reset on your Raise Hand account. You will find
        your new password below. This password has a one time use, and you will be asked to
        enter a new password the next time you log in.

        " . $p . " is your temporary password.

        If you have any problems, please contact support at support@raisehand.com";
      $headers = "From: do-not-reply@raisehand.com" . "\r\n";

      mail($to,$subject,$txt,$headers);
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Please check your email for further reset instructions";
     
?>
