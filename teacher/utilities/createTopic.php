<?php
include '../../utilities/database.php';

  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 2) {
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

  //Check for no name
  if("" == trim($_POST['name'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Topic Name Required";
    header("Location: ../topics.php?class=" . $_GET['class'] . "&page=createTopic");
    die("No name");
  }

  $class = $_GET['class'];
  $name = $_POST['name'];
  $desc = $_POST['description'];
  //Create the topic
  $query = "INSERT INTO topics
    (class_id,
    topic_name,
    description)
    VALUES
    ($class,
    '$name',
    '$desc')";

  $result = $db->query($query) or die($db->error);

  $_SESSION['error'] = true;
  $_SESSION['errorCode'] = "Topic Created";
  header("Location: ../topics.php?class=" . $_GET['class'] . "&page=createTopic");
  die("Done");

?>
