<?php
include_once 'config.php';
require_once 'message.php';


$idnovel = "";

$idacc = "";

$content = "";




if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}

if (isset($_POST['idacc'])) {

    $idacc = $_POST['idacc'];
}

if (isset($_POST['content'])) {

    $content = $_POST['content'];
}

$comment = new Message();
$comment->__construct();
$response = array();
$response = json_encode($comment->newComment($idnovel, $idacc, $content));
echo $response;
