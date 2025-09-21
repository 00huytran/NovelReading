<?php

require_once 'user.php';


$username = "";



if (isset($_POST['username'])) {

    $username = $_POST['username'];
}

$userObject = new User();
$response = array();
$response = $userObject->getname($username);
echo json_encode($response);
