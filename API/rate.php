<?php
include 'dbConnect.php';

class RateNV
{
    private $db;
    private $db_table = "rate";

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
    public function IsRated($idnovel, $idacc)
    {
        $query = "SELECT `rating` FROM `rate` WHERE `idnovel`='$idnovel' AND `idacc`=$idacc";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {
            return false;
        } else return true;
    }
    public function newRate($idnovel, $idacc, $rate)
    {
        $json = array();
        if ($rate <= 0) {
            $json['success'] = false;
            $json['message'] = "You had not rate yet. Please choose and vote again";
        } else if ($this->isIdnovelExit($idnovel) == true && $this->isIdaccExit($idacc) == true) {
            if ($this->IsRated($idnovel, $idacc) == true) {
                $query = "insert into " . $this->db_table . " (idnovel, idacc, rating) values ('$idnovel','$idacc' ,'$rate')";
                $inserted = mysqli_query($this->db->getDb(), $query);

                if ($inserted == true) {

                    $json['success'] = true;
                    $json['message'] = "Successfully sent";
                } else {

                    $json['success'] = false;
                    $json['message'] = "Error in sending";
                }
            } else {
                $json['success'] = false;
                $json['message'] = "Error you might voted this novel";
            }
            mysqli_close($this->db->getDb());
        } else {
            $json['success'] = false;
            $json['message'] = "Error not found idnovel or idaccount or both or you might be voted";
        }
        return $json;
    }


    public function RateAvg($idnovel)
    {
        $query ="SELECT AVG(rating) FROM rate WHERE idnovel = '$idnovel'";
        $result = mysqli_query($this->db->getDb(),$query);
        if(mysqli_num_rows($result)>0){
            $row = mysqli_fetch_assoc($result);
            mysqli_close($this->db->getDb());
            return $row;
        }
        mysqli_close($this->db->getDb());
        return false;

    }
}
