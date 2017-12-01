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
    <link rel="stylesheet" href="utilities/teach.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.js"></script>
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
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'classHome.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'stats.php?class=' . $_GET['class'] . '\';">Class Stats</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
   ?>
  </div>

  <!-- Main content of the webpage -->
  <div class="main">
    <canvas id="myChart"></canvas>
    <script id="source" language="javascript" type="text/javascript">
      window.setInterval(function getData(){
        var $_GET=[];
        window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,function(a,name,value){$_GET[name]=value;});

        //Send reuest to php page for information
        $.ajax({
              type: "GET",
              url: 'utilities/stats.php',
              dataType: "json",
              data: {class: $_GET['class']},
              success: function(stats){
                //Use the returned json to build the labes and data array to build the graph
                var labels
                var theUsers = '"';
                var theData = '';
                var first = 1;
                jQuery.each(stats, function(user, value) {
                  if(first == 1) {
                    theUsers = theUsers + user + '"';
                    theData = theData + value;
                    first = 0;
                  }
                  else {
                    theUsers = theUsers + ', "' + user + '"';
                    theData = theData + ', ' + value;
                  }
                });
                var graphData = '{' +
                  '"type": "bar",' +
                  '"data": {' +
                      '"labels": [' +
                          theUsers +
                          '],' +
                      '"datasets": [{' +
                          '"label": "# of Comments",' +
                          '"data": [' +
                            theData +
                          '],' +
                          '"backgroundColor": [' +
                              '"rgba(255, 99, 132, 0.2)",' +
                              '"rgba(54, 162, 235, 0.2)",' +
                              '"rgba(255, 206, 86, 0.2)",' +
                              '"rgba(75, 192, 192, 0.2)",' +
                              '"rgba(153, 102, 255, 0.2)",' +
                              '"rgba(255, 159, 64, 0.2)"' +
                          '],' +
                          '"borderColor": [' +
                              '"rgba(255,99,132,1)",' +
                              '"rgba(54, 162, 235, 1)",' +
                              '"rgba(255, 206, 86, 1)",' +
                              '"rgba(75, 192, 192, 1)",' +
                              '"rgba(153, 102, 255, 1)",' +
                              '"rgba(255, 159, 64, 1)"' +
                          '],' +
                          '"borderWidth": "1"' +
                      '}]' +
                  '},' +
                  '"options": {' +
                      '"scales": {' +
                          '"yAxes": [{' +
                              '"ticks": {' +
                                  '"beginAtZero":"true"' +
                              '}' +
                          '}]' +
                      '}' +
                  '}' +
                '}';
                var realData = JSON.parse(graphData);
                var ctx = document.getElementById("myChart").getContext('2d');
                var myChart = new Chart(ctx, realData);
              },
              error: function(stats) {
                alert("Error.");
                console.log(stats);
              }
          });
      });
    </script>
    <script>

    </script>
  </div>
  </div>
</html>
