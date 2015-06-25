<?php

namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;

class Chat implements MessageComponentInterface {
	const POINTSPLIT = ":";
	const MESSAGE = "Message";
	const USERSROOM = "UsersRoom";
	const LOGINTEACHER = "LoginTeacher";
	const REGISTERTEACHER = "RegisterTeacher";
	
    protected $clients, $dataBase;

    public function __construct() {
    	include_once("config.php"); // Include database config file

        $this->clients = new \SplObjectStorage;
        $this->dataBase = $dbh;
        echo "Init Chat Server!\n";
    }

    public function onOpen(ConnectionInterface $conn) {
        // Store the new connection to send messages to later
        $this->clients->attach($conn);
        echo "New connection! ({$conn->resourceId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg) {
        $action = explode(Chat::POINTSPLIT, $msg)[0];

        if(strcmp($action, Chat::MESSAGE) == 0) {
        	$this->sendMessage($from, $msg);
        }
        else if(strcmp($action, Chat::USERSROOM) == 0) {
        	$this->sendUsers($from);
        }
        else if(strcmp($action, Chat::LOGINTEACHER) == 0) {
        	$this->loginTeacher($from, $msg);
        }
        else if(strcmp($action, Chat::REGISTERTEACHER) == 0) {
        	$this->registerTeacher($from, $msg);
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

    // ------------------------------- Conections Functions ------------------------------- 

    private function sendMessage(ConnectionInterface $from, $msg) {
    	$action = explode(Chat::POINTSPLIT, $msg)[0];
    	$user = explode(Chat::POINTSPLIT, $msg)[1];
    	$message = substr($msg, strlen($action) + strlen($user) + 2);
        echo sprintf('Connection %d sending message "%s" to %d' . "\n", $from->resourceId, $message, $user);

        foreach($this->clients as $client) {
            if ($user == $client->resourceId) {
            	$trueMessage = $action . Chat::POINTSPLIT . $from->resourceId . Chat::POINTSPLIT . $message;
                $client->send($trueMessage);
                break;
            }
        }
    }
    private function sendUsers(ConnectionInterface $from) {
    	echo sprintf('Connection %d need users' . "\n", $from->resourceId);

    	$msg = Chat::USERSROOM . Chat::POINTSPLIT;
    	foreach ($this->clients as $client) {
    		if ($from !== $client) {
            	$msg = $msg . $client->resourceId . Chat::POINTSPLIT;
            }
        }
        $from->send($msg);
    }
    private function loginTeacher(ConnectionInterface $from, $msg) {
    	$userTeacher = explode(Chat::POINTSPLIT, $msg)[1];
    	$passwordTeacher = explode(Chat::POINTSPLIT, $msg)[2];
    	echo sprintf('Connection %d want to login with user: "%s" and password: "%s"' . "\n", $from->resourceId, $userTeacher, $passwordTeacher);

    	$search = $this->searchTeacherInDataBase($userTeacher);
    	if(empty($search)) {
    		$trueMessage = CHAT::LOGINTEACHER . CHAT::POINTSPLIT . $userTeacher . CHAT::POINTSPLIT . "NoExist";
    		$from->send($trueMessage);
    		echo "Login: No exist" . "\n";
    	}
    	else if(strcmp($userTeacher, $search[0]["username"]) == 0) {
    		if(strcmp($passwordTeacher, $search[0]["password"]) == 0) {
    			$trueMessage = CHAT::LOGINTEACHER . CHAT::POINTSPLIT . $userTeacher . CHAT::POINTSPLIT . "Success";
    			$from->send($trueMessage);
    			echo "Login: Success" . "\n";
    		}
    		else {
    			$trueMessage = CHAT::LOGINTEACHER . CHAT::POINTSPLIT . $userTeacher . CHAT::POINTSPLIT . "WrongPassword";
    			$from->send($trueMessage);
    			echo "Login: Wrong Password" . "\n";
    		}
    	}
    	else echo "hello";
    }
    private function registerTeacher(ConnectionInterface $from, $msg) {
    	$userTeacher = explode(Chat::POINTSPLIT, $msg)[1];
    	$passwordTeacher = explode(Chat::POINTSPLIT, $msg)[2];
    	echo sprintf('Connection %d want to register with user: "%s" and password: "%s"' . "\n", $from->resourceId, $userTeacher, $passwordTeacher);

    	$search = $this->searchTeacherInDataBase($userTeacher);
    	if(empty($search)) {
    		$this->storeTeacherInDataBase($userTeacher, $passwordTeacher);
    		$trueMessage = CHAT::REGISTERTEACHER . CHAT::POINTSPLIT . $userTeacher . CHAT::POINTSPLIT . "Success";
    		$from->send($trueMessage);
    		echo "Register: Success" . "\n";
    	}
    	else {
    		$trueMessage = CHAT::REGISTERTEACHER . CHAT::POINTSPLIT . $userTeacher . CHAT::POINTSPLIT . "Failure";
    		$from->send($trueMessage);
    		echo "Register: Failure" . "\n";
    	}
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
}