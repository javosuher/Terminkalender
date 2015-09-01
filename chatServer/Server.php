<?php

use Ratchet\Server\IoServer;
use Ratchet\Http\HttpServer;
use Ratchet\WebSocket\WsServer;
use MyApp\Main;

require 'vendor/autoload.php';

$server = IoServer::factory(new HttpServer(new WsServer(new Main())), 8080);
$server->run();

?>