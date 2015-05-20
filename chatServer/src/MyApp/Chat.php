<?php

namespace MyApp;
use Ratchet\MessageComponentInterface;
use Ratchet\ConnectionInterface;

class Chat implements MessageComponentInterface {
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
        $numRecv = count($this->clients) - 1;
        echo sprintf('Connection %d sending message "%s" to %d other connection%s' . "\n"
            , $from->resourceId, $msg, $numRecv, $numRecv == 1 ? '' : 's');

        foreach ($this->clients as $client) {
            if ($from !== $client) {
                $client->send($msg); // The sender is not the receiver, send to each client connected
            }
        }

        // Insert into the database
        include("config.php"); // Load Database
        try {
        	//$sql = "INSERT INTO messages(msg) VALUES(:msg)";
        	//$q = $dbh->prepare($sql);
        	//$q->execute($msg);
        	//$stmt = $dbh->prepare("INSERT INTO table(msg) VALUES(:msg)");
			//$stmt->execute(array(':msg' => $msg));
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