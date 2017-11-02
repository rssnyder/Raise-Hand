<?php
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


  //See if session is avalible
  function startSession($class){
    header("Location: pages.php?class=$class");
    die("Go create session");
  }
  //Check to see if live session is currently in session
  $check = "SELECT 1 FROM liveQueue" . $_GET['class'] . " LIMIT 1";
  $result = $db->query($check) or startSession($_GET['class']);



  //Check to see if student is actually in this class
  $belongs = false;
  $query = "SELECT class_id FROM userClasses WHERE user_id = " . $_SESSION['id'];
  $result = $db->query($query) or die($db->error);
  while ($oclass = $result->fetch_assoc()) {
    if($_GET['class'] == $oclass['class_id']) {
      //user is in this class
      $belongs = true;
      break;
    }
  }
  if(!$belongs) {
    //user is not in this class
    header("Location: home.php");
    die("You shall not pass");
  }

?>

<html lang="en">
  <head>
    <link rel="stylesheet" href="../css/pages.css">
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
                     jumDiv.innerHTML = data[counter + 2] + '       - ' + data[counter + 1];
                     //colDiv.appendChild(jumDiv);
                     //div.appendChild(colDiv);

                     //div.innerHTML = '<div class=\"col-md-12\"><div class=\"jumbotron well\">' + data[counter + 1] + ': ' + data[counter + 2] + '</div></div>';
                     counter += 3;
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
           }, 1000);
       </script>
       <script>
         function submitQ() {
           //Get the GET variables
           var $_GET=[];
           window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,function(a,name,value){$_GET[name]=value;});
            //var comment = $('#main').find('#commentbox').find('#comment');
            //Get the value of the comment box
            var comment=document.getElementById( "question" ).value;
            var username=document.getElementById( "username" ).value;
            //Send this to the php file via ajax
            $.ajax({
            type: 'get',
            url: 'utilities/lfQuestion.php',
            data: {
             class: $_GET['class'],
             comment: comment,
             username: username
            },
            success: function(data) {
             //Dont do anything
           },
            error: function(data) {
              alert(data);
            }
            });

        }
       </script>

    <!-- End questionable content -->

    <!-- The top banner of the webpage -->
    <div class="top">
        <font size="-5"><a class="logout" href="../login.php?event=logout">Logout</a></font>
        <center>
          <?php
            echo '<h1>' . $class['class_name'] . ' Live Session</h1>';
          ?>
        </center>
    </div>
  </head>

  <!-- The left sidebar -->
  <div class="left">
    <?php
      echo '<button class="button" onclick="window.location=\'home.php\';">' . $_SESSION['name'] . '\'s Home</button>';
      echo '<button class="button" onclick="window.location=\'pages.php?class=' . $_GET['class'] . '\';">' . $class['class_name'] . ' Home</button>';
      echo '<button class="button" onclick="window.location=\'topics.php?class=' . $_GET['class'] . '\';">Discussion Topics</button>';
      echo '<button class="button" onclick="window.location=\'liveSession.php?class=' . $_GET['class'] . '\';">Live Session</button>';
     ?>
  </div>

  <!-- Main content of the webpage -->
  <div id="main" class="main">
    <br>
    <div id="commentBox" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
       <input type="text" id="question" value="">
       <?php echo '<input type="hidden" id="username" value="' . $_SESSION['username'] . '">';?>
       <button id="submitComment" type="button" onclick="submitQ();">Ask</button>
    </div>
    <div id="questions" class="container-fluid" style="overflow-y: auto;max-height: 90vh;">
    </div>
  </div>
</html>
