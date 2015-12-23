<?php

namespace MyApp;
use \PDO;

class DataBase {
	protected $host, $port, $user, $pass, $dbName, $dataBase;

	public function __construct() {
        $this->host = "localhost"; // Hostname
		$this->port = "3306"; // MySQL Port : Default : 3306
		$this->user = "kalenderUser"; // Username
		$this->pass = "1475"; //Password
		$this->dbName = "Terminkalender"; // Database Name
        
		$this->connect();
    }

    private function connect() {
        try {
            $this->dataBase = new PDO('mysql:dbname=' . $this->dbName . ';host=' . $this->host . ';port=' . $this->port, $this->user, $this->pass);

        } catch (PDOException $e) { 
            echo "Connection failed: " . $e->getMessage(); 
        }
    }
    private function reconnect() {
        $this->dataBase = null;
        $this->connect();
    }

    public function checkDataBaseConnection() {
        if(empty($this->searchTeachersInDataBase())) {
            echo "Data Base Error!! Reconnect!\n";
            $this->reconnect();
        }
    }

    // ------------------------------- Data Base Functions -------------------------------

    public function searchTeachersInDataBase() {
        try {
            $sql = $this->dataBase->prepare("SELECT username FROM teachers");
            $sql->execute();
            $result = $sql->fetchAll();
            //echo "DataBase: searchTeachersInDataBase ----> ";
            //print_r($result);
            return $result;
        } catch(PDOException $e) {
            echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
        }
    }
    public function searchTeacherInDataBase($userTeacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM teachers WHERE username =:username");
    		$sql->bindValue(":username", $userTeacher);
    		$sql->execute();
 			$result = $sql->fetchAll();
            //echo "DataBase: searchTeacherInDataBase by $userTeacher ----> ";
            //print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function storeTeacherInDataBase($userTeacher, $passwordTeacher) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO teachers (username, password) VALUES (?, ?)");
 			$sql -> execute(array($userTeacher, $passwordTeacher));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function searchGamesInDataBase($teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE teacher =:teacher");
    		$sql->execute(array(":teacher" => $teacher));
 			$result = $sql->fetchAll();
            //echo "DataBase: searchGamesInDataBase ----> ";
            //print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function searchGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$result = $sql->fetchAll();
            //echo "DataBase: searchGameInDataBase ----> ";
            //print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function deleteGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("DELETE FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$sql->fetchAll();
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function storeNewGameInDataBase($gameName, $teacher, $password) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO games (name, teacher, password, tasks, users) VALUES (?, ?, ?, ?, ?)");
 			$sql -> execute(array($gameName, $teacher, $password, "", ""));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
    public function updateGameInDataBase($gameName, $teacher, $password, $tasks, $users) {
        try {
			$sql = $this->dataBase->prepare("UPDATE games 
				SET name =:gameName, teacher =:teacher, password =:password, tasks =:tasks, users =:users 
				WHERE name =:gameName AND teacher =:teacher");
 			$sql -> execute(array(":gameName" => $gameName, ":teacher" => $teacher, ":password" => $password, ":tasks" => $tasks, ":users" => $users));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
            $this->reconnect();
    	}
    }
}

?>