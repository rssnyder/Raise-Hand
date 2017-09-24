<?php
	//Simply a landing page to figure out where user should be placed
	session_start();
	//If user is already logged in then send to their home
	if (isset($_SESSION['loggedin']) && $_SESSION['loggedin'] == true) {
		if($_SESSION['role'] == 1) {
      header("Location: admin/home.php");
      die("go");
    }
		else if($_SESSION['role'] == 2) {
      header("Location: teacher/home.php");
      die("go");
    }
		else {
			header("Location: student/home.php");
			die("go");
		}
	}
	//Otherwise, send to login page
	else {
		header("Location: login.php");
	}
?>
