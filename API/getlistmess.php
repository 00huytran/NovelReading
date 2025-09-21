<?php
include_once 'config.php';
require_once 'message.php';

$idnovel = "";

if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}
$mess = new Message();
$response = array();
$response = json_encode($mess->listComment($idnovel));
echo $response;
