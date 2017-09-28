<?php
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
  //Make sure everything is there and then create the class

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
    header("Location: ../pages.php?page=joinClass");
    die("done");
  }
?>
