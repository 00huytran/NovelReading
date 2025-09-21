<?php
include_once 'config.php';
require_once 'chapter.php';

$idnovel = "";

if (isset($_POST['idnovel'])) {

    $idnovel = $_POST['idnovel'];
}
$chapter = new Chapter();
$chapter->__construct();
$response = array();
$response = json_encode($chapter->getChapters($idnovel));
echo $response;
