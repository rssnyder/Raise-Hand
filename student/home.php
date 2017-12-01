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
    <link rel="stylesheet" href="../css/studentHome.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <?php
            echo "<font size='+24' class='neon'>Welcome " . $_SESSION['name'] . "</font>";
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
        $classes = getClasses($db, $_SESSION['id']);
        while ($class = $classes->fetch_assoc()) {
          $thisClass = getClass($db, $class['class_id']);
          echo '
                  <div class="col-md-6">
                    <div class="home">
                      <center>
                        <br>
                        <button id="' . $thisClass['ID'] . '" class="button" onclick="window.location=\'classHome.php?class=' . $thisClass['ID'] . '\';">' . $thisClass['class_name'] . '</button>
                        <p>
                          ' . $thisClass['description'] . '
                        </p>
                      </center>
                    </div>
                  </div>
                </div>
                <br>';
          if($class = $classes->fetch_assoc()) {
            $thisClass = getClass($db, $class['class_id']);
            echo '<div class="row">
                    <div class="col-md-6">
                      <div class="home">
                        <center>
                          <br>
                          <button id="' . $thisClass['ID'] . '" class="button" onclick="window.location=\'classHome.php?class=' . $thisClass['ID'] . '\';">' . $thisClass['class_name'] . '</button>
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
