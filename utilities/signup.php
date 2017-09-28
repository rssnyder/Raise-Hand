<?php
  session_start();
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
  //Make sure everything is there and then sign them up
  if("" == trim($_POST['first'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "First Name Required";
    header("Location: ../newAccount.php");
  }
  else if("" == trim($_POST['last'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Last Name Required";
    header("Location: ../newAccount.php");
  }
  else if("" == trim($_POST['username'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Username Required";
    header("Location: ../newAccount.php");
  }
  else if("" == trim($_POST['email'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Email Required";
    header("Location: ../newAccount.php");
  }
  else if("" == trim($_POST['password1'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Password Required";
    header("Location: ../newAccount.php");
  }
  else if($_POST['password1'] != $_POST['password2']) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Passwords Do Not Match";
    header("Location: ../newAccount.php");
  }
  else {
    $username = $_POST['username'];
    //Check for existing username
    //TODO this isnt for our database, just general sql
    $ucheck = "SELECT username FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($ucheck) or die($db->error);
    $uname = $result->fetch_assoc();
    $uname = $uname['username'];
    if($uname) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Username Taken";
      header("Location: ../newAccount.php");
      die("User exists");
    }
    $password = $_POST['password1'];
    $password = password_hash($password, PASSWORD_DEFAULT);
    signUp($username, $_POST['first'], $_POST['last'], $_POST['email'], $password, $db);
  }

  //Function to enter new user into database
  function signUp($username, $first, $last, $email, $password, $db) {
    //Create sql command
    $insert = "INSERT INTO users(role_id, first_name, last_name, pass, username, email) VALUES (4, '$first', '$last', '$password', '$username', '$email')";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    //We need his ID so we should query his name
    //Create sql command
    $insert = "SELECT ID FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    //Get the data of the username they specified
    $id = $result->fetch_assoc();

    $_SESSION['loggedin'] = true;
    $_SESSION['username'] = $username;
    $_SESSION['id'] = $id['id'];
    $_SESSION['name'] = $first;
    $_SESSION['error']  = false;
    $_SESSION['role'] = 4;
    header("Location: ../student/home.php");
    die("done");
  }
?>
