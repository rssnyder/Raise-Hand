<html>
  <body>
    <p>
    <?php
      $host = "localhost";
      $username = "root";
      $password = "raisehand";
      $cdname = "topics";
      echo "About to try...<br>";
      $mysqlconn = new mysqli(host, username, password);
      if($mysqlconn->connect_error) {
        echo "Awe damn, not good.";
      }
      else {
        echo "Hey, we out here!";
      }
    ?>
  </p>
  </body>
</html>
