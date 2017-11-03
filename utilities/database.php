<?php
  /*

    This file contains functions that deal with the database. Such as getting a class, seeing if a user belongs in a class, and others.

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
    Returns the array that contains information on the thread
  */
  function getThread($db, $thread) {
    //Get this thread
    $query = "SELECT * FROM threads WHERE ID = " . $thread;
    $result = $db->query($query) or die($db->error);
    return $result->fetch_assoc();
  }

  /*
    Returns list the object that contains all children of specified comment
  */
  function getChildComm($db, $parentID, $threadID) {
    //Get the children of this comment
    $query = "SELECT * FROM replies WHERE parent = " . $parentID . " AND thread_id = " . $threadID;
    return $db->query($query) or die($db->error);
  }
 ?>
