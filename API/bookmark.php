<?php
include 'dbConnect.php';

class BookMark
{
    private $db;
    private $db_table = "history";

    public function __construct()
    {
        $this->db = new DbConnect();
    }

    public function makecheckHistory($idacc, $idnovel, $idchapter)
    {
        $query = "SELECT `IDacc`, `IDnovel`, `IDchapter`, `bookmark` FROM " . $this->db_table . " WHERE `IDacc`='$idacc' and `IDnovel`= '$idnovel' and `IDchapter` ='$idchapter'";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {
            return true;
        }
        return false;
    }

    public function updateBookMark($idacc, $idnovel, $idchapter)
    {
        $query = "UPDATE " . $this->db_table . " SET `bookmark` = CURRENT_TIMESTAMP WHERE `IDacc` = '$idacc' AND `IDnovel` = '$idnovel' AND `IDchapter` = '$idchapter'";
        $updated = mysqli_query($this->db->getDb(), $query);
        if ($updated) {
            return true;
        } else return false;
    }
    public function checkBookMark($idacc, $idnovel, $idchapter)
    {
        $json = array();
        if ($this->makecheckHistory($idacc, $idnovel, $idchapter)) {
            if ($this->updateBookMark($idacc, $idnovel, $idchapter)) {
                $json['read'] = true;
                $json['message'] = "Successfully updated bookmark";
            } else {
                $json['read'] = false;
                $json['message'] = "Error updating bookmark";
            }
        }
        if ($this->makecheckHistory($idacc, $idnovel, $idchapter) == false) {
            $query = "INSERT INTO " . $this->db_table . "(`IDacc`, `IDnovel`, `IDchapter`, `bookmark`) VALUES ('$idacc','$idnovel','$idchapter',CURRENT_TIMESTAMP)";
            $inserted = mysqli_query($this->db->getDb(), $query);
            if ($inserted == true) {
                $json['read'] = true;
                $json['message'] = "Successfully add bookmark";
            } else {

                $json['read'] = false;
                $json['message'] = "Error make new bookmark";
            }
        }
        mysqli_close($this->db->getDb());
        return $json;
    }


    public function latestmarked($idacc)
    {
        $row = array();
        $query = "SELECT history.IDnovel, novel.name as novelname, history.IDchapter, chapter.Name as chaptername
        FROM history 
        INNER JOIN chapter ON history.IDchapter = chapter.ID 
        INNER JOIN novel ON history.IDnovel = novel.ID 
        WHERE history.IDacc = '$idacc' ORDER BY history.bookmark DESC LIMIT 1;";
        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);
        }
        mysqli_close($this->db->getDb());
        return $row;
    }
}
