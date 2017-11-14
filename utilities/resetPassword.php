<?php
include 'database.php';

  session_start();

  //Connect to database
  $db = getDB();

  //Make sure everything is there and then reset password
  if("" == trim($_POST['password1'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Password Required";
    header("Location: ../resetPassword.php");
  }
  else if($_POST['password1'] != $_POST['password2']) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Passwords do not match";
    header("Location: ../resetPassword.php");
  }
  else {
    $password = $_POST['password1'];
    $temp = password_hash($password, PASSWORD_DEFAULT);
    $uid = $_SESSION['id'];
    //Update the users account
    $ucheck = "UPDATE users SET pass = '$temp', reset = 0 WHERE ID = '$uid'";
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
