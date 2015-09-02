<?php

namespace MyApp;
use MyApp\Chat;

class Game {

    protected $gameName, $teacher, $password, $tasks, $users, $chats;

    public function __construct($gameName, $teacher, $password, $tasks) {
        $this->gameName = $gameName;
        $this->teacher = $teacher;
        $this->password = $password;
        $this->tasks = $tasks;
        $this->users = array();
        $this->chats = new \SplObjectStorage;
    }

    public function isUserInGame($userName) {
        foreach($this->users as $user) {
            if ($user == $userName) {
                return true;
            }
        }
        return false;
    }
    public function addUser($userName, $id) {
        if(!$this->updateId($userName, $id)) {
            array_push($this->users, array("name"=>$userName, "id"=>$id));
        }
    }
    public function updateId($userName, $id) {
        foreach($this->users as $user) {
            if($user == $userName) {
                $user["id"] = $id;
                return true;
            }
        }
        return false;
    }

    public function addMessage($userSender, $userDestination, $message) {
        foreach ($this->chats as $chat) {
            if(($userSender == $chat->getUser1 && $userDestination == $chat->getUser2) || ($userSender == $chat->getUser2 && $userDestination == $chat->getUser1)) {
                $chat->addMessage($userDestination, $message);
                return true;
            }
        }
        $chat = new Chat($userSender, $userDestination);
        $chat->addMessage($userDestination, $message);
        $this->chats->attach($chat);
        return false;
    }

    public function getUserNames() {
        $userNames = Array();
        foreach($this->users as $user) {
            array_push($userNames, $user["name"]);
        }
        return $userNames;
    }
    public function getUserID($userName) {
        foreach($this->users as $user) {
            if($user["name"] == $userName) {
                return $user["id"];
            }
        }
        return "NoUser";
    }

    public function getChatsFromUser($user) {
        
    }

    public function getGameName() {
        return $this->gameName;
    }
    public function getTeacher() {
        return $this->teacher;
    }
    public function getPassword() {
        return $this->password;
    }
    public function getTasks() {
        return $this->tasks;
    }
    public function getUsers() {
        return $this->users;
    }
    public function getChats() {
        return $this->chats;
    }
}

?>