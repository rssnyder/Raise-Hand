<?php
	//Simply a landing page to figure out where user should be placed
	session_start();
	//If user is already logged in then send to their home
	if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
		header("Location: home.php");
	}
	//Otherwise, send to login page
	else {
		header("Location: login.php");
	}
?>
