<?php


use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

include 'dbConnect.php';
require './vendor/autoload.php';



class User
{

    private $db;

    private $db_table = "account";

    public function __construct()
    {
        $this->db = new DbConnect();
    }

    public function getuserinfor($username)
    {
        $query = "SELECT `ID`, `Useracc` FROM " . $this->db_table . " WHERE `Useracc`='$username' limit 1";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {

            $row = mysqli_fetch_assoc($result);
            mysqli_close($this->db->getDb());

            return $row;
        }
        mysqli_close($this->db->getDb());
        return false;
    }

    public function checkPasswordFormat($password)
    {
        // Mật khẩu phải có ít nhất 8 ký tự
        // Mật khẩu phải chứa ít nhất một chữ cái viết hoa
        // Mật khẩu phải chứa ít nhất một chữ cái viết thường
        // Mật khẩu phải chứa ít nhất một số
        // Mật khẩu có thể chứa các ký tự đặc biệt như !@#$%^&*
        $pattern = '/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/';

        // Sử dụng hàm preg_match() để kiểm tra mật khẩu với biểu thức chính quy
        if (preg_match($pattern, $password)) {
            return true; // Mật khẩu đáp ứng yêu cầu định dạng
        } else {
            return false; // Mật khẩu không đáp ứng yêu cầu định dạng
        }
    }

    public function isLoginExist($username, $password)
    {

        $query = "select * from " . $this->db_table . " where Useracc = '$username' AND pass = '$password' Limit 1";

        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {

            mysqli_close($this->db->getDb());


            return true;
        }

        mysqli_close($this->db->getDb());

        return false;
    }

    public function isEmailUsernameExist($username, $email)
    {

        $query = "select * from " . $this->db_table . " where Useracc = '$username' OR Email = '$email'";

        $result = mysqli_query($this->db->getDb(), $query);

        if (mysqli_num_rows($result) > 0) {

            mysqli_close($this->db->getDb());

            return true;
        }

        return false;
    }

    public function isValidEmail($email)
    {
        return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
    }

    public function createNewRegisterUser($username, $password, $email)
    {

        $isExisting = $this->isEmailUsernameExist($username, $email);

        if ($isExisting) {

            $json['success'] = false;
            $json['message'] = "Error in registering. Probably the username/email already exists";
        } else {

            $isValid = $this->isValidEmail($email);
            if ($isValid) {
                if ($this->checkPasswordFormat($password)) {
                    $query = "insert into " . $this->db_table . " (Useracc, Email, pass) values ('$username','$email' ,'$password')";

                    $inserted = mysqli_query($this->db->getDb(), $query);

                    if ($inserted == true) {

                        $json['success'] = true;
                        $json['message'] = "Successfully registered the user";
                    } else {

                        $json['success'] = false;
                        $json['message'] = "Error in registering. Probably the username/email already exists";
                    }
                    mysqli_close($this->db->getDb());
                } else {
                    $json['success'] = false;
                    $json['message'] = "Error in registering. Probably the wrong password format";
                }
            } else {
                $json['success'] = false;
                $json['message'] = "Error in registering. Email Address is not valid";
            }
        }

        return $json;
    }

    public function loginUsers($username, $password)
    {

        $json = array();

        $canUserLogin = $this->isLoginExist($username, $password);

        if ($canUserLogin) {

            $json['success'] = true;
            $json['message'] = "Successfully logged in";
        } else {
            $json['success'] = false;
            $json['message'] = "Incorrect details";
        }

        return $json;
    }

