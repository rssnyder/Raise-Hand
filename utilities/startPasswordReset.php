<?php
include 'database.php';

  session_start();

  //Connect to database
  $db = getDB();

  //Make sure everything is there and then reset password
  if("" == trim($_POST['email'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Email Required";
    header("Location: ../forgotPassword.php");
    die('Go back');
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
      //make the temp pass
      $p = substr($temp, 10, 8);
      //hash the temp password
      $tempP = password_hash($p, PASSWORD_DEFAULT);
      //Update the users account
      $ucheck = "UPDATE users SET pass = '$tempP', reset = 1 WHERE ID = '$uid'";
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
      header("Location: ../login.php");
      die("go home");
    }
    else {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Email not found";
      header("Location: ../forgotPassword.php");
      die("No found email.");
    }
  }
?>
