<!-- This is the format we will use for the pages on the website. CSS to be added as
    time goes on and I continue to learn more. -->
<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/home.css">
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



  <!-- Main content of the webpage "do the wave" -->
<body>
  <div class="main">
    <br>
    <div class="container-fluid">
      <div class="row">
        <div class="col-md-6">
          <div class="home">
            <center>
              <button class="button" onclick="window.location='createClass.php';">Create a class</button>
              <p>
                Create a new class.
              </p>
            </center>
          </div>
        </div>
        <div class="col-md-6">
          <div class="home">
            <center>
              <button class="button" onclick="window.location='viewReports.php';">View reports</button>
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
              <button class="button" onclick="window.location='createUser.php';">Create a user</button>
              <p>
                Create a new user.
              </p>
            </center>
          </div>
        </div>
        <div class="col-md-6">
          <div class="home">
            <center>
              <button class="button" onclick="window.location='';">Do other stuff</button>
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
