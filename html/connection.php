<?php
  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $cdname = "topics";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');

  echo $db->host_info;
?>

<html>
  <body>
    <h1>
    Connected to SQL server.
  </h1>
  <?php
    $table = 'threads';
    $value = 'text';
    $query = "SELECT * FROM $table";
    $result = $db->query($query) or die('Error querying database.');
    while ($row = $result->fetch_assoc()) {
      echo $row["id"] . $row["$value"] .'<br>';
  }
  ?>
  </body>
</html>
