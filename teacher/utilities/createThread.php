<?php
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

  //Check for no name
  if("" == trim($_POST['title'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Post Title Required";
    header("Location: ../threads.php?class=" . $_GET['class'] . "&page=createThread");
    die("No name");
  }

  $topic = $_GET['topic'];
  $uID = $_SESSION['id'];
  $title = $_POST['title'];
  $desc = $_POST['description'];
  //Create the topic
  $query = "INSERT INTO threads
    (topic_id,
    owner_id,
    title,
    description)
    VALUES
    ($topic,
    $uID,
    '$title',
    '$desc')";

  //die('$query');
  $result = $db->query($query) or die($db->error);

  //Get the thread id so we can redirect the user there
  //We should use OUTPUT but it does not seem to work right now

  $query = "SELECT threads.ID
    FROM db309sab3.threads
    WHERE title = '$title' AND owner_id = $uID AND topic_id = $topic";
  $result = $db->query($query) or die($db->error);
  //Get that Post
  $topic = $result->fetch_assoc();
  //If we have 2 posts that match, just send them to general thread page
  if($ohNo = $result->fetch_assoc()) {
    header("Location: ../threads.php?class=" . $_GET['class'] . "&topic=" . $topic);
    die("Oops");
  }
  //Send them to their post
  header("Location: ../posts.php?class=" . $_GET['class'] . "&thread=" . $topic['ID']);
  die("Done");

?>
