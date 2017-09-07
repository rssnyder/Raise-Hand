<?php

  session_start();
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {

  } else {
    header("Location: login.php");
  }

  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  $password = "raisehand";
  $database = "topics";
  $db = new mysqli($host, $username, $password, $database, 3306) or die('Error connecting to server');
  //Print out host information
  //echo $db->host_info;

  //Function to print comments in a parent->child order
  function getComments($parentID, $level, $db) {
    $query = "SELECT * FROM posts WHERE parent=$parentID";
    $result = $db->query($query) or die('Error querying database.');
    //Get children and print comments
    while ($row = $result->fetch_assoc()) {
      //Indentation for comment hiarchy
      echo str_repeat('&nbsp;', 4*$level);
      //Print comment
      echo $row["text"] . '<font size="-2"> - ' . $row["owner"] . '</font><br>';
      //Recursivly get child comments for any that exist
      getComments($row["id"], $level + 1, $db);
    }
  }

?>

<!-- Scrtipt to hide comment form, or show it, based on button press -->
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
            clickedButton.value = 'Comment'
        }
  }}
</script>

<!-- Style for the hidden comment button -->
<style>
  .hidden {
      display:none;
  }

  .unhidden {
      display:block;
  }
  body {
    background-color: #ff5733;
  }
</style>

<html>
  <body>
    <h1>Welcome to the forums!<font size="-2">beta</font></h1>
    <?php
      getComments(1000, 0, $db);
      echo '<br><br>';
    ?>

    <!-- Hidden comment section -->
    <div id="about" class="hidden">
      <div class="content3">
        <form action="comment.php" method="post">
          <!--Username: <input type="text" name="username" value=""><br> -->
          <input type="text" name="comment" value=""><br>
          <input name="submit" type="submit" value="Submit">
        </form>
      </div>
    </div>
    <input type="button" onclick="unhide(this, 'about') " value="Comment">

  </body>
</html>
