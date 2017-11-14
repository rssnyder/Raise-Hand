<?php
include 'database.php';

  //Start a session
  session_start();

  //Connect to database
  $db = getDB();

  //Check and make sure they entered a username
  if("" == trim($_POST['username'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Username";
    header("Location: ../login.php");
    die("No username.");
  }
  //Check and make sure they entered a password
  else if("" == trim($_POST['password'])) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No Password";
    header("Location: ../login.php");
    die("No password.");
  }
  //Injection? Maybe, maybe not. Maybe screw you.
  else if (strpos($comment, ';')) {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "No thanks.";
    header("Location: ../login.php");
    die("Injection attempt");
  }
  //If both fields are populated correctly then execute login function
  else {
    //Get post variables and send to function
    $password = $_POST['password'];
    $username = $_POST['username'];
    signIn($username, $password, $db);
  }

  //Function to enter new user into database
  function signIn($username, $password, $db) {
    //Create sql command
    $insert = "SELECT * FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    //Get the data of the username they specified
    $pass = $result->fetch_assoc();
    //Get ID
    $id = $pass['ID'];
    //Get name
    $name = $pass['first_name'];
    //Check for admin/teacher privilages
    $role = $pass["role_id"];
    //Get hashed password from database
    $passs = $pass["pass"];
    //If the passwords match
    if(password_verify($password, $passs)) {
      if($pass['reset']) {
        $_SESSION['loggedin'] = true;
        $_SESSION['username'] = $username;
        $_SESSION['id'] = $id;
        $_SESSION['name'] = $name;
        $_SESSION['error']  = false;
        $_SESSION['role'] = $role;
        $_SESSION['email'] = $pass['email'];
        //User has a password Reset
        header("Location: ../resetPassword.php");
        die("Go reset the password");
      }
      //Set logged in variables
      echo "success";
      $_SESSION['loggedin'] = true;
      $_SESSION['username'] = $username;
      $_SESSION['id'] = $id;
      $_SESSION['name'] = $name;
      $_SESSION['error']  = false;
      $_SESSION['role'] = $role;
      $_SESSION['email'] = $pass['email'];
      if($role == 1) {
        header("Location: ../admin/home.php");
        die("Going to admin panel");
      }
      else if($role == 2) {
        header("Location: ../teacher/home.php");
        die("Going to admin panel");
      }
      else if($role == 4) {
        header("Location: ../student/home.php");
        die("Going to admin panel");
      }
      //Send user to their homepage
	    echo “success”;
      header("Location: ../login.php");



    }
    else {
        echo "fail";
      //Set session varibales to notify user
      $_SESSION['loggedin'] = false;
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Incorrect Password";
      //Send user back to login page if passwords didnt match
      header("Location: ../login.php");
    }
  }
?>
