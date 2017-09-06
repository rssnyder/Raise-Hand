<?php
  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');
  //Print out host information
  echo $db->host_info;

  //Function to print comments in a parent->child order
  function getComments($parentID, $level, $db) {
    $query = "SELECT * FROM posts WHERE parent=$parentID";
    $result = $db->query($query) or die('Error querying database.');
    //Get children and print comments
    while ($row = $result->fetch_assoc()) {
      //Indentation for comment hiarchy
      echo str_repeat('&nbsp;', 4*$level);
      //Print comment
      echo $row["text"] . '<font size="-2">by ' . $row["owner"] . '</font><br>';
      //Recursivly get child comments for any that exist
      getComments($row["id"], $level + 1, $db);
    }
  }
?>

<html>
  <body>
    <h1>Connected to SQL server.</h1>
    <?php
      getComments(100, 0, $db);
    ?>
  </body>
</html>
