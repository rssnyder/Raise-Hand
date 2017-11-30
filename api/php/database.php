<?php
  /*
    This file contains functions that deal with the database. Such as getting a class, seeing if a user belongs in a class, and others.

    My code was dirty before. Dirty like a roadside rest stop in the middle of Lousiana.
    Now it shines.

    Remove this file if you want to see civilization fall.
  */

   /// Start a connection to the project database.
   /// @TODO Grab all this information from a file instread of hard coding the credentails.
   /// @return A database referance variable.
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

  /// Get all the information on a given class.
  /// @param $db the database to query.
  /// @param $class the class ID in integer or string form.
  /// @return Array of class properties.
  function getClass($db, $class) {
    //Get this class
    $query = "SELECT * FROM classes WHERE ID = " . $class;
    $result = $db->query($query) or die($db->error);
    return $result->fetch_assoc();
  }

  /// Get all the class information for classes that a user belongs to.
  /// @param $db the database to query.
  /// @param $uID the user identification number, in integer or string form.
  /// @return A database result that is a list of class information arrays.
  function getClasses($db, $uID) {
    //Get the classes that the teacher is a teacher of
    $query = "SELECT class_id FROM userClasses WHERE user_id = $uID";
    $result = $db->query($query) or die($db->error);
    return $result;
  }

  /// Get the information on a given thread.
  /// @param $db the database to query.
  /// @param $threadID the thread identification number of the thread you wish see.
  /// @return An array of information on the given thread.
  function getThread($db, $threadID) {
    //Get this thread
    $query = "SELECT * FROM threads WHERE ID = " . $threadID;
    $result = $db->query($query) or die($db->error);
    return $result->fetch_assoc();
  }

  /// Get all the threads that are children of a given topic.
  /// @param $db the database to query.
  /// @param $topicID the topic identification number of the topic we wish to see.
  /// @return A list of arrays that contain information on all the children threads of this topic.
  function getThreads($db, $topicID) {
    //Get the threads
    $query = "SELECT * FROM threads WHERE topic_id = " . $topicID;
    $result = $db->query($query) or die('Error querying database.');
    return $result;
  }

  /// Get all the topics that belong to a certain class.
  /// @param $db the database to query.
  /// @param $classID The identification number of the class we want to display.
  /// @return A list of attays that contain information on all the topics in this class.
  function getTopics($db, $classID) {
    //Get the topics
    $query = "SELECT * FROM topics WHERE class_id = " . $classID;
    $result = $db->query($query) or die('Error querying database.');
    return $result;
  }

  /// Get all the child comments of a given comment, from a certain thread.
  /// @param $db the database to query.
  /// @param $parentID the comment identification number of whom we wish to see the children of.
  /// @param $threadID the thread to which this comment belings to.
  /// @return A list of arrays that hold information on all the children of this comment.
  function getChildComm($db, $parentID, $threadID) {
    //Get the children of this comment
    $query = "SELECT * FROM replies WHERE parent = " . $parentID . " AND thread_id = " . $threadID;
    $result = $db->query($query) or die($db->error);
    return $result;
  }

  /// A function to see if a user should be able to see a given class page.
  /// @param $db the database to query.
  /// @param $classID the class identification number that this user wishes to see.
  /// @param $uID the user identification number that wihes to see this class.
  /// @return True if the user can see this class, false otherwise.
  function doesBelong($db, $classID, $uID) {
    //Check to see if student is actually in this class
    $query = "SELECT class_id FROM userClasses WHERE user_id = " . $uID ." AND class_id = " . $classID;
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

  /// Get the relationship that a user and class share.
  /// @param $db the database to query.
  /// @param $uID the user identification number of the user we want to see.
  /// @param $classID the class identification number that we wish to see the realtionship to.
  /// @return The realtionship, a number in our case, between the user and class.
  function getRelationship($db, $uID, $classID) {
    //Get the userClass referance
    $query = "SELECT relationship FROM userClasses WHERE user_id = " . $uID . " AND class_id = " . $classID;
    $result = $db->query($query) or die($db->error);
    $relationship = $result->fetch_assoc();
    return $relationship['relationship'];
  }

  /// Get the full name and email of a given user.
  /// @param $db the database to query.
  /// @param $uID the user identification number of the user we want to query.
  /// @return An array that contains the information on the user.
  function getUserInfo($db, $uID) {
    //Get the user with limited information
    $query = "SELECT first_name, last_name,email FROM users WHERE ID = " . $uID;
    $result = $db->query($query) or die($db->error);
    $user = $result->fetch_assoc();
    return $user;
  }

  /// Get a list of the TAs that belong to a certain class.
  /// @param $db the database to query.
  /// @param $classID the class identification number that we want to see the assistants of.
  /// @return An array of strings, each string built for display on a webpage that contain the name and email of each TA.
  function getTAs($db, $classID) {
    //Get the TAs IDs from the class
    $query = 'SELECT user_id FROM userClasses WHERE relationship = 3 AND class_id = ' . $classID;
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