    public function forgotpass($email)
    {
        $isValid = $this->isValidEmail($email);
        if ($isValid) {
            try {
                $otp = random_int(10000, 99999);
            } catch (Exception $e) {
                $otp = rand(10000, 99999);
            }
            $query = "update " . $this->db_table . " set otpreset = '" . $otp . "' where Email = '" . $email . "'";
            $upd = mysqli_query($this->db->getDb(), $query);
            if ($upd == true) {
                $mail = new PHPMailer(true);

                try {


                    //Server settings
                    $mail->SMTPDebug = 2;                      //Enable verbose debug output
                    $mail->isSMTP();                                            //Send using SMTP
                    $mail->Host       = 'smtp.example.com';                     //Set the SMTP server to send through
                    $mail->SMTPAuth   = true;                                   //Enable SMTP authentication
                    $mail->Username   = 'user@example.com';                     //SMTP username
                    $mail->Password   = 'secret';                               //SMTP password
                    $mail->SMTPSecure = PHPMailer::ENCRYPTION_SMTPS;            //Enable implicit TLS encryption
                    $mail->Port       = 587;                                    //TCP port to connect to; use 587 if you have set `SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS`

                    //Recipients
                    $mail->setFrom('from@example.com', 'Mailer');
                    $mail->addAddress($email);     //Add a recipient
                    $mail->addReplyTo('info@example.com', 'Information');

                    //Content
                    $mail->isHTML(true);                                  //Set email format to HTML
                    $mail->Subject = 'Reset password : Light Novel Hako';
                    $mail->Body    = 'Your OTP is : ' . $otp . ' .';
                    $mail->AltBody = 'Reset password to access Reading Applicaction';


                    if ($mail->send()) {
                        $json['success'] = true;
                        $json['message'] = "Successfully sent";
                    } else {
                        $json['success'] = false;
                        $json['message'] = "Error. Probably the email does not exist";
                    };
                } catch (Exception $e) {
                    $json['success'] = false;
                    $json['message'] = "Error. False to sent OTP throught mail";
                }
            } else {

                $json['success'] = false;
                $json['message'] = "Error. Can't connect database";
            }

            mysqli_close($this->db->getDb());
        }
        return $json;
    }

    public function resetpass($email, $otp, $newpass)
    {
        $isValid = $this->isValidEmail($email);
        if ($isValid) {
            try {
                $otp = random_int(10000, 99999);
            } catch (Exception $e) {
                $otp = rand(10000, 99999);
            }
            $query = "update " . $this->db_table . " set pass = '" . $newpass . "' , otpreset = '' where Email = '" . $email . "' and otpreset = '" . $otp . "'";
            $upd = mysqli_query($this->db->getDb(), $query);
            if ($upd == true) {
                $json['success'] = true;
                $json['message'] = "Successfully sent";
            } else {
                $json['success'] = false;
                $json['message'] = "Error. Probably the email does not exist";
            }
            mysqli_close($this->db->getDb());
        }
        return $json;
    }

    public function getname($username)
    {
        $query = "SELECT * FROM " . $this->db_table . " WHERE `Useracc`='$username' limit 1";
        $result = mysqli_query($this->db->getDb(), $query);
        if (mysqli_num_rows($result) > 0) {
            $json['check'] = true;
        } else $json['check'] = false;
        mysqli_close($this->db->getDb());
        return $json;
    }
    public function changepass($username, $password)
    {
        if ($this->checkPasswordFormat($password)) {
            $query = "UPDATE " . $this->db_table . " SET `pass`='$password' WHERE `Useracc` = '$username'";
            $update = mysqli_query($this->db->getDb(), $query);
            if ($update  == true) {
                $json['success'] = true;
                $json['message'] = "update successful";
            } else {
                $json['success'] = false;
                $json['message'] = "update error";
            }
        } else {
            $json['success'] = false;
            $json['message'] = "Mật khẩu phải có ít nhất 8 ký tự\nMật khẩu phải chứa ít nhất một chữ cái viết hoa\n Mật khẩu phải chứa ít nhất một chữ cái viết thường\n Mật khẩu phải chứa ít nhất một số\n Mật khẩu có thể chứa các ký tự đặc biệt như !@#$%^&*";
        }
        mysqli_close($this->db->getDb());
        return $json;
    }
}
