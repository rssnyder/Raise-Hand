<?php
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
