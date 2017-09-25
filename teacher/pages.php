<?php
  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 2) {
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
?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <h1>Class Settings</h1>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <div class="left">
    <?php
      echo '<button class="button" onclick="window.location=\'home.php\';">Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <div align="center" class="container">
      <?php
        if($_GET['page'] == 'classSettings') {
          echo '<form id="singup-form" action="updateClass.php?class=' . $_GET['class'] . '" method="post">';
          if($_SESSION['error']){
              echo '<font color="red">' . $_SESSION['errorCode'] . "</font><br><br>";
              $_SESSION['error'] = false;
          }
          echo 'Class Name: <br>
            <input type="text" name="name" value="" size="35"><br><br>
            Start Date "YYYY-MM-DD": <br>
            <input type="text" name="startDate" value="" size="35"><br><br>
            End Date "YYYY-MM-DD": <br>
            <input type="text" name="endDate" value="" size="35"><br><br>
            Class Start Time "HH:MM:SS": <br>
            <input type="text" name="startTime" value="" size="35"><br><br>
            Class End Time "HH:MM:SS": <br>
            <input type="text" name="endTime" value="" size="35"><br><br>
            Meetings Per Week: <br>
            <input type="text" name="meetingsPerWeek" value="" size="35"><br><br>
            <input name="signup" type="submit" value="Update Class"><br><br>
          </form>';
        }
        else if($_GET['page'] == 'viewReports') {

        }
      ?>
    </div>
  </div>

</html>
