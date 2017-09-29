<?php
/*
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {

  }
  else {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "You don't belong there";
    header("Location: login.php?event=logout");
  }
  */
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="css/home.css">
  </head>
  <body>
    <div align="center">
      <h1>Reset Password</h1>
    </div>
    <?php
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div>";
        $_SESSION['error'] = false;
    }
    ?>
      <div align="center" class="container">
        <form id="recover-form" action="utilities/resetPassword.php" method="post">
          Enter new password: <br>
          <input type="password" name="password1" value="" size="35"><br><br>
          Retype password: <br>
          <input type="password" name="password2" value="" size="35"><br><br>
          <input name="signup" type="submit" value="Change Password"><br><br>
        </form>
      </div>

  </body>
</html>
