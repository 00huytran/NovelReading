<?php
include 'dbConnect.php';
class ReadingPage
{
    private $db;
    private $db_table = "reading";

    public function __construct()
    {
        $this->db = new DbConnect();
    }

    public function getReadingPage($idchapter)
    {
        $data = array();
        // SELECT * FROM `reading` WHERE `idchapter`=1
        $query = "SELECT `id`, `idchapter`, `img`, `textcontent` FROM " . $this->db_table . " WHERE idchapter = '$idchapter'";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {

            while ($row = mysqli_fetch_assoc($result)) {
                $data[] = $row;
            }
        }
        mysqli_close($this->db->getDb());
        return $data;
    }
}
