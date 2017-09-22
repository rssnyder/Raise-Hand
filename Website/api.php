<?php 

	require_once 'sql.php';
	
	$response = array();
	$username = $_POST['username'];
	$pass = $_POST['pass']; 	
	$insert = "SELECT * FROM users";
    	//Excecute
    	$result = $db->query($insert) or die($db->error);
    	//Get the data of the username they specified
    	$pass = $result->fetch_assoc();
    	//Check for admin/teacher privilages
    	$role = $pass["role_id"];
    	//Get hashed password from database
    	$pass = $pass["pass"];
			
	if($result->num_rows > 0){
						
		//$stmt->bind_result($username, $pass);
		//$stmt->fetch();
						
		/*$user = array(
			'username'=>$username, 
			'pass'=>$pass,
			'first_name'=>$first_name,
			'last_name'=>$last_name
		);*/
						
		$response['error'] = false; 
		$response['message'] = 'Login successfull'; 
		//$response['user'] = $user; 
	}
	else{
		$response['error'] = false; 
		$response['message'] = 'Invalid username or password';
	}
	echo json_encode($response);
?>