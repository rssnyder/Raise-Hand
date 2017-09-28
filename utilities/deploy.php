<?php
  shell_exec('git pull');
  header("Location: ../login.php?event=logout");
  die("You have pulled.")
?>
