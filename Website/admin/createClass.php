<?php
  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 1) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Not Permitted";
      header("Location: ../login.php");
      die("oops");
    }
  } else {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Session Expired";
    header("Location: ../login.php");
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
  //Make sure everything is there and then create the class

  if("" == trim($_POST['name'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Class Name Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['teacher'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Instructor Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['startDate'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Start Date Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['endDate'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "End Date Required";
    header("Location: login.php");
  }
  else if("" == trim($_POST['startTime'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Start Time Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['endTime'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "End Time Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['meetingsPerWeek'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Meetings Per Week Required";
    header("Location: pages.php?page=createUser");
  }
  else if("" == trim($_POST['universityID'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "University ID Required";
    header("Location: pages.php?page=createUser");
  }
  else {
    $create = "CREATE TABLE 'classes' (
    'ID' int(11) NOT NULL AUTO_INCREMENT,
    'teacher_id' int(11) NOT NULL,
    'class_name' varchar(30) DEFAULT NULL,
    'class_start' date DEFAULT NULL,
    'class_end' date DEFAULT NULL,
    'class_time_start' time DEFAULT NULL,
    'class_time_end' time DEFAULT NULL,
    'times_met_per_week' int(11) DEFAULT NULL,
    'access_code' int(11) DEFAULT NULL,
    'university_id' int(11) DEFAULT NULL,
    PRIMARY KEY ('ID'),
    KEY 'teacher_id' ('teacher_id'),
    KEY 'fk_class_universityID' ('university_id'),
    CONSTRAINT 'classes_ibfk_1' FOREIGN KEY ('teacher_id') REFERENCES 'users' ('ID'),
    CONSTRAINT 'fk_class_universityID' FOREIGN KEY ('university_id') REFERENCES 'universities' ('ID')
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1";

    $result = $db->query($create) or die($db->error);
    header("Location: pages.php?page=createClass");
    die("done");
  }
?>
