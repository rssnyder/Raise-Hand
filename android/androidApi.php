<?php
  /*
    This file contains functions that deal with the communication between android and the database. 
    
  */

   /// Start a connection to the project database.
   /// @return A database referance variable.
  function getDB() {
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
  /// @param $qID the id of the question to endorse
  /// @return A database result that is a list of class information arrays.
  function endorseQ($db, $qID) {
    return $result;
  }
  
  /// Get all the class information for classes that a user belongs to.
  /// @param $db the database to query.
  /// @param $rID the id of the reply to endorse
  /// @return A database result that is a list of class information arrays.
  function endorseR($db, $rID) {
    return $result;
  }
  
  
  function forgotPassword($db, $email){
      return $result;
  }

function liveFeed($db, $classId){
    return $result;
}

function login($db, $username, $password){
    
}

function notifications($db, $classList){
    
}

function postQ($db, $description, $title, $ownerId, $topicId, $username){
    
}

 ?>
