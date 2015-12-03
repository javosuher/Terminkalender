<?php

namespace MyApp;

class Chat {
    const CHATSPLIT = "=";
    const MESSAGESPLIT = ";";

    protected $user1, $user2, $userRealName1, $userRealName2, $messages;

    public function __construct($userOne, $userTwo, $userReal1, $userReal2) {
        $this->user1 = $userOne;
        $this->user2 = $userTwo;
        $this->userRealName1 = $userReal1;
        $this->userRealName2 = $userReal2;
        $this->messages = array();
    }

    public function addMessage($userDestination, $message) {
        if($userDestination == $this->user1) {
            array_push($this->messages, array("time"=>date("H:i:s"), "message"=>$this->user2 . Chat::CHATSPLIT . $message));
        }
        else if($userDestination == $this->user2) {
            array_push($this->messages, array("time"=>date("H:i:s"), "message"=>$this->user1 . Chat::CHATSPLIT . $message));
        }
        else echo sprintf('No "%s" in Chat' . "\n", $userDestination);
    }

    public function getMessagesUser($user) {
        $conversation = "";
        if($user == $this->user1 || $user == $this->user2) {
            foreach($this->messages as $message) {
                $conversation = $conversation . $message["message"] . Chat::MESSAGESPLIT;
            }
        }
        return $conversation;
    }

    public function pickUpChat() {
        $conversation = "";
        foreach($this->messages as $message) {
            $user = explode(Chat::CHATSPLIT, $message["message"])[0];
            if($user == $this->user1) $user = $this->userRealName1;
            else $user = $this->userRealName2;
            $trueMessage = explode(Chat::CHATSPLIT, $message["message"])[1];
            $conversation = $conversation . $message["time"] . " > ". $user . ": " . $trueMessage . "\n";
        }
        return $conversation;
    }

    public function isUsersInChat($userOne, $userTwo) {
        return ($userOne == $this->user1 && $userTwo == $this->user2) || ($userOne == $this->user2 && $userTwo == $this->user1);
    }

    public function getUser1() {
        return $this->user1;
    }
    public function getUser2() {
        return $this->user2;
    }
    public function getUserRealName1() {
        return $this->userRealName1;
    }
    public function getUserRealName2() {
        return $this->userRealName2;
    }
    public function getMessages() {
        return $this->messages;
    }
    public function getMessagesSize() {
        return count($this->messages);
    }
}

?>