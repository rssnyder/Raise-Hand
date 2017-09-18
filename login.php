<?php
  //Clear session
  session_start();
  //Check for error from previous sign in
  if($_SESSION['error']){
    //Do nothing
  }
  //If user already logged in then take to their home
  else if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    header("Location: home.php");
  //Otherwise, clear this session and send them to the login
  }
  else {
    session_unset();
	}
?>
<html>
  <head>
    <link rel="stylesheet" href="styles.css">
  </head>
  <body>
    <div align="center">
      <h1>Raise Hand</h1>
    </div>
    <?php
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div>";
    }
    ?>
      <div align="center" class="container">
        <form id="login-form" action="signin.php" method="post">
          Username: <br>
          <input type="text" name="username" value="" size="35"><br>
          Password: <br>
          <input type="password" name="password" value="" size="35"><br><br>
          <input name="signin" type="submit" value="Login">
        </form>
      </div>
  </body>
</html>
