<?php
  //A playground for all things PHP
  $creds = "info.txt";
  $file = fopen($creds, "r");
  $host = fgets($file);
  $port = fgets($file);
  $user = fgets($file);
  $password = fgets($file);
  $dbname = fgets($file);
  fclose($file);
?>
