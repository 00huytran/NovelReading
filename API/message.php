<?php
include 'dbConnect.php';

class Message
{
    private $db;
    private $db_table = "comment";
    public function __construct()
    {
        $this->db = new DbConnect();
    }
    public function isIdnovelExit($idnovel)
    {
        $query = "select ID from novel WHERE ID = '$idnovel'";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {

            return true;
        }
        mysqli_close($this->db->getDb());
        return false;
    }
    public function isIdaccExit($idacc)
    {
        $query = "select ID from account WHERE ID = '$idacc'";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {

            return true;
        }
        mysqli_close($this->db->getDb());
        return false;
    }
    public function newComment($idnovel, $idacc, $content)
    {
        if ($this->isIdnovelExit($idnovel) == true && $this->isIdaccExit($idacc) == true) {
            if ($content != null) {
                $query = "INSERT INTO " . $this->db_table . " (`IDnovel`, `IDacc`, `Content`) VALUES ('$idnovel','$idacc','$content')";
                $inserted = mysqli_query($this->db->getDb(), $query);
                if ($inserted == true) {

                    $json['success'] = true;
                    $json['message'] = "Successfully sent";
                } else {

                    $json['success'] = false;
                    $json['message'] = "Error in sending comment";
                }
                mysqli_close($this->db->getDb());
            } else {
                $json['success'] = false;
                $json['message'] = "you did not comment";
            }
        } else {
            $json['success'] = false;
            $json['message'] = "Can not find user or novel to sent comment";
        }
        return $json;
    }

    public function listComment($idnovel)
    {
        $query = "SELECT account.Useracc as user,rate.rating , comment.content
        FROM " . $this->db_table . "
        INNER JOIN account ON account.ID = comment.IDacc
        LEFT JOIN rate ON rate.idnovel = comment.IDnovel AND rate.idacc = comment.IDacc
        WHERE comment.idnovel = '$idnovel' ORDER BY comment.id DESC";
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
