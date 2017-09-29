<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="css/home.css">
  </head>
  <body>
    <div align="center">
      <h1>Create a new account</h1>
    </div>
    <?php
    session_start();
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div><br><br>";
        $_SESSION['error'] = false;
    }
    ?>
      <div align="center" class="container">
        <form id="singup-form" action="utilities/signup.php" method="post">
          First Name: <br>
          <input type="text" name="first" value="" size="35"><br><br>
          Last Name: <br>
          <input type="text" name="last" value="" size="35"><br><br>
          Username: <br>
          <input type="text" name="username" value="" size="35"><br><br>
          Email: <br>
          <input type="text" name="email" value="" size="35"><br><br>
          Password: <br>
          <input type="password" name="password1" value="" size="35"><br><br>
          Retype Password: <br>
          <input type="password" name="password2" value="" size="35"><br><br>
          <input name="signup" type="submit" value="Create Account"><br><br>
        </form>
      </div>

  </body>
</html>
