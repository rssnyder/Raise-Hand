<?php

  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');

  //Check for empty username
  if("" == trim($_POST['username'])) {
    $username = 'Anonymous';
  }
  else {
    $username = $_POST['username'];

  }

  //Do not allow empty comments
  if("" != trim($_POST['comment'])) {
    $comment =  $_POST['comment'];
    putComment("1000", $username, $comment, $db);
  }

  //If we ever get here, just go back to forum
  header("Location: forum.php");

  //Function to send a new comment to the database
  function putComment($parentID, $owner, $text, $db) {
    $query = "INSERT INTO posts (id, parent, owner, points, endorsed, timestamp, text) VALUES (NULL, '$parentID', '$owner', '0', '0', '2017-09-01 17:00:00.000000', '$text')";
    //echo $query;
    $result = $db->query($query) or die($db->error);
    header("Location: connection.php");
  }
?>
