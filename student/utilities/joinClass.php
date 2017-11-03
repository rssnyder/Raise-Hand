<?php
include '../../utilities/database.php';

  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 4) {
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

  //Get the db Referance
  $db = getDB();

  if("" == trim($_POST['accessCode'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Access Code Required";
    header("Location: ../pages.php?page=joinClass");
  }
  else {
    //Find class with given access code
    $getClass = "SELECT ID FROM classes WHERE access_code = " . $_POST['accessCode'];
    $result = $db->query($getClass) or die($db->error);
    $class = $result->fetch_assoc();
    $id = $class['ID'];
    $addClass = "INSERT INTO userClasses
                  (relationship,
                  user_id,
                  class_id)
                  VALUES
                  (4,
                  " . $_SESSION['id'] . ",
                  " . $id . ");
                  ";

    $result = $db->query($addClass) or die($db->error);

    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Class Joined";
    header("Location: ../home.php");
    die("done");
  }
?>
