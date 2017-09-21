<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="login.php?event=logout">Logout</a></font>
        <center>
          <h1>Welcome, User</h1>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <div class="left">
    <button class="button" onclick="window.location='home.php';">Home</button>
    <button class="button" onclick="window.location='createClass.php';">Create class</button>
    <button class="button" onclick="window.location='viewReports.php';">View reports</button>
    <button class="button" onclick="window.location='createUser.php';">Create user</button>
    <button class="button" onclick="window.location='other.php';">Other</button>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
      This is where content will go.
  </div>

</html>
