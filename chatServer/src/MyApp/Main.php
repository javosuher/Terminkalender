<?php

namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;
use MyApp\Game;
use MyApp\DataBase;

class Main implements MessageComponentInterface {
	const POINTSPLIT = ":";
	const DATASPLIT = ";";
    const TASKLIMITSPLIT = "-";
    const TASKSPLIT = ",";

    const TEACHERS = "Teachers";
	const LOGIN = "Login";
    const ENTERGAME = "EnterGame";
	const MESSAGE = "Message";
	const USERSROOM = "UsersRoom";
    const CHAT = "Chat";
    const TASK = "Task";
    const TASKVALIDATE = "TaskValidate";
    const TASKS = "Tasks";

	const LOGINTEACHER = "LoginTeacher";
	const REGISTERTEACHER = "RegisterTeacher";
	const CREATEGAME = "CreateGame";
	const UPDATEGAME = "UpdateGame";
	const GAMES = "Games";
	const OPENGAMES = "OpenGames";
    const REMOVEGAMES = "RemoveGames";
    const CLOSEGAMES = "CloseGames";

    const NAME = "Name";
    const NAMEADUSERNAME = "NameAndUserName";
	
    protected $clients, $dataBase, $games, $semaphore;

    public function __construct() {
        $this->clients = new \SplObjectStorage;
        $this->games = new \SplObjectStorage;
        $this->dataBase = new DataBase();

        $key = 123321;
        $maxAcquire = 1;
        $permissions =0666;
        $autoRelease = 1;
        $this->semaphore = sem_get($key, $maxAcquire, $permissions, $autoRelease);

        echo "Init Server!\n";

        $this->games->attach(new Game("dodo", "sandra", "f", "zoo-2,beber-2,aletear-2,pescar-3,leer un libro-1,migrar-4,tomar un tentempie-2,jugar videojuegos-3,jugar al futbol-4,hacer la comida-1,estudiar-2", "juan,pepe,maria,andrés,perico,taquiato,pedro,camilo,afterwak,petanca,casimiro,pafer,hyeri,lontu.vetertu,calsd,fewjwd,sadkjda,dasjdja,das,dsa")); // Example OpenGame
        $this->games->attach(new Game("otro", "sandra", "f", "zoo-2,beber-2,aletear-2,pescar-3,leer un libro-1,migrar-4,tomar un tentempie-2,jugar videojuegos-3,jugar al futbol-4,hacer la comida-1,estudiar-2", "juan,pepe,maria,andrés,perico,taquiato,pedro,camilo,afterwak,petanca,casimiro,pafer,hyeri,lontu.vetertu,calsd,fewjwd,sadkjda,dasjdja,das,dsa")); // Example OpenGame
    }

    public function onOpen(ConnectionInterface $conn) {
        $this->clients->attach($conn);
        echo "New connection! ({$conn->resourceId})\n";
    }

