<?php
include_once 'config.php';
require_once 'novel.php';

$novel = new Novel();
$novel->__construct();
$response = array();
$response = json_encode($novel->getTop5());
echo $response;
