<?php
  session_start();
?>

<style>
  .hidden {
      display:none;
  }

  .unhidden {
      display:block;
  }
  body {
    background-color: #FFC300;
  }
</style>

<!-- Scrtipt to hide signup form, or show it, based on button press -->
<script type="text/javascript">
  function unhide(clickedButton, divID) {
    var item = document.getElementById(divID);
    if (item) {
        if(item.className=='hidden') {
            item.className = 'unhidden' ;
            clickedButton.value = 'Cancel'
        }
        else {
            item.className = 'hidden';
            clickedButton.value = 'Sign Up'
        }
  }}
</script>

<html>
  <body>
    <center>
      <h1>Welcome to the *shitty forums!<font size="-2">beta v0.1.32</font></h1>
      <br><br>
      <h6>Sign in below</h6>
      <br><br>
      <?php
        if($_SESSION['error']) {
          echo $_SESSION['errorCode'];
          $_SESSION['error'] = false;
        }
      ?>
      <br><br><br><br><br>

        <form action="signin.php" method="post">
          Username: <input type="text" name="username" value=""><br>
          Password:  <input type="password" name="password" value=""><br>
          <input name="signin" type="submit" value="Login">
        </form>

        <!-- Signup Form -->
        <div id="signup" class="hidden">
          <div class="content3">
            <form action="signup.php" method="post">
              <br>
              Username: <input type="text" name="username" value=""><br>
              Email: <input type="text" name="email" value=""><br>
              Password:  <input type="password" name="password" value=""><br>
              <font size="-2">Passwords are stored as a salted hash</font><br>
              <input name="signupp" type="submit" value="Sign Up">
            </form>
          </div>
        </div>
        <input type="button" onclick="unhide(this, 'signup') " value="Sign Up">
    </center>

  </body>
</html>
