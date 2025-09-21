<?php
require_once 'rate.php';
$rate = new RateNV;
$rate->__construct();

$idnovel = "";

if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}

$response = array();
$response = json_encode($rate->RateAvg($idnovel));
echo $response;
