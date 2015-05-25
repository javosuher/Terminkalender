<?php

namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;

class Chat implements MessageComponentInterface {
	const POINTSPLIT = ":";
	const MESSAGE = "Message";
	const USERSROOM = "UsersRoom";

    protected $clients;

    public function __construct() {
        $this->clients = new \SplObjectStorage;
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

        //storeInDataBase($msg);
    }

    private function sendMessage(ConnectionInterface $from, $msg) {
    	$numRecv = count($this->clients) - 1;
        echo sprintf('Connection %d sending message "%s" to %d other connection%s' . "\n"
            , $from->resourceId, $msg, $numRecv, $numRecv == 1 ? '' : 's');

        foreach ($this->clients as $client) {
            if ($from !== $client) {
                $client->send($msg); // The sender is not the receiver, send to each client connected
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

    private function storeInDataBase($msg) {
    	include("config.php"); // Load Database
        try {
			$sql = $dbh -> prepare("INSERT INTO messages (msg) VALUES (?)");
			$sql -> execute(array($msg));
    	} catch(PDOException $e) {
    		echo $sql . "<br>" . $e->getMessage();
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
}