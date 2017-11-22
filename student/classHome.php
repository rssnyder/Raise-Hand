<?php
  include '../utilities/database.php';

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

  //Get this class
  $class = getClass($db, $_GET['class']);

  //Check to see if student is actually in this class
  if(!doesBelong($db, $_GET['class'], $_SESSION['id'])) {
    //user is not in this class
    header("Location: home.php");
    die("You shall not pass");
  }

?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <?php
            echo '<h1>' . $class['class_name'] . '</h1>';
          ?>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <!-- The left sidebar -->
  <div class="left">
    <?php
      echo '<button class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button class="button" onclick="window.location=\'classHome.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <div align="center" class="container">
          <div id="questions" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">';
                <div class="row row-no-padding">
                  <div class="col-md-12">
                    <div class="jumbotron well">
                      <?php
                        $teacher = getUserInfo($db, $class['teacher_id']);
                        echo '<h1>' . $class['class_name'] . '</h1><h3>' . $class['description'] . '</h3>';
                      ?>
                    </div>
                  </div>
                </div>
                <div class="row row-no-padding">
                  <div class="col-md-12">
                    <div class="jumbotron well">
                      <?php
                          echo '<h4>Instructor:</h4> ' . $teacher['first_name'] . ' ' . $teacher['last_name'] . '<h6>Contact:</h6> ' . $teacher['email'];
                      ?>
                    </div>
                  </div>
                </div>
                <div class="row row-no-padding">
                  <div class="col-md-12">
                    <div class="jumbotron well">
                      <?php
                          echo '<h4>Teaching Assistants:</h4>';
                          $allTAs = getTAs($db, $_GET['class']);
                          foreach ($allTAs as $value) {
                            echo $value . '<br>';
                          }
                      ?>
                    </div>
                  </div>
                </div>
                <?php
                  //Time to get the recent threads
                  $threads = getRecentThreads($db, $_GET['class']);
                ?>
                <div class="row row-no-padding">
                  <div class="col-md-4">
                    <div class="jumbotron well">
                      <?php
                        //If we have at least 3 threads
                        if($thisT = $threads->fetch_assoc()) {
                          echo $thisT['description'];
                        }
                      ?>
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="jumbotron well">

                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="jumbotron well">

                    </div>
                  </div>
                </div>
            </div>
    </div>
  </div>

</html>
