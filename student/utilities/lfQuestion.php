<?php
include '../../utilities/database.php';

  //Get the db Referance
  $db = getDB();


  $actual_link = "http://$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]";


  if("" == trim($_GET['comment'])) {
    die("Comment field is empty");
  }

  $question = "INSERT INTO liveQueue" . $_GET['class'] . "
    (username,
    class_id,
    txt)
    VALUES
    ('" . $_GET['username'] . "',
    " . $_GET['class'] . ",
    '" . $_GET['comment'] . "')";

  $result = $db->query($question) or die($actual_link);

  echo json_encode($db->error);

?>
