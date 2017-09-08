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

      //Get the values for the comment
      $owner = $row["owner"];
      $text = $row["text"];
      $id = $row["id"];

      //Make the comment a button
      //htmlspecialchars(<button class="comment" onclick="unhide(this, comment) ">)
      echo('<button class="comment" onclick="unhide(this,\'comment' . $id . '\')">');

      if(!strcmp($owner, 'rssnyder')) {
        echo '<font color=yellow>' . $text . '</font>' . '<font size="-2"> - ' . $row["owner"] . '</font>';
      }
      else {
        echo $text . '<font size="-2"> - ' . $row["owner"] . '</font>';
      }
      echo '</button><br>';

      //Create a button and text field to create a child comment. Put the parents ID in so we can connect the two
      echo '<div id="comment' . $id . '" class="hidden">
            <div class="content3">
              <form action="comment.php" method="post">
                <input type="text" name="comment" value=""><br>
                <input onclick="parrentID("' . $id . '")" name="submit" type="submit" value="Submit">
              </form>
            </div>
            </div>';

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

  function parentID(id) {
    <?php
      $_SESSION['parentID'] = id;
    ?>
  }
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
  button.comment {
    background:none;
    border:none;
  }

</style>

<html>
  <head>
    <link rel="stylesheet" type="text/css" href="mystyle.css">
  </head>
  <body>
    <h1>Welcome to the *shitty forums!<font size="-2">beta v0.1.26</font></h1>
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

    <button class="comment">Click here</button>

  </body>

<!--
  <div id="container">
    <div id="footer">
      <font size="-2">
       Copyright Riley Snyder Industries<br>
       Contact information: <a href="mailto:rileysndr@gmail.com">
       rileysndr@gmail.com</a></text>
    </div>
  </div>
-->
</html>
