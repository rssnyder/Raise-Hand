<?php

  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');

  //Function to send a new comment to the database
  function putComment($parentID, $owner, $text, $db) {
    $query = "INSERT INTO posts (id, parent, owner, points, endorsed, timestamp, text) VALUES (NULL, '$parentID', '$owner', '0', '0', '2017-09-01 17:00:00.000000', '$text')";
    //echo $query;
    $result = $db->query($query) or die($db->error);
    header("Location: connection.php");
  }

  $username = $_POST['username'];
  $comment =  $_POST['comment'];
  putComment("1", $username, $comment, $db);
?>
