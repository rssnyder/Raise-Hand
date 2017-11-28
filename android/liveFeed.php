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

	$query = "SELECT * FROM liveQueue" . $_GET['class'] . " WHERE class_id = " . $_GET['class'];
  	$result = $db->query($query) or die($db->error);

  	$a = 1;

    $thing = $result->fetch_assoc();
  	//print the first element (so the comma isn't at the beginning)
  	Echo '{';
  	Echo '"result":';
  	Echo '[';
	Echo '{"ID":';
  	Echo '"'.$thing['ID'].'"';
  	Echo ', "username":';
  	Echo '"'.$thing['username'].'"';
  	Echo ', "class_id":';
  	Echo '"'.$thing['class_id'].'"';
  	Echo ', "creation":';
  	Echo '"'.$thing['creation'].'"';
  	Echo ', "txt":';
  	Echo '"'.$thing['txt'].'"';
  	Echo '}';

  	// Print the rest of the elements
  	while($class = $result->fetch_assoc()) {
  		if($a > 1){
		  	Echo ',{"ID":';
		  	Echo '"'.$class['ID'].'"';
		  	Echo ', "username":';
		  	Echo '"'.$class['username'].'"';
		  	Echo ', "class_id":';
		  	Echo '"'.$class['class_id'].'"';
		  	Echo ', "creation":';
		  	Echo '"'.$class['creation'].'"';
		  	Echo ', "txt":';
		  	Echo '"'.$class['txt'].'"';
		  	Echo '}';
	  }
	  $a = 2;
  	}
  	Echo ']';
  	Echo '}';

?>