<?php
    //require_once 'sql.php';
	$host="mysql.cs.iastate.edu";
	$port=3306;
	$socket="";
	$user="dbu309sab3";
	$password="SD0wFGqd";
	$dbname="db309sab3";
	//Connect to database
	$db = new mysqli($host, $user, $password, $dbname, $port, $socket) or die ('Could not connect to the database server' . mysqli_connect_error());
	$studentID=$_GET['studentId'];
	$q = "SELECT * FROM users WHERE ID='$studentID'";
	$res = $db->query($question) or die($db->error);
    $result = $db->query($question) or die($db->error);
    //Fetch all of the topics for a given class
    while($row = $result->fetch_array())
    {
        Echo 'NEWTOPIC ';
        Echo 'CREATETIME ';
        Echo ''.$row['creation_time'].' ';
        Echo 'TOPICNAME ';
        Echo ''.$row['topic_name'].' ';
        Echo 'ID ';
        Echo ''.$row['ID'].' ';
        Echo 'DESCRIPTION ';
        Echo ''.$row['description'].' ';
    }
?>
