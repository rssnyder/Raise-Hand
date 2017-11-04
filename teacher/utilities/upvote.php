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

  $points = $_GET['points'] + 1;

  $comment= substr($_GET['comment'], 4);

  $query = "UPDATE replies
    SET
    points = $points
    WHERE ID = $comment";

  $result = $db->query($query) or die($db->error);

  echo json_encode(substr($_GET['comment'], 4));

?>