    public function onMessage(ConnectionInterface $from, $msg) {
        $action = explode(Main::POINTSPLIT, $msg)[0];

        if(strcmp($action, Main::TEACHERS) == 0) {
            $this->sendTeachers($from, $msg);
        }
        else if(strcmp($action, Main::LOGIN) == 0) {
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
        else if(strcmp($action, Main::CHAT) == 0) {
            $this->sendChatConversation($from, $msg);
        }
        else if(strcmp($action, Main::TASK) == 0) {
            $this->addTaskCalendar($from, $msg);
        }
        else if(strcmp($action, Main::TASKVALIDATE) == 0) {
            $this->userValidateTask($from, $msg);
        }
        else if(strcmp($action, Main::TASKS) == 0) {
            $this->sendTaskCalendar($from, $msg);
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
        else if(strcmp($action, Main::REMOVEGAMES) == 0) {
            $this->removeGame($from, $msg);
        }
        else if(strcmp($action, Main::CLOSEGAMES) == 0) {
            $this->closeGame($from, $msg);
        }
    }

    public function onClose(ConnectionInterface $conn) {
        $this->clients->detach($conn);
        echo "Connection {$conn->resourceId} has disconnected\n";
    }

    public function onError(ConnectionInterface $conn, \Exception $e) {
        echo "An error has occurred: {$e->getMessage()}\n";
        $conn->close();
    }

    // ------------------------------- Client Functions ------------------------------- 

    private function sendTeachers(ConnectionInterface $from, $msg) {
        echo sprintf('Connection %d want teachers' . "\n", $from->resourceId);

        $teachers = $this->dataBase->searchTeachersInDataBase();
        $trueMessage = Main::TEACHERS . Main::POINTSPLIT;
        foreach($teachers as $teacher) {
            $trueMessage = $trueMessage . $teacher["username"] . Main::POINTSPLIT;
        }
        $from->send($trueMessage);

    }
    private function login(ConnectionInterface $from, $msg) {
    	$teacher = explode(Main::POINTSPLIT, $msg)[1];
        echo sprintf('Connection %d want Open Games from teacher: "%s"' . "\n", $from->resourceId, $teacher);

        $openGames = $this->searchOpenGames($teacher, Main::NAME);
        $trueMessage = Main::LOGIN . Main::POINTSPLIT;
        foreach($openGames as $game) {
            $trueMessage = $trueMessage . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::DATASPLIT . $game["users"] . Main::POINTSPLIT;
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
            sem_acquire($this->semaphore);
            $enter = $game->enterInGame($userName, $from);
            sem_release($this->semaphore);
            
            if($enter) {
                echo "Enter Game: Success" . "\n";
                $userUserName = $game->getUserUserName($userName);
                $message = Main::ENTERGAME . Main::POINTSPLIT . $gameName . Main::POINTSPLIT . $userUserName;
                $from->send($message);
            }
            else {
                echo "Enter Game: User not exist" . "\n";
                $message = Main::ENTERGAME . Main::POINTSPLIT . "UserNoExist";
                $from->send($message);
            }
        }
        else {
            echo "Enter Game: Wrong password" . "\n";
            $message = Main::ENTERGAME . Main::POINTSPLIT . "WrongPassword";
            $from->send($message);
        }

        $this->sendTaskCalendar($from, $msg);
    }
    private function sendUsers(ConnectionInterface $from, $msg) {
        $name = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
        $teacher = explode(Main::POINTSPLIT, $msg)[3];
    	echo sprintf('"%s" need users' . "\n", $name);

        $game = $this->getOpenGame($teacher, $gameName);
        $users = $game->getUserUserNames();

    	$message = Main::USERSROOM . Main::POINTSPLIT;
    	foreach ($users as $user) {
    		if ($user !== $name) {
            	$message = $message . $user . Main::POINTSPLIT;
            }
        }
        $from->send($message);
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
        if($game !== "Empty") {
            sem_acquire($this->semaphore);
            $game->addMessage($userSender, $userDestination, $message);
            $userID = $game->getUserID2($userDestination);
            sem_release($this->semaphore);

            if($userID !== "NoID") {
                $trueMessage = Main::MESSAGE . Main::POINTSPLIT . $userSender . Main::POINTSPLIT .  $message;
                $userID->send($trueMessage);
            }
        }
        else {
            $message = Main::CLOSEGAMES . Main::POINTSPLIT;
            $from->send($message);
        }
    }
    private function sendChatConversation(ConnectionInterface $from, $msg) {
        $name = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
        $teacher = explode(Main::POINTSPLIT, $msg)[3];
        $name2 = explode(Main::POINTSPLIT, $msg)[4];
        $messagesSize = explode(Main::POINTSPLIT, $msg)[5];
        echo sprintf('"%s" search messages from "%s" chat' . "\n", $name, $name2);

        $game = $this->getOpenGame($teacher, $gameName);
        if($game !== "Empty") {
            $conversation = $game->getChatFromUsers($name, $name2, $messagesSize);

            if($conversation !== "") {
                $message = Main::CHAT . Main::POINTSPLIT . $name2 . Main::POINTSPLIT . $conversation;
                $from->send($message);
            }
        }
        else {
            $message = Main::CLOSEGAMES . Main::POINTSPLIT;
            $from->send($message);
        }
    }
    private function addTaskCalendar(ConnectionInterface $from, $msg) {
        $userUserName = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
        $teacher = explode(Main::POINTSPLIT, $msg)[3];
        $description = explode(Main::POINTSPLIT, $msg)[4];
        $location = explode(Main::POINTSPLIT, $msg)[5];
        $position = explode(Main::POINTSPLIT, $msg)[6];
        $partners = explode(Main::POINTSPLIT, $msg)[7];
        echo sprintf('"%s" add Task Data: Description: "%s", Location: "%s", Position: "%s", Partners: "%s"' . "\n", $userUserName, $description, $location, $position, $partners);

        $game = $this->getOpenGame($teacher, $gameName);
        if($game !== "Empty") {
            sem_acquire($this->semaphore);
            $game->addTaskCalendar($description, $userUserName, $location, $position, $partners);
            sem_release($this->semaphore);
        }
        else {
            $message = Main::CLOSEGAMES . Main::POINTSPLIT;
            $from->send($message);
        }
    }
    private function sendTaskCalendar(ConnectionInterface $from, $msg) {
        $userName = explode(Main::POINTSPLIT, $msg)[1];
        $teacher = explode(Main::POINTSPLIT, $msg)[2];
        $gameName = explode(Main::POINTSPLIT, $msg)[3];
        echo sprintf('"%s" search Tasks Data' . "\n", $userName);

        $game = $this->getOpenGame($teacher, $gameName);
        if($game !== "Empty") {
            $tasks = $game->getTasksDataFromUser($userName);

            $message = Main::TASKS . Main::POINTSPLIT . $tasks;
            $from->send($message);
        }
        else {
            $message = Main::CLOSEGAMES . Main::POINTSPLIT;
            $from->send($message);
        }
    }
    private function userValidateTask(ConnectionInterface $from, $msg) {
        $userUserName = explode(Main::POINTSPLIT, $msg)[1];
        $gameName = explode(Main::POINTSPLIT, $msg)[2];
        $teacher = explode(Main::POINTSPLIT, $msg)[3];
        echo sprintf('"%s" want validate their Tasks' . "\n", $userUserName);

        $game = $this->getOpenGame($teacher, $gameName);
        if($game !== "Empty") {
            sem_acquire($this->semaphore);
            $tasks = $game->validateTasksDataFromUser($userUserName);
            sem_release($this->semaphore);

            echo "Validate Calendar: Success" . "\n";
            $message = Main::TASKVALIDATE . Main::POINTSPLIT . $tasks;
            $from->send($message);
        }
        else {
            $message = Main::CLOSEGAMES . Main::POINTSPLIT;
            $from->send($message);
        }
    }

    // ------------------------------- Teacher Functions ------------------------------- 

    private function loginTeacher(ConnectionInterface $from, $msg) {
    	$userTeacher = explode(Main::POINTSPLIT, $msg)[1];
    	$passwordTeacher = explode(Main::POINTSPLIT, $msg)[2];
    	echo sprintf('Connection %d want to login with user: "%s" and password: "%s"' . "\n", $from->resourceId, $userTeacher, $passwordTeacher);

    	$search = $this->dataBase->searchTeacherInDataBase($userTeacher);
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

    	$search = $this->dataBase->searchTeacherInDataBase($userTeacher);
    	if(empty($search)) {
    		$this->dataBase->storeTeacherInDataBase($userTeacher, $passwordTeacher);
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

    	$search = $this->dataBase->searchGameInDataBase($gameName, $teacher);
    	if(empty($search)) {
    		$this->dataBase->storeNewGameInDataBase($gameName, $teacher, $password);
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
        $users = explode(Main::POINTSPLIT, $msg)[5];
    	echo sprintf('Connection %d want to update a game: "%s" from teacher: "%s" password: "%s", tasks: %s and users: %s' . "\n", $from->resourceId, $gameName, $teacher, $password, $tasks, $users);

    	$this->dataBase->updateGameInDataBase($gameName, $teacher, $password, $tasks, $users);
    	$message = Main::UPDATEGAME . Main::POINTSPLIT;
    	$from->send($message);
    	echo "update Game: Success" . "\n";
    }
    private function sendGamesTeacher(ConnectionInterface $from, $msg) {
    	$teacher = explode(Main::POINTSPLIT, $msg)[1];
    	echo sprintf('Connection %d from teacher: "%s" need games' . "\n", $from->resourceId, $teacher);

    	$gamesDB = $this->dataBase->searchGamesInDataBase($teacher);
    	$openGames = $this->searchOpenGames($teacher, Main::NAMEADUSERNAME);

    	$msg = Main::GAMES . Main::POINTSPLIT;
    	foreach ($gamesDB as $game) {
            $msg = $msg . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::DATASPLIT . $game["users"] . Main::POINTSPLIT;
        }
        $msg = $msg . Main::OPENGAMES . Main::POINTSPLIT;
        foreach ($openGames as $game) {
            $msg = $msg . $game["name"] . Main::DATASPLIT . $game["password"] . Main::DATASPLIT . $game["tasks"] . Main::DATASPLIT . $game["users"] . Main::POINTSPLIT;
        }
    	$from->send($msg);
    }
    private function openGame(ConnectionInterface $from, $msg) {
    	$gameName = explode(Main::POINTSPLIT, $msg)[1];
    	$teacher = explode(Main::POINTSPLIT, $msg)[2];
    	$password = explode(Main::POINTSPLIT, $msg)[3];
    	$tasks = explode(Main::POINTSPLIT, $msg)[4];
        $users = explode(Main::POINTSPLIT, $msg)[5];

    	$this->games->attach(new Game($gameName, $teacher, $password, $tasks, $users));
    	$this->dataBase->deleteGameInDataBase($gameName, $teacher);
    	$message = Main::GAMES . Main::POINTSPLIT . $teacher;
    	$this->sendGamesTeacher($from, $message);
    	$message = Main::OPENGAMES . Main::POINTSPLIT . $gameName;
    	echo "Open Game: Success" . "\n";
    	$from->send($message);
    }
    private function removeGame(ConnectionInterface $from, $msg) {
        $gameName = explode(Main::POINTSPLIT, $msg)[1];
        $teacher = explode(Main::POINTSPLIT, $msg)[2];

        $this->dataBase->deleteGameInDataBase($gameName, $teacher);
        $message = Main::GAMES . Main::POINTSPLIT . $teacher;
        $this->sendGamesTeacher($from, $message);
        $message = Main::REMOVEGAMES . Main::POINTSPLIT . $gameName;
        echo "Remove Game: Success" . "\n";
        $from->send($message);
    }
    private function closeGame(ConnectionInterface $from, $msg) {
        $gameName = explode(Main::POINTSPLIT, $msg)[1];
        $teacher = explode(Main::POINTSPLIT, $msg)[2];

        $game = $this->getOpenGame($teacher, $gameName);
        $users = $game->getUsers();
        foreach ($users as $user) {
            if($user["id"] !== "NoID") {
                $message = Main::CLOSEGAMES . Main::POINTSPLIT;
                $user["id"]->send($message);
            }
        }

        $data = $this->pickUpOpenGameData($teacher, $gameName);
        sem_acquire($this->semaphore);
        $this->deleteOpenGame($teacher, $gameName);
        sem_release($this->semaphore);
        $message = Main::GAMES . Main::POINTSPLIT . $teacher;
        $this->sendGamesTeacher($from, $message);
        $message = Main::CLOSEGAMES . Main::POINTSPLIT . $gameName . Main::POINTSPLIT . $teacher . Main::POINTSPLIT . $data;
        echo "Close Game" . $gameName . ": Success" . "\n";
        $from->send($message);
    }

    // ------------------------------- Open Games Functions -------------------------------

    private function searchOpenGames($teacher, $typeUser) {
    	$openGames = array();
    	foreach($this->games as $game) {
            if ($game->getTeacher() == $teacher) {
            	$name = $game->getGameName();
            	$password = $game->getPassword();
            	$tasks = $game->getStringTasks();
                $users = "";
                if($typeUser == Main::NAME) {
                    $users = $game->getStringUserNames();
                }
                else if($typeUser == Main::NAMEADUSERNAME) {
                    $users = $game->getStringUserBothNames();
                }
            	array_push($openGames, array("name"=>$name, "password"=>$password, "tasks"=>$tasks, "users"=>$users));
            }
        }
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
    private function deleteOpenGame($teacher, $gameName) {
        foreach($this->games as $game) {
            if ($game->getTeacher() == $teacher && $game->getGameName() == $gameName) {
                $this->games->detach($game);
                return true;
            }
        }
        return false;
    }
    private function pickUpOpenGameData($teacher, $gameName) {
        $game = $this->getOpenGame($teacher, $gameName);
        $data = $game->pickUpData();
        echo $gameName . "closed" . "\n";
        return $data;
    }
}

?>