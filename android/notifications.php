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
	$class_id=$_GET['classId'];
	$classes=explode('+', $class_id);
	$list='(';
	foreach($classes as $classID){
	   $list.=$classID . ', ';
	}
    $list=substr($list, 0, strlen($list)-2);
    $list.=')';
	echo $list;
    $userClasses= "SELECT ID FROM topics WHERE class_id IN $list";
    echo $userClasses;
    $topic=$db->query($userClasses) or die($db->error);
    $topics= $topic->fetch_array();
    $list2=implode(', ', $topics);
	$stmt = "SELECT t.* FROM threads t WHERE t.topic_id IN ($list2) AND (TIMESTAMPDIFF(MINUTE, t.creation, NOW())) <= 180";
	$stmt = $db->query($stmt) or die($db->error);
	while($r= $stmt->fetch_array()){
            Echo 'NEWQUESTION ';
            Echo 'QUESTIONTITLE ';
            Echo ''.$r['title'].' ';
            Echo 'QUESTIONDESCRIPTION ';
            Echo ''.$r['description'].' ';
            Echo 'QUESTIONUSER ';
            Echo ''.$r['user_name'].' ';
            Echo 'QUESTIONUSERID ';
            Echo ''.$r['owner_id'].' ';
            Echo 'POINTS ';
            Echo ''.$r['points'].' ';
            Echo 'ENDORSED ';
            if($r['endorsed']==1){
                Echo 'Yes ';
            }
            else{
                Echo 'No ';
            }
            Echo 'CREATION ';
            Echo ''.$r['creation'].' ';
            Echo 'QUESTIONID ';
            Echo ''.$r['ID'].' ';
	}

?>
