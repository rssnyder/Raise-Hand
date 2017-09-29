<!DOCTYPE html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="css/home.css">
  </head>
  <body>
    <div align="center">
      <h1>Recover Password</h1>
    </div>
    <?php
    if($_SESSION['error']){
        echo "<br><br><br><div align=\"center\">Error: " . $_SESSION['errorCode'] . "</div>";
        $_SESSION['error'] = false;
    }
    ?>
      <div align="center" class="container">
        <form id="recover-form" action="utilities/startPasswordReset.php" method="post">
          Email: <br>
          <input type="text" name="email" value="" size="35"><br><br>
          <input name="signup" type="submit" value="Reset Password"><br><br>
        </form>
      </div>

  </body>
</html>
