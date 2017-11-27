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

  //Email teacher with log of live session
  //Get this live feed
  $query = "SELECT * FROM liveQueue" . $_GET['class'] . " WHERE class_id = " . $_GET['class'];
  $result = $db->query($query) or die($db->error);

  //Build the email
  $today = getdate();
  $body = "The following is the log of the live session from " . $today['weekday'] . ", " . $today['month'] . $today['mday'] . "\r\n\n\n";
  while($question = $result->fetch_assoc()) {
    $body = $body . $question['txt'] . " - " . $question['username'] . "\r\n\n";
  }
  //Now with body we can build the email and send it
  $to = $_SESSION['email'];
  $subject = "Live Session on " . $today['weekday'] . ", " . $today['month'] . " " . $today['mday'];
  $headers = "From: do-not-reply@raisehand.com" . "\r\n";

  mail($to,$subject,$body,$headers);

  //SQL to drop table of class live feed
  $destroy = "DROP TABLE db309sab3.liveQueue" . $_GET['class'];

  $result = $db->query($destroy) or die($db->error);
  header("Location: ../pages.php?class=" . $_GET['class']);


?>
