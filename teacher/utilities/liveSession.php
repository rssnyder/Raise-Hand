<?php
include '../../utilities/database.php';

  //Get the db Referance
  $db = getDB();

  //Get this live feed
  $query = "SELECT * FROM liveQueue" . $_GET['class'] . " WHERE class_id = " . $_GET['class'];
  $result = $db->query($query) or die($db->error);

  //Pass the question as JSON to the webpage
  $arr = array();
  while($class = $result->fetch_assoc()) {
    array_push($arr, $class['ID']);
    //array_push($arr, $class['username']);
    array_push($arr, $class['txt']);
  }

  echo json_encode($arr);
?>
