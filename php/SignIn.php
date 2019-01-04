<?php
 $con = mysqli_connect("localhost", "k2h0508", "rudgns0508", "k2h0508");
 
 $userEmail = $_POST["userEmail"];
 $userPassword = $_POST["userPassword"];
 
 $statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userEmail = ? AND userPassword = ?");
 mysqli_stmt_bind_param($statement, "ss", $userEmail, $userPassword);
 mysqli_stmt_execute($statement);
 
 mysqli_stmt_store_result($statement);
 mysqli_stmt_bind_result($statement, $userEmail, $userPassword, $userName);
 
 $response = array();
 $response["success"] = false;
 
 while(mysqli_stmt_fetch($statement)){ 
  $response["success"] = true;
  $response["userEmail"] = $userEmail;
  $response["userPassword"] = $userPassword;
  $response["userName"] = $userName;
 }
 
 echo json_encode($response); 
?>﻿