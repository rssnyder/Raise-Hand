<?php 

	require_once 'sql.php';
	
	$response = array();
	$username = $_POST['username'];
	$pass = $_POST['pass']; 	
	$stmt = $db->prepare("SELECT * FROM users WHERE username = ‘$username’ AND pass=‘$pass’”);
	$stmt->bind_param("ss",$username, $pass);			
	$stmt->execute();			
	$stmt->store_result();
					
	if($stmt->num_rows > 0){
						
		$stmt->bind_result($username, $pass);
		$stmt->fetch();
						
		$user = array(
			'username'=>$username, 
			'pass'=>$pass,
			'first_name'=>$first_name,
			'last_name'=>$last_name
		);
						
		$response['error'] = false; 
		$response['message'] = 'Login successfull'; 
		$response['user'] = $user; 
	}
	else{
		$response['error'] = false; 
		$response['message'] = 'Invalid username or password';
	}
	echo json_encode($response);
?>
