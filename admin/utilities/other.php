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
                user_name = ' ',
                flagged = 0
                WHERE ID = $comment";

    $result = $db->query($query) or die($db->error);
    header("Location: ../pages.php?page=viewReports");
    die("comment removed.");
  }
  else if($_GET['type'] == 'dismissComment') {
    $comment = $_GET['comment'];
    $query = "UPDATE replies
                SET
                flagged = 0
                WHERE ID = $comment";

    $result = $db->query($query) or die($db->error);
    header("Location: ../pages.php?page=viewReports");
    die("flag dismissed.");
  }
  else if($_GET['type'] == 'deleteClass') {
    if("" == trim($_POST['className'])) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "No class name";
      header("Location: ../pages.php?page=deleteClass");
      die("No name.");
    }
    else if("" == trim($_POST['id'])) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "No class id";
      header("Location: ../pages.php?page=deleteClass");
      die("No code.");
    }
    else if("" == trim($_POST['teachUsername'])) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "No username";
      header("Location: ../pages.php?page=deleteClass");
      die("No uname.");
    }
    else if("" == trim($_POST['password'])) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Please verify your password";
      header("Location: ../pages.php?page=deleteClass");
      die("No pass.");
    }
    $name = $_POST['className'];
    $id = $_POST['id'];
    $user = $_POST['teachUsername'];
    $password = $_POST['password'];
    $usernmae = $_SESSION['username'];
    //Lets make sure this is really an Administrator
    $query = "SELECT pass FROM users WHERE username = '$username' ";
    $result = $db->query($query) or die($db->error);
    $pass = $result->fetch_assoc();
    $passs = $pass["pass"];
    if(password_verify($password, $passs)) {
      //This is the admin

      //Get all topics so I can get all threads so I can get all Replies
      $query = "SELECT * FROM topics WHERE class_id = " . $id;
      $topics = $db->query($query) or die($db->error);
      //Get all threads
      while($topic = $topics->fetch_assoc()) {
        $tid = $topic['ID']
        //Get all threads
        $query = "SELECT ID FROM threads WHERE topic_id = $tid";
        $threads = $db->query($query) or die($db->error);
        //Delete all replies
        while($thread = $threads->fetch_assoc()) {
          $thid = $thread['ID'];
          $query = "DELETE FROM replies WHERE thread_id = $thid";
          $delete = $db->query($query) or die($db->error);
        }
        //All replies deleted, delete thread
        $query = "DELETE FROM threads WHERE ID = $tid";
        $delete = $db->query($query) or die($db->error);
      }
      //Delete all topics Now
      $query = "DELETE FROM topics WHERE class_id = $id";
      $result = $db->query($query) or die($db->error);

      //Now delete class
      $query = "DELETE FROM classes WHERE ID = $id";
      $result = $db->query($query) or die($db->error);

      //Remove TAs/students/teachers
      $query = "DELETE FROM userClasses WHERE class_id = $id";
      $result = $db->query($query) or die($db->error);

      header("Location: ../pages.php?page=deleteClass");
      die("goodbye my lover.");
    }


  }
?>
