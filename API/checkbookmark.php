<?php

require_once 'bookmark.php';


$idacc = "";

$idnovel = "";

$idchapter = "";

if (isset($_POST['idacc'])) {

    $idacc = $_POST['idacc'];
}

if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}

if (isset($_POST['idchapter'])) {

    $idchapter = $_POST['idchapter'];
}

$bookmark = new BookMark();

$response = array();
$response = json_encode($bookmark->checkBookMark($idacc, $idnovel, $idchapter));
echo $response;
