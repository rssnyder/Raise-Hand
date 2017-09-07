<html>
<body>
<?php
  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');
  //Print out host information
  //echo $db->host_info;
  //Make sure everything is there and then sign them up
  if("" == trim($_POST['username'])) {
    die("No username");
    header("Location: login.php");
  }
  else if("" == trim($_POST['password'])) {
    die("No password");
    header("Location: login.php");
  }
  else {
    $password = $_POST['password'];
    $username = $_POST['username'];
    signIn($username, $password, $db);
  }

  //Function to enter new user into database
  function signIn($username, $password, $db) {
    //Create sql command
    $insert = "SELECT password FROM users WHERE username = '$username' ";
    //Excecute
    $result = $db->query($insert) or die($db->error);
    $pass = $result->fetch_assoc();
    $pass = $pass["password"];
    if(password_verify($password, $pass)) {
      echo "made it";
      header("Location: forum.php");
    }
    else {
      echo "Failed";

      header("Location: index.php");
    }
  }
?>
</body>
</html>
