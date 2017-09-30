<?php

    $uploaddir = '/var/www/html/uploads/';
    $uploadfile = $uploaddir . basename($_FILES['uploaded_file']['name']);

    echo "<p>";

    if (copy($_FILES['uploaded_file']['tmp_name'], $uploadfile)) {
      echo "File is valid, and was successfully uploaded.\n";
    } else {
       echo "Upload failed";
    }

    echo "</p>";
    echo '<pre>';
    echo 'Here is some more debugging info:';
    print_r($_FILES);
    print "</pre>";
    header("Location: uploads");
?>
