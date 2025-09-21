<?php

include_once 'config.php';
require_once 'novel.php';   



$name = "";


if (isset($_POST['name'])) {
    $name = $_POST['name'];
}


$novel = new Novel();
$novel->__construct();
$response = array();
$response = json_encode($novel->getResearchNovel($name));
echo $response;