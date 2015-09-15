<?php

namespace MyApp;

class Chat {
    const CHATSPLIT = "=";
    const MESSAGESPLIT = ";";

    protected $user1, $user2, $messages;

    public function __construct($userOne, $userTwo) {
        $this->user1 = $userOne;
        $this->user2 = $userTwo;
        $this->messages = array();
    }

    public function addMessage($userDestination, $message) {
        if($userDestination == $this->user1) {
            array_push($this->messages, $this->user2 . Chat::CHATSPLIT . $message);
        }
        else if($userDestination == $this->user2) {
            array_push($this->messages, $this->user1 . Chat::CHATSPLIT . $message);
        }
        else echo sprintf('No "%s" in Chat' . "\n", $userDestination);
        //print_r($this->messages);
        //echo "\n";
    }

    public function getMessagesUser($user) {
        $conversation = "";
        if($user == $this->user1 || $user == $this->user2) {
            foreach($this->messages as $message) {
                $conversation = $conversation . $message . Chat::MESSAGESPLIT;
            }
        }
        return $conversation;
    }

    public function pickUpChat() {
        $conversation = "";
        foreach($this->messages as $message) {
            $user = explode(Chat::CHATSPLIT, $message)[0];
            $trueMessage = explode(Chat::CHATSPLIT, $message)[1];
            $conversation = $conversation . $user . ": " . $trueMessage . "\n";
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
    public function getMessages() {
        return $this->messages;
    }
    public function getMessagesSize() {
        return count($this->messages);
    }
}

?>