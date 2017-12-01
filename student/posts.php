<?php
  include '../utilities/database.php';

  session_start();
  //Check if user is logged in
  if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
    if(($_SESSION['role'] != 4) && $_SESSION['role'] != 3) {
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

  //Get the db Referance
  $db = getDB();

  //Get this class
  $class = getClass($db, $_GET['class']);

  //Check to see if student is actually in this class
  if(!doesBelong($db, $_GET['class'], $_SESSION['id'])) {
    //user is not in this class
    header("Location: home.php");
    die("You shall not pass");
  }

  //Get the relationship of the student to this class for TA endorsement
  $relationship = getRelationship($db, $_SESSION['id'], $class['ID']);

  function getChildComments($parentID, $lvl, $threadID, $db, $relationship) {
    //Get the children of this comment
    $result = getChildComm($db, $parentID, $threadID);
    //If we have a child comments
    while($comment = $result->fetch_assoc()) {
      //Indent the line the specifed level
      $id = $comment['ID'];
      $text = $comment['txt'];
      $author = $comment['user_name'];
      $endorsed = $comment['endorsed'];
      $flagged = $comment['flagged'];
      $creation = $comment['creation'];
      $points = $comment['points'];

      //If the comment is remove, we still want to post something there so that you can see child comments.
      if($text == 'REMOVED') {
        echo '<div class="row">
                <div class="col-md-' . $lvl . '"></div>
                <div class="col-md-' . (12 - $lvl) . '">
                  <div class="jumbotron well">';
        echo '<p>' . $text . '</p>
              </div></div></div>';
      }
      else {
        //Print the comment
        echo '<div class="row">
                <div class="col-md-' . $lvl . '"></div>
                <div class="col-md-' . (12 - $lvl) . '">
                  <div class="jumbotron well">';
        echo '<p>' . $text . '</p>  -' . $author . ' @ ' . $creation . '<br><br>';
        //Print the buttons
        echo '<button id="like' . $id . '" type="button" class="btn btn-default btn-sm" onclick="likeC(\'like' . $id . '\','. $points .')">
                <span class="glyphicon glyphicon-thumbs-up"></span>' . $points . ' Likes
              </button><button class="commentButton" onclick="unhide(this,\'childComment' . $id . '\')">Reply</button>';
        //TAs can endorse thing
        if(($relationship ==  3) && (!$endorsed)) {
          echo '<a href="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $threadID . '&comment=' . $id . '&action=endorse" class="commentButton">Endorse</a>';
        }
        //Print endorsement
        if($endorsed) {
          echo '<text class="threed">Endorsed Answer!</text>';
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
      }
      //Print any child comments of this comment
      getChildComments($id, $lvl + 1, $threadID, $db, $relationship);
    }

  }
?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

    <script type="text/javascript">
      //Vote Button
      $('.vote').click(function () {
        $(this).toggleClass('on');
      });
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
      <script language="javascript" type="text/javascript">
         function likeC(id, points){
           //Send reuest to php page for information
           $.ajax({
                 type: "GET",
                 url: 'utilities/upvote.php',
                 dataType: "json",
                 data: {comment: id, points: points},
                 success: function(data){
                   var div = document.getElementById(id);
                   div.innerHTML = '<span class="glyphicon glyphicon-thumbs-up"></span> ' + (points + 1) + ' Likes';
                 },
                 error: function() {
                   alert("There was an error with voting. Please try again later.");
                 }
             });
           };
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
      echo '<button class="button" onclick="window.location=\'classHome.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <div id="threads" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
      <?php
        //Get the current thread
        $mainThread = getThread($db, $_GET['thread']);
        //Print the thread title
        echo '<div class="row row-no-padding">
                <div class="col-md-12">
                  <div class="jumbotron well">';
        echo '<h5>' . $mainThread['title'] . '</h5><br>' . $mainThread['description'] . '<br><br>';
        echo '<button class="commentButton" onclick="unhide(this,\'childComment0\')">Reply</button><a href="utilities/comment.php?class=' . $_GET['class'] . '&thread=' . $_GET['thread'] . '&action=flagThread" class="commentButton">Flag</a>';
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
        getChildComments(0, 1, $_GET['thread'], $db, $relationship);
      ?>
    </div>
  </div>
</html>
