<?php
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

  $question = "INSERT INTO liveQueue" . $_GET['class'] . "
    (username,
    class_id,
    txt)
    VALUES
    (" . $_GET['username'] . ",
    " . $_GET['class'] . ",
    " . $_GET['question'] . ")";

  $result = $db->query($question) or echo json_encode($db->error);

  echo json_encode("Done.");

?>
