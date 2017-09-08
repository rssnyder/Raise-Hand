<?php
  session_start();
  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  //Get SQL password from config failed
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');

  //Check for empty comment
  /*
  if("" == trim($_POST['username'])) {
    $username = 'Anonymous';
  }
  else {
    $username = $_POST['username'];

  }
  */

  //Get return URL ready
  $re = "Location: index.php?thread=";
  $direct = $_SESSION['thread'];

  $username = $_SESSION['username'];
  //$parentID = $_POST['parentID'];
  //Do not allow empty comments or parent id fields
  if(("" != trim($_POST['comment'])) && ("" != trim($_POST['parentID']))) {
    $comment =  $_POST['comment'];
    //If they have a semi-colon, we are just gonna say they are trying to inject
    //We have no tolerance here
    if (strpos($comment, ';') !== false) {
        //Get outa here
        $_SESSION['message'] = "I hope you DROP hot soup all over youself";
        header($re . $direct);
        die("Get outa here");
    }
    $parentID = $_POST['parentID'];
    putComment($parentID, $username, $comment, $db);
  }

  //Either way just go back to forum
  header($re . $direct);

  //Function to send a new comment to the database
  function putComment($parentID, $owner, $text, $db) {
    $timestamp = date('Y-m-d H:i:s.u');
    $query = "INSERT INTO posts (id, parent, owner, points, endorsed, timestamp, text) VALUES (NULL, '$parentID', '$owner', '0', '0', '$timestamp', '$text')";
    //echo $query;
    $result = $db->query($query) or die($db->error);
  }
?>
