<?php
  //Clear session
  session_start();
  //Check for error from previous sign in
  if($_SESSION['error']){
    //Do nothing
  }
  else {
    session_unset();
	}
?>

<html>
  <head>
    <link rel="stylesheet" href="css/home.css">
  </head>
  <body>
    <div id="title" align="center">
      <h1 id="title">Raise Hand</h1>
    </div>
    <?php
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div>";
        $_SESSION['error'] = false;
    }
    ?>
      <div align="center" class="container">
        <form id="login-form" action="signin.php" method="post">
          Username: <br>
          <input type="text" name="username" value="" size="35"><br>
          Password: <br>
          <input type="password" name="password" value="" size="35"><br><br>
          <input name="signin" type="submit" value="Login"><br><br>
          <a class="logout" href="newAccount.php">Don't have an account? Sign up here!</a>
        </form>
      </div>

  </body>
</html>
