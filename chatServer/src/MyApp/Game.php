<?php

class Game {

    protected $gameName, $teacher, $password, $tasks, $users;

    public function __construct($gameName, $teacher, $password, $tasks) {
        $this->gameName = $gameName;
        $this->teacher = $teacher;
        $this->password = $password;
        $this->tasks = $tasks;
        $this->users = new \SplObjectStorage;
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
}

?>