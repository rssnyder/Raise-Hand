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
	$question_id=$_GET['questionId'];
	$stmt = "SELECT r.* FROM replies r WHERE r.thread_id='$question_id' AND r.parent=0 AND (TIMESTAMPDIFF(MINUTE, r.creation, NOW())) <= 10";
	$stmt = $db->query($stmt) or die($db->error);
	while($ro= $stmt->fetch_array()){
           Echo 'NEWREPLY ';
                Echo 'REPLYTXT ';
                Echo ''.$ro['txt'].' ';
                Echo 'REPLYUSER ';
                Echo ''.$ro['user_name'].' ';
                Echo 'REPLYUSERID ';
                Echo ''.$ro['owner_id'].' ';
                Echo 'POINTS ';
                Echo ''.$ro['points'].' ';
                Echo 'ENDORSED ';
                if($ro['endorsed']==1){
                    Echo 'Yes ';
                }
                else{
                 Echo 'No ';
                }
                Echo 'CREATION ';
                Echo ''.$ro['creation'].' ';
                Echo 'PARENT ';
                Echo ''.$ro['parent'].' ';
                Echo 'REPLYID ';
                Echo ''.$ro['ID'].' ';
	}

?>
