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

  //Move to session CREATE
  function startSession($class){
    header("Location: utilities/startLive.php?class=$class");
    die("Go create session");
  }

  //Check to see if live session is currently in session
  $check = "SELECT 1 FROM liveQueue" . $_GET['class'] . " LIMIT 1";
  $result = $db->query($check) or startSession($_GET['class']);

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
    <script>
      var feed = 0;

      //Script to get question

      function createQ() {
        var div = document.createElement("div");
        div.innerHTML = "hey there." + counter;
        counter++;
        //Set this question to where it belongs
        document.getElementById('test').appendChild(div);
        document.getElementById('test').innerHTML = "No.";
      }

      </script>
      <script id="source" language="javascript" type="text/javascript">
         window.setInterval(function getData(){
           //Get the GET variables
           var $_GET=[];
           window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,function(a,name,value){$_GET[name]=value;});
           //Send reuest to php page for information
           $.ajax({
                 type: "GET",
                 url: 'utilities/liveSession.php',
                 dataType: "json",
                 data: {class: $_GET['class']},
                 success: function(data){
                   var counter = feed;
                   while(data[counter]) {
                     var div = document.createElement("div");
                     div.setAttribute('class', 'row');
                     div.setAttribute('id', data[counter]);
                     feed = data[counter];
                     //start
                     var colDiv = document.createElement("div");
                     colDiv.setAttribute('class', 'col-md-12');
                     var jumDiv = document.createElement("div");
                     jumDiv.setAttribute('class', 'jumbotron well');
                     jumDiv.setAttribute('id', data[counter]);
                     jumDiv.innerHTML = data[counter + 1];
                     //colDiv.appendChild(jumDiv);
                     //div.appendChild(colDiv);

                     //div.innerHTML = '<div class=\"col-md-12\"><div class=\"jumbotron well\">' + data[counter + 1] + ': ' + data[counter + 2] + '</div></div>';
                     counter += 2;
                     feed = counter;
                     document.getElementById('questions').appendChild(jumDiv);
                   }
                 },
                 error: function() {
                   alert("Error.");
                 }
             });

             //Sorts the questions, newest ones first
             $("#questions div").sort(function(a, b) {
                return parseInt(b.id) - parseInt(a.id);
              }).each(function() {
                var elem = $(this);
                elem.remove();
                $(elem).appendTo("#questions");
              });
           }, 100);
       </script>
       <script id="feedSav" language="javascript" type="text/javascript">
         function saveFeed() {
            var textFile = null,
             makeTextFile = function (text) {
               var data = new Blob([text], {type: 'text/plain'});

               // If we are replacing a previously generated file we need to
               // manually revoke the object URL to avoid memory leaks.
               if (textFile !== null) {
                 window.URL.revokeObjectURL(textFile);
               }

               textFile = window.URL.createObjectURL(data);

               return textFile;
             };


             var create = document.getElementById('create'),
               textbox = document.getElementById('textbox');

             create.addEventListener('click', function () {
               //Get all the comment Divs
               var x = document.querySelectorAll(".jumbotron well");
               x[0].innerHTML = "Hey we can change this.";
               var link = document.getElementById('downloadlink');
               link.href = makeTextFile(textbox.value);
               link.style.display = 'block';
             }, false);
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
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'classHome.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'stats.php?class=' . $_GET['class'] . '\';">Class Stats</button>';
      echo '<button id="color' . ($class['ID'] % 6) . '" class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '&page=classSettings\';">Class Settings</button>';
   ?>
  </div>

  <!-- Main content of the webpage -->
  <div id="main" class="main">
    <div id="commentBox" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
       <?php echo '<form action="utilities/endLive.php?class=' . $_GET['class'] . '" method="post">'; ?>
         <input name="end" type="submit" value="End Live Session">
       </form>
    </div>
    <div id="questions" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
   </div>
  </div>
</html>
