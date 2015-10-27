<?php

namespace MyApp;
use MyApp\Chat;
use MyApp\Calendar;

class Game {
    const TASKLIMITSPLIT = "-";
    const SPLIT = ",";

    protected $gameName, $teacher, $password, $tasks, $users, $chats, $calendars;

    public function __construct($gameName, $teacher, $password, $tasks, $users) {
        $this->gameName = $gameName;
        $this->teacher = $teacher;
        $this->password = $password;
        $this->tasks = array();
        $this->users = array();
        $this->chats = new \SplObjectStorage;
        $this->calendars = new \SplObjectStorage;

        $taksSplit = explode(Game::SPLIT, $tasks);
        foreach($taksSplit as $task) {
            $name = explode(Game::TASKLIMITSPLIT, $task)[0];
            $limit = explode(Game::TASKLIMITSPLIT, $task)[1];

            array_push($this->tasks, array("name"=>$name, "limit"=>$limit));
        }

        $usersSplit = explode(Game::SPLIT, $users);
        $userName = 0;
        foreach($usersSplit as $user) {
            array_push($this->users, array("name"=>$user, "userName"=>$userName, "id"=>"NoID"));
            ++$userName;
        }
    }

    public function isUserInGame($userName) {
        foreach($this->users as $user) {
            if ($user == $userName) {
                return true;
            }
        }
        return false;
    }
    public function enterInGame($name, $id) {
        foreach($this->users as &$user) {
            if($user["name"] == $name) {
                $user["id"] = $id;
                return true;
            }
        }
        return false;
    }

    public function getTaskNames() {
        $tasks = Array();
        foreach($this->tasks as $task) {
            array_push($tasks, $task["name"]);
        }
        return $tasks;
    }
    public function getTaskLimit($taskName) {
        foreach ($this->tasks as $task) {
            if($task["name"] == $taskName) {
                return $task["limit"];
            }
        }
        return "NoTask";
    }
    public function getStringTasks() {
        $tasksString = "";
        foreach ($this->tasks as $task) {
            $tasksString = $tasksString . $task["name"] . Game::TASKLIMITSPLIT . $task["limit"] . Game::SPLIT;
        }
        $tasksString = rtrim($tasksString, ",");
        return $tasksString;
    }

    public function getUserNames() {
        return $this->getUserFields("name");
    }
    public function getUserUserNames() {
        return $this->getUserFields("userName");
    }
    public function getUserID($userName) {
        foreach($this->users as $user) {
            if($user["name"] == $userName) {
                return $user["id"];
            }
        }
        return "NoID";
    }
    public function getStringUsers() {
        return $this->getStringUserFields("name");
    }
    public function getStringUserNames() {
        return $this->getStringUserFields("userName");
    }
    private function getUserFields($field) {
        $users = Array();
        foreach($this->users as $user) {
            array_push($users, $user[$field]);
        }
        return $users;
    }
    private function getStringUserFields($field) {
        $usersString = "";
        foreach ($this->users as $user) {
            $usersString = $usersString . $user[$field] . Game::SPLIT;
        }
        $usersString = rtrim($usersString, ",");
        return $usersString;
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
    public function getCalendars() {
        return $this->calendars;
    }
}

?>