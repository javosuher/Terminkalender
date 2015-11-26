<?php

$host = "localhost"; // Hostname
$port = "3306"; // MySQL Port : Default : 3306
$user = "kalenderUser"; // Username
$pass = "1475"; //Password
$db   = "Terminkalender"; // Database Name

try {
	$dbh = new PDO('mysql:dbname=' . $db . ';host=' . $host . ';port=' . $port, $user, $pass);
	//echo "Connected successfully\n";

} catch (PDOException $e) { 
	echo "Connection failed: " . $e->getMessage(); 
}

?>