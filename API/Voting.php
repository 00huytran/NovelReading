<?php
require_once 'rate.php';
$rate = new RateNV;
$rate->__construct();

$idacc = "";

$idnovel = "";

$rating = "";

if (isset($_POST['idacc'])) {

    $idacc = $_POST['idacc'];
}

if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}

if (isset($_POST['rating'])) {

    $rating = $_POST['rating'];
}
$response = array();
$response = json_encode($rate->newRate($idnovel, $idacc, $rating));
echo $response;
