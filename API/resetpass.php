<?php 


$email = "";
$otp = "";
$newpass = "";

if(isset($_POST['email'])){
    $email = $_POST['email'];

}

if(isset($_POST['otp'])){
    $email = $_POST['otp'];

}

if(isset($_POST['newpass'])){
    $email = $_POST['newpass'];

}


$userObject = new User();

$response = array();
$response = $userObject->resetpass($email,$otp,$newpass);
echo json_encode($response);