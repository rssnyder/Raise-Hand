<?php 

	require_once 'DbConnect.php';
	
	$response = array();
	
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
			
			case 'signup':
				if(isTheseParametersAvailable(array('username','pass','first_name', 'last_name'))){
					$username = $_POST['username']; 
					$pass = $_POST['pass']; 
					$first_name = $_POST['first_name'];
					$last_name = $_POST['last_name']; 
					
					$stmt = $conn->prepare("SELECT id FROM users WHERE username = ?");
					$stmt->bind_param("ss", $username);
					$stmt->execute();
					$stmt->store_result();
					
					if($stmt->num_rows > 0){
						$response['error'] = true;
						$response['message'] = 'User already registered';
						$stmt->close();
					}else{
						$stmt = $conn->prepare("INSERT INTO users (username, pass) VALUES (?, ?)");
						$stmt->bind_param("ssss", $username, $pass);

						if($stmt->execute()){
							$stmt = $conn->prepare("SELECT username, pass FROM users WHERE username = ?"); 
							$stmt->bind_param("s",$username);
							$stmt->execute();
							$stmt->bind_result($username, $pass);
							$stmt->fetch();
							
							$user = array(
								'username'=>$username, 
								'pass'=>$pass,
								'first_name'=>$first_name,
								'last_name'=>$last_name
							);
							
							$stmt->close();
							
							$response['error'] = false; 
							$response['message'] = 'User registered successfully'; 
							$response['user'] = $user; 
						}
					}
					
				}else{
					$response['error'] = true; 
					$response['message'] = 'required parameters are not available'; 
				}
				
			break; 
			
			case 'login':
				
				if(isTheseParametersAvailable(array('username', 'pass'))){
					
					$username = $_POST['username'];
					$pass = $_POST['pass']; 
					
					$stmt = $conn->prepare("SELECT username FROM users WHERE username = ? AND pass = ?");
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
					}else{
						$response['error'] = false; 
						$response['message'] = 'Invalid username or password';
					}
				}
			break; 
			
			default: 
				$response['error'] = true; 
				$response['message'] = 'Invalid Operation Called';
		}
		
	}else{
		$response['error'] = true; 
		$response['message'] = 'Invalid API Call';
	}
	
	echo json_encode($response);
	
	function isTheseParametersAvailable($params){
		
		foreach($params as $param){
			if(!isset($_POST[$param])){
				return false; 
			}
		}
		return true; 
	}
?>