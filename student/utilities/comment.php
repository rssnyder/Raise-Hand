<?php
include '../../utilities/database.php';

  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if(($_SESSION['role'] != 4) && $_SESSION['role'] != 3) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Not Permitted";
      header("Location: ../login.php");
      die("oops");
    }
  } else {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Session Expired";
    header("Location: ../login.php?event=logout");
  }

  //Get the db Referance
  $db = getDB();

  $thread = $_GET['thread'];


  if($_GET['action'] == 'comment') {
    $comment = $_POST['comment'];

    //Check for empty comment
    if("" == trim($comment)) {
      header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $_GET['thread'] . "&post=failure");
      die("No comment");
    }

    //Get the values

    $parentID = $_POST['parentID'];
    //$comment = $_POST['comment'];
    $userID = $_SESSION['id'];
    $username = $_SESSION['username'];

    $query = "INSERT INTO replies
              (thread_id,
              owner_id,
              txt,
              user_name,
              parent)
              VALUES
              ($thread,
                $userID,
                '$comment',
                '$username',
                $parentID)";

    //Submit the comment
    $result = $db->query($query) or die($db->error);
    header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $thread . "&post=success");
    die("Comment posted.");
  }
  else if($_GET['action'] == 'endorse') {
    $comment = $_GET['comment'];
    $query = "UPDATE replies
                SET
                endorsed = 1
                WHERE ID = $comment";

    $result = $db->query($query) or die($query);
    header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $thread);
    die("Comment endorsed.");
  }
  else if($_GET['action'] == 'flag') {
    $comment = $_GET['comment'];
    $query = "UPDATE replies
                SET
                flagged = 1
                WHERE ID = $comment";

    $result = $db->query($query) or die($query);
    header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $thread);
    die("Flag submitted.");
  }
  else if($_GET['action'] == 'flagThread') {
    $query = "UPDATE threads
                SET
                flagged = 1
                WHERE ID = $thread";

    $result = $db->query($query) or die($query);
    header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $thread);
    die("Flag submitted.");
  }
  header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $thread);
  die("Flag submitted.");
?>
