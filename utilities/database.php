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

 ?>
