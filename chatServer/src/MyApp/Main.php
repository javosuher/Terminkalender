<?php

namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;
use MyApp\Game;

class Main implements MessageComponentInterface {
	const POINTSPLIT = ":";
	const DATASPLIT = ";";
	const LOGIN = "Login";
    const ENTERGAME = "EnterGame";
	const MESSAGE = "Message";
	const USERSROOM = "UsersRoom";
	const LOGINTEACHER = "LoginTeacher";
	const REGISTERTEACHER = "RegisterTeacher";
	const CREATEGAME = "CreateGame";
	const UPDATEGAME = "UpdateGame";
	const GAMES = "Games";
	const OPENGAMES = "OpenGames";
	
    protected $clients, $dataBase, $games;

    public function __construct() {
    	include_once("Config.php"); // Include database config file

        $this->clients = new \SplObjectStorage;
        $this->dataBase = $dbh;
        $this->games = new \SplObjectStorage;
        $this->token = sem_get(0);
        echo "Init Server!\n";

        $this->games->attach(new Game("dodo", "sandra", "f", "zoo,beber,aletear")); // Example OpenGame
    }

    public function onOpen(ConnectionInterface $conn) {
        // Store the new connection to send messages to later
        $this->clients->attach($conn);
        echo "New connection! ({$conn->resourceId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg) {
        $action = explode(Main::POINTSPLIT, $msg)[0];

        if(strcmp($action, Main::LOGIN) == 0) {
            $this->login($from, $msg);
        }
        else if(strcmp($action, Main::ENTERGAME) == 0) {
            $this->enterGame($from, $msg);
        }
        else if(strcmp($action, Main::MESSAGE) == 0) {
        	$this->sendMessage($from, $msg);
        }
        else if(strcmp($action, Main::USERSROOM) == 0) {
        	$this->sendUsers($from, $msg);
        }
        else if(strcmp($action, Main::LOGINTEACHER) == 0) {
        	$this->loginTeacher($from, $msg);
        }
        else if(strcmp($action, Main::REGISTERTEACHER) == 0) {
        	$this->registerTeacher($from, $msg);
        }
        else if(strcmp($action, Main::CREATEGAME) == 0) {
        	$this->createGame($from, $msg);
        }
        else if(strcmp($action, Main::UPDATEGAME) == 0) {
        	$this->updateGame($from, $msg);
        }
        else if(strcmp($action, Main::GAMES) == 0) {
        	$this->sendGamesTeacher($from, $msg);
        }
        else if(strcmp($action, Main::OPENGAMES) == 0) {
        	$this->openGame($from, $msg);
        }
    }

    public function onClose(ConnectionInterface $conn) {
        // The connection is closed, remove it, as we can no longer send it messages
        $this->clients->detach($conn);
        echo "Connection {$conn->resourceId} has disconnected\n";
    }

    public function onError(ConnectionInterface $conn, \Exception $e) {
        echo "An error has occurred: {$e->getMessage()}\n";
        $conn->close();
    }

    // ------------------------------- Client Functions ------------------------------- 

    private function login(ConnectionInterface $from, $msg) {
    	$teacher = explode(Main::POINTSPLIT, $msg)[1];
        echo sprintf('Connection %d want Open Games from teacher: "%s"' . "\n", $from->resourceId, $teacher);

        $openGames = $this->searchOpenGames($teacher);
        $trueMessage = Main::LOGIN . Main::POINTSPLIT;
        foreach($openGames as $game) {
            $trueMessage = $trueMessage . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::POINTSPLIT;
        }
        $from->send($trueMessage);
    }
    private function enterGame(ConnectionInterface $from, $msg) {
        $userName = explode(Main::POINTSPLIT, $msg)[1];
        $teacher = explode(Main::POINTSPLIT, $msg)[2];
        $gameName = explode(Main::POINTSPLIT, $msg)[3];
        $password = explode(Main::POINTSPLIT, $msg)[4];
        echo sprintf('Connection %d want enter the "%s" game "%s" with password "%s" and name "%s"' . "\n", $from->resourceId, $teacher, $gameName, $password, $userName);

        $game = $this->getOpenGame($teacher, $gameName);

        if($game->getPassword() == $password) {
            $game->addUser($userName, $from->resourceId);

            echo "Enter Game: Success" . "\n";
            $message = Main::ENTERGAME . Main::POINTSPLIT . $gameName;
            $from->send($message);
        }
        else {
            echo "Enter Game: Wrong password" . "\n";
            $message = Main::ENTERGAME . Main::POINTSPLIT . "WrongPassword";
            $from->send($message);
        }
    }
    private function sendMessage(ConnectionInterface $from, $msg) {
        $userSender = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
    	$teacher = explode(Main::POINTSPLIT, $msg)[3];
    	$userDestination = explode(Main::POINTSPLIT, $msg)[4];
        $messageStart = strlen(Main::MESSAGE) + strlen($userSender) + strlen($gameName) + strlen($teacher) + strlen($userDestination) + 5;
    	$message = substr($msg, $messageStart);
        echo sprintf('"%s" sending message "%s" to "%s"' . "\n", $userSender, $message, $userDestination);

        $game = $this->getOpenGame($teacher, $gameName);
        $game->addMessage($userSender, $userDestination, $message);
        $userID = $game->getUserID($userDestination);

        foreach($this->clients as $client) {
            if ($userID == $client->resourceId) {
            	$trueMessage = Main::MESSAGE . Main::POINTSPLIT . $userSender . Main::POINTSPLIT . $message;
                $client->send($trueMessage);
                return true;
            }
        }
    }
    private function sendUsers(ConnectionInterface $from, $msg) {
        $name = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
        $teacher = explode(Main::POINTSPLIT, $msg)[3];
    	echo sprintf('"%s" need users' . "\n", $name);

        $game = $this->getOpenGame($teacher, $gameName);
        $users = $game->getUserNames();

    	$message = Main::USERSROOM . Main::POINTSPLIT;
    	foreach ($users as $user) {
    		if ($user !== $name) {
            	$message = $message . $user . Main::POINTSPLIT;
            }
        }
        $from->send($message);
    }

