<?php
  shell_exec('git stash');
  shell_exec('git pull');
  header("Location: ../index.php");
  die("You have pulled.")
?>
