<?php
  shell_exec('sudo git pull');
  header("Location: ../login.php?event=logout");
  die("You have pulled.")
?>
