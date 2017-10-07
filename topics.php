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
	
	$classId = $_GET['classId'];
    $question = "SELECT * FROM topics WHERE class_id='$classId'";
    //Excecute
    $result = $db->query($question) or die($db->error);
    while($row = $result->fetch_array())
    {
        Echo 'NEWTOPIC ';
        Echo 'CREATETIME ';
        Echo ''.$row['creation_time'].' ';
        Echo 'TOPICNAME ';
        Echo ''.$row['topic_name'].' ';
        Echo 'DESCRIPTION ';
        Echo ''.$row['description'].' ';
        $quest= "SELECT * FROM threads WHERE topic_id= '$row[ID]'";
        $res= $db->query($quest) or die($db->error);
        while($r= $res->fetch_array()){
            Echo 'NEWQUESTION ';
            Echo 'QUESTIONTITLE ';
            Echo ''.$r['title'].' ';
            Echo 'QUESTIONDESCRIPTION ';
            Echo ''.$r['description'].' ';
            Echo 'QUESTIONUSER ';
            Echo ''.$r['user_name'].' ';
            Echo 'POINTS ';
            Echo ''.$r['points'].' ';
            Echo 'ENDORSED ';
            if($r['endorsed_user_id']==null){
                Echo 'No ';
            }
            else{
                Echo 'Yes ';
            }
            Echo 'CREATION ';
            Echo ''.$r['creation'].' ';
            $que= "SELECT * FROM replies WHERE thread_id= '$r[ID]'";
            $resp= $db->query($que) or die($db->error);
            while($ro= $resp->fetch_array()){
                Echo 'NEWREPLY ';
                Echo 'REPLYTXT ';
                Echo ''.$ro['txt'].' ';
                Echo 'REPLYUSER ';
                Echo ''.$ro['user_name'].' ';
                Echo 'POINTS ';
                Echo ''.$ro['points'].' ';
                Echo 'ENDORSED ';
                if($ro['endorsed_user_id']==null){
                    Echo 'No ';
                }
                else{
                 Echo 'Yes ';
                }
                Echo 'CREATION ';
                Echo ''.$ro['creation'].' ';
            }
        }
    }
?>