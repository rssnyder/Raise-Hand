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

    function refreshQ($db, $topicId){
        return $result;
    }

    function refreshR($db, $questionId){
        return $result;
    }

    function refreshRR($db, $replyId){
        return $result;
    }

    function reportQ($db, $questionId){
        return $result;
    }

    function reportR($db, $replyId){
        return $result;
    }

    function resetPassword($db, $password, $username){
        return $result;
    }

    function signup($db, $firstname, $lastname, $password, $username, $email, $universityId){
        return $result;
    }

    function topics($db, $classId){
        return $result;
    }

    function upvoteQ($db, $questionId){
        return $result;
    }

    function upvoteR($db, $replyId){
        return $result; 
    }
?>
