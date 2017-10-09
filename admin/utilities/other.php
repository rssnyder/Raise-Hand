<?php
  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 1) {
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

  if($_GET['type'] == 'makeAdmin') {
    $uname = $_POST['username'];
    if("" == trim($_POST['username'])) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Username Required";
      header("Location: ../pages.php?page=other");
      die('No username');
    }
    else {
      $makeAdmin = "UPDATE users SET role_id = 1 WHERE username = '$uname'";
      $result = $db->query($makeAdmin) or die($makeAdmin);
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "User Level Elivated";
      header("Location: ../pages.php?page=other");
    }
  }
  else if($_GET['type'] == 'removeComment') {
    $comment = $_GET['comment'];
    $query = "UPDATE replies
                SET
                txt = 'REMOVED',
                user_id = ' ',
                flagged = 0
                WHERE ID = $comment";

    $result = $db->query($query) or die($query);
    header("Location: ../pages.php?page=viewReports");
    die("comment removed.");
  }
  else if($_GET['type'] == 'dismissComment') {
    $comment = $_GET['comment'];
    $query = "UPDATE replies
                SET
                flagged = 0
                WHERE ID = $comment";

    $result = $db->query($query) or die($query);
    header("Location: ../pages.php?page=viewReports");
    die("flag dismissed.");
  }
?>
