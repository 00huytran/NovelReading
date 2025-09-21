<?php

require_once 'user.php';


$email = "tranquanghuycva@gmail.com";

if (isset($_POST['email'])) {
    $email = $_POST['email'];
}
$userObject = new User();

$response = array();
$response = $userObject->forgotpass($email);
echo json_encode($response);
