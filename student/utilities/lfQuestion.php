<?php
include '../../utilities/database.php';

  //Get the db Referance
  $db = getDB();

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

  $result = $db->query($question) or die($db->error);

  echo json_encode($db->error);

?>