    // ------------------------------- Teacher Functions ------------------------------- 

    private function loginTeacher(ConnectionInterface $from, $msg) {
    	$userTeacher = explode(Main::POINTSPLIT, $msg)[1];
    	$passwordTeacher = explode(Main::POINTSPLIT, $msg)[2];
    	echo sprintf('Connection %d want to login with user: "%s" and password: "%s"' . "\n", $from->resourceId, $userTeacher, $passwordTeacher);

    	$search = $this->searchTeacherInDataBase($userTeacher);
    	if(empty($search)) {
    		$trueMessage = Main::LOGINTEACHER . Main::POINTSPLIT . $userTeacher . Main::POINTSPLIT . "NoExist";
    		$from->send($trueMessage);
    		echo "Login: No exist" . "\n";
    	}
    	else if(strcmp($userTeacher, $search[0]["username"]) == 0) {
    		if(strcmp($passwordTeacher, $search[0]["password"]) == 0) {
    			$trueMessage = Main::LOGINTEACHER . Main::POINTSPLIT . $userTeacher . Main::POINTSPLIT . "Success";
    			$from->send($trueMessage);
    			echo "Login: Success" . "\n";
    		}
    		else {
    			$trueMessage = Main::LOGINTEACHER . Main::POINTSPLIT . $userTeacher . Main::POINTSPLIT . "WrongPassword";
    			$from->send($trueMessage);
    			echo "Login: Wrong Password" . "\n";
    		}
    	}
    }
    private function registerTeacher(ConnectionInterface $from, $msg) {
    	$userTeacher = explode(Main::POINTSPLIT, $msg)[1];
    	$passwordTeacher = explode(Main::POINTSPLIT, $msg)[2];
    	echo sprintf('Connection %d want to register with user: "%s" and password: "%s"' . "\n", $from->resourceId, $userTeacher, $passwordTeacher);

    	$search = $this->searchTeacherInDataBase($userTeacher);
    	if(empty($search)) {
    		$this->storeTeacherInDataBase($userTeacher, $passwordTeacher);
    		$trueMessage = Main::REGISTERTEACHER . Main::POINTSPLIT . $userTeacher . Main::POINTSPLIT . "Success";
    		$from->send($trueMessage);
    		echo "Register: Success" . "\n";
    	}
    	else {
    		$trueMessage = Main::REGISTERTEACHER . Main::POINTSPLIT . $userTeacher . Main::POINTSPLIT . "Failure";
    		$from->send($trueMessage);
    		echo "Register: Failure" . "\n";
    	}
    }
    private function createGame(ConnectionInterface $from, $msg) {
    	$gameName = explode(Main::POINTSPLIT, $msg)[1];
    	$teacher = explode(Main::POINTSPLIT, $msg)[2];
    	$password = explode(Main::POINTSPLIT, $msg)[3];
    	echo sprintf('Connection %d want to create a game: "%s" from teacher: "%s" and password: "%s"' . "\n", $from->resourceId, $gameName, $teacher, $password);

    	$search = $this->searchGameInDataBase($gameName, $teacher);
    	if(empty($search)) {
    		$this->storeNewGameInDataBase($gameName, $teacher, $password);
    		$trueMessage = Main::CREATEGAME . Main::POINTSPLIT . $gameName . Main::POINTSPLIT . "Success";
    		$from->send($trueMessage);
    		echo "Create Game: Success" . "\n";
    	}
    	else {
    		$trueMessage = Main::CREATEGAME . Main::POINTSPLIT . $gameName . Main::POINTSPLIT . "Failure";
    		$from->send($trueMessage);
    		echo "Create Game: Failure" . "\n";
    	}
    }
    private function updateGame(ConnectionInterface $from, $msg) {
    	$gameName = explode(Main::POINTSPLIT, $msg)[1];
    	$teacher = explode(Main::POINTSPLIT, $msg)[2];
    	$password = explode(Main::POINTSPLIT, $msg)[3];
    	$tasks = explode(Main::POINTSPLIT, $msg)[4];
    	echo sprintf('Connection %d want to update a game: "%s" from teacher: "%s" password: "%s" and tasks: %s' . "\n", $from->resourceId, $gameName, $teacher, $password, $tasks);

    	$this->updateGameInDataBase($gameName, $teacher, $password, $tasks);
    	$message = Main::UPDATEGAME . Main::POINTSPLIT;
    	$from->send($message);
    	echo "update Game: Success" . "\n";
    }
    private function sendGamesTeacher(ConnectionInterface $from, $msg) {
    	$teacher = explode(Main::POINTSPLIT, $msg)[1];
    	echo sprintf('Connection %d from teacher: "%s" need games' . "\n", $from->resourceId, $teacher);

    	$gamesDB = $this->searchGamesInDataBase($teacher);
    	$openGames = $this->searchOpenGames($teacher);

    	$msg = Main::GAMES . Main::POINTSPLIT;
    	foreach ($gamesDB as $game) {
            $msg = $msg . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::POINTSPLIT;
        }
        $msg = $msg . Main::OPENGAMES . Main::POINTSPLIT;
        foreach ($openGames as $game) {
            $msg = $msg . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::POINTSPLIT;
        }
    	$from->send($msg);
    }

