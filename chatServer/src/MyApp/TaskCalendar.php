<?php

namespace MyApp;

class TaskCalendar {
    const SPLIT = ",";

	protected $name, $numberPartners, $users, $usersTask;

	public function __construct($name, $numberPartners, $users) {
		$this->name = $name;
		$this->numberPartners = $numberPartners - 1;
        $this->users = $users;
		$this->usersTask = array();
	}

	public function addUserData($userUserName, $userRealName, $location, $position, $partners) {
        if(!empty($this->usersTask)) {
            foreach($this->usersTask as &$userTask) {
                if($userTask["userName"] == $userUserName) {
                    $userTask["location"] = $location;
                    $userTask["position"] = $this->stringPositionToPosition($position);
                    $userTask["partners"] = $this->stringPartnersToPartners($partners);
                    $userTask["time"] = date("H:i:s");
                    return true;
                }
            }
        }
        array_push($this->usersTask, array("userName"=>$userUserName, "userRealName"=>$userRealName, "location"=>$location, "position"=>$this->stringPositionToPosition($position), "partners"=>$this->stringPartnersToPartners($partners), "time"=>date("H:i:s")));
        return false;
	}
    private function stringPositionToPosition($position) {
        $positionSplit = explode(TaskCalendar::SPLIT, $position);
        $positionArray = array("x"=>$positionSplit[0], "y"=>$positionSplit[1]);
        return $positionArray;
    }
    private function stringPartnersToPartners($partners) {
        $users = explode(TaskCalendar::SPLIT, $partners);
        if($users[0] !== "") {
            return $users;
        }
        else return array();
    }

    public function getUserTaskDataByUserName($userName) {
        return $this->getUserTaskData($userName, "userName");
    }
    public function getUserTaskDataByRealName($userRealName) {
        return $this->getUserTaskData($userRealName, "userRealName");
    }
    private function getUserTaskData($user, $field) {
        foreach($this->usersTask as $userTask) {
            if($userTask[$field] == $user) {
                return $userTask;
            }
        }
        return "NotFound";
    }

    public function partnersToString($partners) {
        if(!empty($partners)) {
            return $this->partnersToStringSeparator($partners, ",", false);
        }
        else return "";
    }

    public function pickUpTaskCalendar() {
        $data = "";
        foreach($this->usersTask as $userTask) {
            $data = $data . "-----> " . $userTask["userRealName"] . "\n" . "Location: " . $userTask["location"] . "\n" . "Position: " . $userTask["position"]["x"] . ", " . $userTask["position"]["y"] . "\n";
            if($this->numberPartners > 0) {
               $data = $data  . "Partners: " . $this->pickUpPartnersToString($userTask["partners"]) . "\n";
            }
            $data = $data . "Time: " . $userTask["time"] . "\n";
        }
        return $data;
    }
    private function pickUpPartnersToString($partners) {
        return $this->partnersToStringSeparator($partners, ", ", true);
    }
    private function partnersToStringSeparator($partners, $separator, $realName) {
        $usersString = "";
        foreach ($partners as $partner) {
            if($realName) {
                $usersString = $usersString . $this->getUserRealName($partner) . $separator;
            }
            else {
                $usersString = $usersString . $partner . $separator;
            }
        }
        $usersString = rtrim($usersString, $separator);
        return $usersString;
    }
    private function getUserRealName($userName) {
        foreach($this->users as $user) {
            if($user["userName"] == $userName) {
                return $user["name"];
            }
        }
        return "NotFound";
    }

	public function getName() {
        return $this->name;
    }
    public function getNumberPartners() {
        return $this->numberPartners;
    }
    public function getuserTask() {
        return $this->usersTask;
    }
}

?>