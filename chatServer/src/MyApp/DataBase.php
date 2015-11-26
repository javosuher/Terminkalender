<?php

namespace MyApp;
use \PDO;

class DataBase {
	protected $dataBase;

	public function __construct() {
        $host = "localhost"; // Hostname
		$port = "3306"; // MySQL Port : Default : 3306
		$user = "kalenderUser"; // Username
		$pass = "1475"; //Password
		$dbName = "Terminkalender"; // Database Name

		try {
			$this->dataBase = new PDO('mysql:dbname=' . $dbName . ';host=' . $host . ';port=' . $port, $user, $pass);

		} catch (PDOException $e) { 
			echo "Connection failed: " . $e->getMessage(); 
		}
    }

        // ------------------------------- Data Base Functions -------------------------------

    public function searchTeachersInDataBase() {
        try {
            $sql = $this->dataBase->prepare("SELECT username FROM teachers");
            $sql->execute();
            $result = $sql->fetchAll();
            return $result;
        } catch(PDOException $e) {
            echo $sql . "<br>" . $e->getMessage();
        }
    }
    public function searchTeacherInDataBase($userTeacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM teachers WHERE username =:username");
    		$sql->bindValue(":username", $userTeacher);
    		$sql->execute();
 			$result = $sql->fetchAll();
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    public function storeTeacherInDataBase($userTeacher, $passwordTeacher) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO teachers (username, password) VALUES (?, ?)");
 			$sql -> execute(array($userTeacher, $passwordTeacher));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    public function searchGamesInDataBase($teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE teacher =:teacher");
    		$sql->execute(array(":teacher" => $teacher));
 			$result = $sql->fetchAll();
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    public function searchGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$result = $sql->fetchAll();
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    public function deleteGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("DELETE FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$sql->fetchAll();
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    public function storeNewGameInDataBase($gameName, $teacher, $password) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO games (name, teacher, password, tasks, users) VALUES (?, ?, ?, ?, ?)");
 			$sql -> execute(array($gameName, $teacher, $password, "", ""));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
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
    	}
    }
}

?>