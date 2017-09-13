<?php

  session_start();
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_GET['thread'] === null) {
      header("Location: index.php?thread=General");
      die("oops");
    }
    $_SESSION['thread'] = $_GET['thread'];
  } else {
    header("Location: login.php");
  }
  if($_GET['thread'] === null) {
    header("Location: index.php?thread=General");
    die("oops");
  }


  //Define paramiters and initalize connection
  $host = "127.0.0.1";
  $username = "root";
  //Get SQL password from config failed
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
      echo str_repeat('-', 8*$level);

      //Get the values for the comment
      $owner = $row["owner"];
      $text = $row["text"];
      $id = $row["id"];

      //Make the comment itself a button
      echo('<button class="comment" onclick="unhide(this,\'childComment' . $id . '\')">');

      //Print the comment
      if(!strcmp($owner, 'rssnyder')) {
        echo '<font size= "5" color=yellow>' . $text . '</font><font size="-2"> - ' . $row["owner"] . '</font><br>';
      }
      else {
        echo '<font size="5">' . $text . '</font><font size="-2"> - ' . $row["owner"] . '</font><br>';
      }

      echo '</button><br>';

      //This is for Davien. Lord bless her Soul
      $username = $_SESSION['username'];
      //This is your comment
      if((!strcmp($owner, $username)) || ($_SESSION['admin'] == 1)) {
        echo '<div id="childComment' . $id . '" class="hidden">
              <div class="content3">
                <form action="comment.php" method="post">
                  <input type="hidden" name="commentID" value="' . $id . '"/>
                  <input name="delete" type="submit" value="Delete" />
                </form>
                <form action="comment.php" method="post">
                  <input name="submit" type="submit" value="Comment" /><br>
                  <input type="hidden" name="parentID" value="' . $id . '"/>
                  <input type="text" size="30" name="comment" value="" /><br>
                </form>
              </div>
            </div>';
      }

      //Create the dropdown text box that appears when the comment is clicked
      echo '<div id="childComment' . $id . '" class="hidden">
            <div class="content3">
              <form action="comment.php" method="post">
                <input type="hidden" name="parentID" value="' . $id . '"/>
                <input type="text" size="30" name="comment" value="" /><br>
                <input name="submit" type="submit" value="Submit" /><br>
              </form>
            </div>
          </div>';

      //Recursivly get child comments for any that exist
      getComments($row["id"], $level + 1, $db);
    }
  }

  function getThread($thread, $db) {
    $query = "SELECT * FROM threads WHERE title = '$thread'";
    $result = $db->query($query) or die('Error querying database.');
    //Get children and print comments
    while ($row = $result->fetch_assoc()) {
      echo '<h1>' . $row['title'] . '<br>';
      echo '<font size="-2">' . $row['description'] . '</font></h1>';
      return $row['id'];
    }
  }

  function getThreads($thread, $db) {
    $query = "SELECT * FROM threads WHERE title != '$thread'";
    $result = $db->query($query) or die('Error querying database.');
    //Get children and print comments
    echo '<h5>';
    while ($row = $result->fetch_assoc()) {
      $thisThread = $row['title'];
      echo '<a href="index.php?thread=' . $thisThread . '">' . $thisThread . '</a>';
      echo str_repeat('&nbsp;', 10);
    }
    echo '</h5>';
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
    <?php
      if($_SESSION['message'] !== false) {
        echo '<h8>' . $_SESSION['message'] . '</h8>';
        $_SESSION['message'] = false;
      }
      //$thread = $_GET['thread'];
      $thread = $_SESSION['thread'];
      $id = getThread($thread, $db);
      getThreads($thread, $db);
      getComments($id, 0, $db);
      echo '<br><br>';
      //Submit top level comment  -->

      echo '<form action="comment.php" method="post">
              <input type="hidden" name="parentID" value="' . $id . '"/>
              <input type="text" size="30" name="comment" value="" /><br>
              <input name="submit" type="submit" value="Submit" />
            </form>';
    ?>


  </body>
</html>
