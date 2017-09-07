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
      getComments(1000, 0, $db);
      echo '<br><br>';
    ?>
    <!--Comment form-->
    <form action="comment.php" method="post">
      <input type="text" name="username" value=""><br>
      <input type="text" name="comment" value=""><br>
      <input name="submit" type="submit" value="Submit">
    </form>
    <!-- AJAX call for comment placement
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type = "text/javascript">
      $(function() {
        $('form').on('submit', function(e) {
          e.preventDefault();
          $.ajax({
            type: 'post',
            url: 'comment.php',
            data: $('form').serialize(),
            success: function() {
              alert('Comment was posted!');
            }
            error: function(xhr) {
              alert("fuck me");
            }
          })
        })
      }
    })
  </script> -->
  </body>
</html>
