<?php

namespace MyApp;

class Chat {
    const DATASPLIT = ";";

    protected $user1, $user2, $messages;

    public function __construct($userOne, $userTwo) {
        $this->user1 = $userOne;
        $this->user2 = $userTwo;
        $this->messages = Array();
    }

    public function addMessage($userDestination, $message) {
        if($userDestination == $this->user1) {
            array_push($this->messages, $this->user1 . Chat::DATASPLIT . $message);
        }
        else if($userDestination == $this->user2) {
            array_push($this->messages, $this->user2 . Chat::DATASPLIT . $message);
        }
        else echo sprintf('No "%s" in Chat' . "\n", $userDestination);
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
}

?>