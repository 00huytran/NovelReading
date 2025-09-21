<?php 
    include_once 'config.php';
    require_once 'readingpage.php';

    $idchapter = "";

    if (isset($_POST['idchapter'])) {
        $idchapter = $_POST['idchapter'];
    }
    $readingpage = new ReadingPage();
    $readingpage->__construct();
    $response = array();
    $response = json_encode($readingpage->getReadingPage($idchapter));
    echo $response;