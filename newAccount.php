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
      <h1>Create a new account</h1>
    </div>
    <?php
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div>";
    }
    ?>
      <div align="center" class="container">
        <form id="singup-form" action="signup.php" method="post">
          First Name: <br>
          <input type="text" name="first" value="" size="35"><br><br>
          Last Name: <br>
          <input type="text" name="last" value="" size="35"><br><br>
          Username: <br>
          <input type="text" name="username" value="" size="35"><br><br>
          Password: <br>
          <input type="password" name="password" value="" size="35"><br><br>
          <input name="signup" type="submit" value="Create Account"><br><br>
        </form>
      </div>

  </body>
</html>
