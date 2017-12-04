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
  //Make sure everything is there and then create the class

  if("" == trim($_POST['name'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Class Name Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['teacher'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Instructor Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['startDate'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Start Date Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['endDate'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "End Date Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['startTime'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Start Time Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['endTime'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "End Time Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['meetingsPerWeek'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Meetings Per Week Required";
    header("Location: ../pages.php?page=createClass");
  }
  else if("" == trim($_POST['universityID'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "University ID Required";
    header("Location: ../pages.php?page=createClass");
  }
  else {
    //Get teacher id
    $teacherID = $_POST['teacher'];
    $teacherQ = "SELECT ID FROM users WHERE username = '$teacherID' ";
    $result = $db->query($teacherQ) or die($db->error);
    //Get the data of the username they specified
    $pass = $result->fetch_assoc();
    //Check for admin/teacher privilages
    $teacherID = $pass["ID"];
    //Make user teacher
    $makeTeacher = "UPDATE users SET role_id = 2 WHERE ID = '$teacherID'";
    $result = $db->query($makeTeacher) or die($db->error);

    $name = $_POST['name'];
    $startDate = $_POST['startDate'];
    $endDate = $_POST['endDate'];
    $startTime = $_POST['startTime'];
    $endTime = $_POST['endTime'];
    $meetingsPerWeek = $_POST['meetingsPerWeek'];
    $universityID = $_POST['universityID'];
    $accessCode = rand();
    $create = "INSERT INTO classes
      (
      teacher_id,
      class_name,
      class_start,
      class_end,
      class_time_start,
      class_time_end,
      times_met_per_week,
      access_code,
      university_id,
      description)
      VALUES
      (
      $teacherID,
      '$name',
      '$startDate',
      '$endDate',
      '$startTime',
      '$endTime',
      $meetingsPerWeek,
      $accessCode,
      $universityID,
      'A class')";
    $result = $db->query($create) or die($db->error);

    //Get class
    $query = "SELECT * FROM classes WHERE access_code = $accessCode";
    $result = $db->query($query) or die($db->error);
    $thisClass = $result->fetch_assoc();
    //Add into teacher table
    $addClass = "INSERT INTO userClasses
                  (relationship,
                  user_id,
                  class_id)
                  VALUES
                  (2,
                  " . $teacherID . ",
                  " . $thisClass['ID'] . ");
                  ";

    $result = $db->query($addClass) or die($db->error);
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Class Created";
    header("Location: ../pages.php?page=createClass");
    die("done");
  }
?>
