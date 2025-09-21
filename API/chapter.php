<?php
include 'dbConnect.php';
class Chapter
{
    private $db;
    private $db_table = "chapter";

    public function __construct()
    {
        $this->db = new DbConnect();
    }

    public function getChapters($idnovel)
    {
        $query = "SELECT * FROM " . $this->db_table . "  WHERE idnovel = $idnovel ORDER BY ID ASC  LIMIT 0, 10 ";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {
            $data = array();
            while ($row = mysqli_fetch_assoc($result)) {
                $data[] = $row;
            }
            mysqli_close($this->db->getDb());
            return $data;
        }
        mysqli_close($this->db->getDb());
        return false;
    }
}
