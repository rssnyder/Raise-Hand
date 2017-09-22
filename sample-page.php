<!-- This is the format we will use for the pages on the website. CSS to be added as
    time goes on and I continue to learn more. -->
<html>
  <head>
    <link rel="stylesheet" href="css/pages.css">
  </head>

  <!-- The top banner of the webpage -->
  <div class="top">
      <font size="-5"><a class="logout" href="login.php?event=logout">Logout</a></font>
      <center>
        <h1>Welcome, User</h1>
      </center>
  </div>

  <!-- The left sidebar -->
  <div class="left">
    <button class="button" onclick="window.location='home.php';">Home</button>
    <button class="button" onclick="window.location='notifications.php';">Notifications</button>
    <button class="button" onclick="window.location='';">Class A</button>
    <button class="button" onclick="window.location='';">Class B</button>
    <button class="button" onclick="window.location='';">Class C</button>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
      This is where content will go.
  </div>
</html>
