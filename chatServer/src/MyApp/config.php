<?php

$host = "localhost"; // Hostname
$port = "3306"; // MySQL Port : Default : 3306
$user = "chatUser"; // Username Here
$pass = "1475"; //Password Here
$db   = "Chat"; // Database Name

try {
	$dbh  = new PDO('mysql:dbname=' . $db . ';host=' . $host . ';port=' . $port, $user, $pass);
	//echo "Connected successfully\n";

} catch (PDOException $e) { 
	echo "Connection failed: " . $e->getMessage(); 
}

?>