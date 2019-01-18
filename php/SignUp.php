<?php
 $con = mysqli_connect("localhost","k2h0508","rudgns0508","k2h0508");
 $parent_num = $_POST["parent_num"];
 $userEmail = $_POST["userEmail"];
 $userPassword = $_POST["userPassword"];
 $parentName = $_POST["parentName"];
 $parentHardAd = $_POST["parentHardAd"];
 $parentAge = $_POST["parentAge"];
 $childCount = $_POST["childCount"];

 $statement = mysqli_prepare($con, "INSERT INTO userParents VALUES(?, ?, ?, ?, ?, ?)");
 mysqli_stmt_bind_param($statement, "ssssss", $parent_num, $userEmail, $userPassword, $parentName, $parentHardAd, $parentAge, $childCount);
 mysqli_stmt_execute($statement);
 
 $response = array();
 $response["success"] = true;
  
 echo json_encode($response);

?>