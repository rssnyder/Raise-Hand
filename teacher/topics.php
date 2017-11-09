<?php
include '../utilities/database.php';

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

  //Get the db Referance
  $db = getDB();

  //Get this class
  $class = getClass($db, $_GET['class']);

  //Check to see if this teacher actaully owns this classes
  if($class['teacher_id'] != $_SESSION['id']) {
    header("Location: home.php");
    die("oops");
  }

?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

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
      echo '<br><br>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '&page=createTopic\';">New Topic</button>';
      echo '<br><br>';
      echo '<button class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button class="button" onclick="window.location=\'classHome.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
      <?php
        //If teacher wants to create a new class
        if($_GET['page'] == 'createTopic') {
          echo '<div align="center" class="container">';
          echo '<form id="newTopic-form" action="utilities/createTopic.php?class=' . $_GET['class'] . '" method="post">';
          echo '<br><br>Create a new topic<br><br>';
          if($_SESSION['error']){
              echo '<font color="red">' . $_SESSION['errorCode'] . "</font><br><br>";
              $_SESSION['error'] = false;
          }
          echo 'Topic Name: <br>
            <input type="text" name="name" value="" size="35"><br><br>
            Topic Description: <br>
            <input type="text" name="description" value="" size="35"><br><br>
            <input name="newTopic" type="submit" value="Create Topic"><br><br>
          </form>';
        }
        //Otherwise print the topics
        else {
          echo '<div id="threads" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">';
          //Get the topics
          $result = getTopics($db, $_GET['class']);
          //Get topics
          while ($topic = $result->fetch_assoc()) {
            echo '<div class="row">
  		              <div class="col-md-6">
                      <div class="jumbotron well">';

            //Make the topic itself a button
            echo '<a href="threads.php?class=' . $_GET['class'] . '&topic=' . $topic["ID"] . '" class="topic">';

            //Print the topic
            echo '<h2>' . $topic["topic_name"] . '</h2></a><br><p> - ' . $topic["description"] . '</p></div></div>';
            if($topic = $result->fetch_assoc()) {
              echo '<div class="col-md-6">
                      <div class="jumbotron well">';

              //Make the topic itself a button
              echo '<a href="threads.php?class=' . $_GET['class'] . '&topic=' . $topic["ID"] . '" class="topic">';

              //Print the topic
              echo '<h2>' . $topic["topic_name"] . '</h2></a><br><p> - ' . $topic["description"] . '</p></div></div></div>';
            }
            else {
              echo '</div>';
            }
        }
        echo '</div>';
      }
      ?>
    </div>
  </div>
</html>
