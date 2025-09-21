<?php
include_once 'config.php';
require_once 'user.php';


$username = "";
if (isset($_POST['username'])) {

    $username = $_POST['username'];
}

$user = new User();
$user->__construct();
$response = array();
$response = json_encode($user->getuserinfor("$username"));
echo $response;
