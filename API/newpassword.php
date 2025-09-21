<?php

require_once 'user.php';


$username = "";

$password = "";


if (isset($_POST['username'])) {

    $username = $_POST['username'];
}

if (isset($_POST['password'])) {

    $password = $_POST['password'];
}
$userObject = new User();
$response = array();
$response = $userObject->changepass($username,$password);
echo json_encode($response);