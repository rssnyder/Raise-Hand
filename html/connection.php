<?php
  $host = "localhost";
  $username = "root";
  $password = "raisehand";
  $cdname = "topics";
  //echo "About to try...<br>";
  $mysqlconn = new mysqli("localhost", "root", "raisehand") or die('Error connecting to server');
?>

<html>
  <body>
    <p>
    "We are connected"
  </p>
  </body>
</html>
