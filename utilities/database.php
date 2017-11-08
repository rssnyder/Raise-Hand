<?php
  /*

    This file contains functions that deal with the database. Such as getting a class, seeing if a user belongs in a class, and others.

    My code was dirty before. Dirty like a roadside rest stop in the middle of Lousiana.
    Now it shines.

    Remove this file if you want to see civilization fall.

  /*
    Returns a variable that contains the database. This can be used when you need one off queries
  */
  function getDB() {
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

    //Pass to parent
    return $db;
  }

  /*
    Returns the array that contains the class object
  */
  function getClass($db, $class) {
    //Get this class
    $query = "SELECT * FROM classes WHERE ID = " . $class;
    $result = $db->query($query) or die($db->error);
    return $result->fetch_assoc();
  }

  /*
    Gets object of all classes this person is a memeber of, in any role
  */
  function getClasses($db, $uID) {
    //Get the classes that the teacher is a teacher of
    $query = "SELECT class_id FROM userClasses WHERE user_id = $uID";
    $result = $db->query($query) or die($db->error);
    return $result;
  }

  /*
    Returns the array that contains information on the thread
  */
  function getThread($db, $thread) {
    //Get this thread
    $query = "SELECT * FROM threads WHERE ID = " . $thread;
    $result = $db->query($query) or die($db->error);
    return $result->fetch_assoc();
  }

  /*
    Returns object of threads avalible in this SplSubject
  */
  function getThreads($db, $topic) {
    //Get the threads
    $query = "SELECT * FROM threads WHERE topic_id = " . $topic;
    $result = $db->query($query) or die('Error querying database.');
    return $result;
  }

  /*
    Returns object of topics from a class
  */
  function getTopics($db, $class) {
    //Get the topics
    $query = "SELECT * FROM topics WHERE class_id = " . $class;
    $result = $db->query($query) or die('Error querying database.');
    return $result;
  }

  /*
    Returns list the object that contains all children of specified comment
  */
  function getChildComm($db, $parentID, $threadID) {
    //Get the children of this comment
    $query = "SELECT * FROM replies WHERE parent = " . $parentID . " AND thread_id = " . $threadID;
    $result = $db->query($query) or die($db->error);
    return $result;
  }

  /*
    Checks if a user belongs in a given class
  */
  function doesBelong($db, $class, $uID) {
    //Check to see if student is actually in this class
    $query = "SELECT class_id FROM userClasses WHERE user_id = " . $uID ." AND class_id = " . $class;
    $result = $db->query($query) or die($db->error);
    if($oclass = $result->fetch_assoc()) {
        //This user is associated with this class
        return true;
    }
    else {
      //Did not find user and class in table
      return false;
    }
  }

  /*
    Gets the relationship of the user to the class
  */
  function getRelationship($db, $uID, $class) {
    //Get the userClass referance
    $query = "SELECT relationship FROM userClasses WHERE user_id = " . $uID . " AND class_id = " . $class;
    $result = $db->query($query) or die($db->error);
    $relationship = $result->fetch_assoc();
    return $relationship['relationship'];
  }

  /*
    Gets a users information
  */
  function getUserInfo($db, $uID) {
    //Get the user with limited information
    $query = "SELECT first_name, last_name,email FROM users WHERE ID = " . $uID;
    $result = $db->query($query) or die($db->error);
    $user = $result->fetch_assoc();
    return $user;
  }

  /*
    Function to get list of TAs for a class
  */
  function getTAs($db, $class) {
    //Get the TAs IDs from the class
    $query = 'SELECT user_id FROM userClasses WHERE relationship = 3 AND class_id = ' . $class;
    $result = $db->query($query) or die($db->error);
    //Get all these users
    $allTA = array();
    while($TA = $result->fetch_assoc()) {
      //Build a string of the name
      $query = 'SELECT first_name, last_name, email FROM users WHERE ID = ' . $TA['user_id'];
      $result2 = $db->query($query) or die($db->error);
      $thisTA = $result2->fetch_assoc();
      $name = $thisTA['first_name'] . ' ' . $thisTA['last_name'] . ': ' . $thisTA['email'];
      array_push($allTA, $name);
    }
    //Return the array of TA names
    return $allTA;
  }

 ?>
