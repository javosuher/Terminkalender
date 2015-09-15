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
        foreach($this->users as &$user) {
            if($user["name"] == $userName) {
                $user["id"] = $id;
                return "User Update";
            }
        }
        array_push($this->users, array("name"=>$userName, "id"=>$id));
        return "New User";
    }

    public function addMessage($userSender, $userDestination, $message) {
        foreach ($this->chats as $chat) {
            if($chat->isUsersInChat($userSender, $userDestination)) {
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

    public function getChatFromUsers($userOne, $userTwo, $messagesSize) {
        foreach($this->chats as $chat) {
            if($chat->isUsersInChat($userOne, $userTwo) && ($messagesSize < $chat->getMessagesSize())) {
                return $chat->getMessagesUser($userTwo);
            }
        }
        return "";
    }

    public function pickUpData() {
        $chatsConversation = "CHATS \n================================================= \n\n";
        foreach($this->chats as $chat) {
            $chatsConversation = $chatsConversation . "------------ " . $chat->getUser1() . " and " . $chat->getUser2() . " ------------ \n";
            $chatsConversation = $chatsConversation . $chat->pickUpChat() . "\n";
        }
        return $chatsConversation;
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