    private function openGame(ConnectionInterface $from, $msg) {
    	$gameName = explode(Main::POINTSPLIT, $msg)[1];
    	$teacher = explode(Main::POINTSPLIT, $msg)[2];
    	$password = explode(Main::POINTSPLIT, $msg)[3];
    	$tasks = explode(Main::POINTSPLIT, $msg)[4];

    	$this->games->attach(new Game($gameName, $teacher, $password, $tasks));
    	$this->deleteGameInDataBase($gameName, $teacher);
    	$message = Main::GAMES . Main::POINTSPLIT . $teacher;
    	$this->sendGamesTeacher($from, $message);
    	$message = Main::OPENGAMES . Main::POINTSPLIT . $gameName;
    	echo "Open Game: Success" . "\n";
    	$from->send($message);
    }

    // ------------------------------- Open Games Functions -------------------------------

    private function searchOpenGames($teacher) {
    	$openGames = array();
    	foreach($this->games as $game) {
            if ($game->getTeacher() == $teacher) {
            	$name = $game->getGameName();
            	$password = $game->getPassword();
            	$tasks = $game->getTasks();
            	array_push($openGames, array("name"=>$name, "password"=>$password, "tasks"=>$tasks));
            }
        }
        //print_r($openGames);
        return $openGames;
    }
    private function getOpenGame($teacher, $gameName) {
        foreach($this->games as $game) {
            if ($game->getTeacher() == $teacher && $game->getGameName() == $gameName) {
                return $game;
            }
        }
        return "Empty";
    }

    // ------------------------------- Data Base Functions -------------------------------

    private function searchTeacherInDataBase($userTeacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM teachers WHERE username =:username");
    		$sql->bindValue(":username", $userTeacher);
    		$sql->execute();
 			$result = $sql->fetchAll();
 			//print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function storeTeacherInDataBase($userTeacher, $passwordTeacher) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO teachers (username, password) VALUES (?, ?)");
 			$sql -> execute(array($userTeacher, $passwordTeacher));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function searchGamesInDataBase($teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE teacher =:teacher");
    		$sql->execute(array(":teacher" => $teacher));
 			$result = $sql->fetchAll();
 			//print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function searchGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("SELECT * FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$result = $sql->fetchAll();
 			//print_r($result);
 			return $result;
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function deleteGameInDataBase($gameName, $teacher) {
    	try {
    		$sql = $this->dataBase->prepare("DELETE FROM games WHERE name =:gameName AND teacher =:teacher");
    		$sql->execute(array(":gameName" => $gameName, ":teacher" => $teacher));
 			$sql->fetchAll();
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function storeNewGameInDataBase($gameName, $teacher, $password) {
        try {
			$sql = $this->dataBase->prepare("INSERT INTO games (name, teacher, password, tasks) VALUES (?, ?, ?, ?)");
 			$sql -> execute(array($gameName, $teacher, $password, ""));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
    private function updateGameInDataBase($gameName, $teacher, $password, $tasks) {
        try {
			$sql = $this->dataBase->prepare("UPDATE games 
				SET name =:gameName, teacher =:teacher, password =:password, tasks =:tasks 
				WHERE name =:gameName AND teacher =:teacher");
 			$sql -> execute(array(":gameName" => $gameName, ":teacher" => $teacher, ":password" => $password, ":tasks" => $tasks));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
    	}
    }
}

?>