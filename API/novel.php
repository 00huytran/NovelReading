<?php

include 'dbConnect.php';

class Novel
{
    private $db;
    private $db_table = "novel";

    public function __construct()
    {
        $this->db = new DbConnect();
    }

    public function getTop5()
    {
        // SELECT novel.ID, novel.name, novel.img, novel.author, novel.discription
        // FROM novel
        // INNER JOIN rate ON rate.idnovel = novel.ID
        // GROUP BY novel.ID
        // HAVING AVG(rate.rating) >= 4
        // ORDER BY AVG(rate.rating) DESC;
        //$query = "SELECT ID,name,img,author,discription FROM " . $this->db_table . " LIMIT 5";
        $query = "SELECT novel.ID, novel.name, novel.img, novel.author, novel.discription
                FROM ".$this->db_table."
                INNER JOIN rate ON rate.idnovel = novel.ID
                GROUP BY novel.ID
                HAVING AVG(rate.rating) >= 4
                ORDER BY AVG(rate.rating) DESC";

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
    public function getnovel(){
        $query = "SELECT ID,name,img,author,discription FROM " . $this->db_table . " LIMIT 5";
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

    public function getresearchnovel($string){
        $query="SELECT * FROM " . $this->db_table . " WHERE name LIKE '%".$string."%'";
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