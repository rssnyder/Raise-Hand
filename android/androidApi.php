<?php
  /*
    This file contains functions that deal with the communication between android and the database. 
    
  */

    /// Start a connection to the project database.
    /// @return A database referance variable.
    function getDB() {
        return $db;
    }

    /// Mark a question as endorsed (by a teacher)
    /// @param $db the database to query.
    /// @param $questionID the id of the question to endorse
    /// @return if it was successful or not
    function endorseQ($db, $questionID) {
        return $result;
    }
  
    /// Mark a reply as endorsed (by a teacher)
    /// @param $db the database to query.
    /// @param $replyId the id of the reply to endorse
    /// @return if it was successful or not
    function endorseR($db, $replyId) {
        return $result;
    }
  
    /// A user forgot his or her password, and this method will set a temporary password for the user and email it to him or her
    /// @param $db the database to query.
    /// @param $email the email of the user to send the temporary passsword to 
    /// @return if it was successful or not
    function forgotPassword($db, $email){
         return $result;
    }
    
    /// A method that enables a connection between android and the database during the livefeed
    /// @param $db the database to query.
    /// @param $classId the class the live feed is in
    /// @return questions and replies that are currently in the live feed
    function liveFeed($db, $classId){
        return $result;
    }

    /// A way for a user to sign in
    /// @param $db the database to query
    /// @param $username the username of the person who is trying to login
    /// @param $password the password the username typed into the android app
    /// @return if the login was successful, information on the user, otherwise says it failed
    function login($db, $username, $password){
        return $result;
    }
    /// This method gets the recent activity from the classes that the user is in 
    /// @param $db the database to query.
    /// @param $classList an array of the classes that a user is in
    /// @return recent activity made in the classes the user is in
    function notifications($db, $classList){
        return $result;
    }
    
    /// A user uses this method to post a question on the android app and add it to the database
    /// @param $db the database to query.
    /// @param $description
    /// @param $title
    /// @param $ownerId
    /// @param $topicId
    /// @param $username
    /// @return if the question was added to the database
    function postQ($db, $description, $title, $ownerId, $topicId, $username){
        return $result;
    }
    
    /// A user uses this method to post a reply on the android app and add it to the database
    /// @param $db the database to query.
    /// @param $txt
    /// @param $ownerId
    /// @param $threadId
    /// @param $replyParentId
    /// @param $username
    /// @return if the question was added to the database
    function postR($db, $txt, $username, $ownerId, $threadId, $replyParentId){
        return $result;
    }
    
    /// When a user refreshes from the android app on the questions page, this method is called. 
    /// @param $db the database to query.
    /// @param $topicId the topic id that the questions fall under
    /// @return all of the questions with this topic id
    function refreshQ($db, $topicId){
        return $result;
    }
    
    /// When a user refreshes from the android app on the replies page, this method is called. 
    /// @param $db the database to query.
    /// @param $questionId the question id that the replies fall under
    /// @return all of the replies with this question id
    function refreshR($db, $questionId){
        return $result;
    }
    
    /// When a user refreshes from the android app on the replies of replies page, this method is called. 
    /// @param $db the database to query.
    /// @param $replyId the reply id that this reply is replying to
    /// @return all of the replies that are in replies to this reply id
    function refreshRR($db, $replyId){
        return $result;
    }
    
    /// Mark a question as reported
    /// @param $db the database to query.
    /// @param $questionID the id of the question to report
    /// @return if it was successful or not
    function reportQ($db, $questionId){
        return $result;
    }
    
    /// Mark a reply as reported
    /// @param $db the database to query.
    /// @param $replyId the id of the reply to report
    /// @return if it was successful or not
    function reportR($db, $replyId){
        return $result;
    }
    
    /// A user forgot his or her password, and this method will allow them to reset their password to a new one
    /// @param $db the database to query.
    /// @param $username the username of the person who is reseting his or her password
    /// @param $password the new password the user is setting his or her password to
    /// @return if it was successful or not
    function resetPassword($db, $password, $username){
        return $result;
    }
    
    /// A user uses this method to sign up to RaiseHand using the android app
    /// @param $db the database to query.
    /// @param $firstname
    /// @param $lastname
    /// @param $password
    /// @param $email
    /// @param $username
    /// @param $universityId
    /// @return if the user was successfully added
    function signup($db, $firstname, $lastname, $password, $username, $email, $universityId){
        return $result;
    }
    
    /// This method returns all of the topics (and the questions within the topics and replies within the quesiton) for the given class
    /// @param $db the database to query.
    /// @param $classId the class to retrieve the data from
    /// @return the topics (and questions within the topics and replies within the questions)
    function topics($db, $classId){
        return $result;
    }
    
    /// Upvote a question
    /// @param $db the database to query.
    /// @param $questionID the id of the question to upvote
    /// @return if it was successful or not
    function upvoteQ($db, $questionId){
        return $result;
    }
    
    /// Upvote a reply
    /// @param $db the database to query.
    /// @param $replyId the id of the reply to be upvoted
    /// @return if it was successful or not
    function upvoteR($db, $replyId){
        return $result; 
    }
?>
