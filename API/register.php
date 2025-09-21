<?php

require_once 'user.php';

$username = "";

$password = "";

$email = "";


if (isset($_POST['username'])) {

    $username = $_POST['username'];
}

if (isset($_POST['password'])) {

    $password = $_POST['password'];
}

if (isset($_POST['email'])) {

    $email = $_POST['email'];
}

$userObject = new User();

// Registration
$json_registration = array();

if (!empty($username) && !empty($password) && !empty($email)) {

    $hashed_password = $password;

    $json_registration = $userObject->createNewRegisterUser($username, $hashed_password, $email);

    echo json_encode($json_registration);
}
