<?php
  /*
    This file contains functions that deal with the communication between android and the database. 
    
  */

    /// Start a connection to the project database.
    /// @return A database referance variable.
    function getDB() {
        return $db;
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
        return $result;
    }

    function notifications($db, $classList){
        return $result;
    }

    function postQ($db, $description, $title, $ownerId, $topicId, $username){
        return $result;
    }

    function postR($db, $txt, $username, $ownerId, $threadId, $replyParentId){
        return $result;
    }

    function refreshQ(){
        return $result;
    }

    function refreshR(){
        return $result;
    }

    function refreshRR(){
        return $result;
    }

    function reportQ(){
        return $result;
    }

    function reportR(){
        return $result;
    }

    function resetPassword(){
        return $result;
    }

    function signup(){
        return $result;
    }

    function topics(){
        return $result;
    }

    function upvoteQ(){
        return $result;
    }

    function upvoteR(){
        return $result; 
    }
?>
