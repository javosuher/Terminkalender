<?php

namespace MyApp;

class TaskCalendar {
    const SPLIT = ",";

	protected $name, $numberPartners, $usersTask;

	public function __construct($name, $numberPartners) {
		$this->name = $name;
		$this->numberPartners = $numberPartners - 1;
		$this->usersTask = array();
	}

	public function addUserData($userUserName, $userRealName, $location, $position, $partners) {
        if(!empty($this->usersTask)) {
            foreach($this->usersTask as &$userTask) {
                if($userTask["userName"] == $userUserName) {
                    $userTask["location"] = $location;
                    $userTask["position"] = $this->stringPositionToPosition($position);
                    $userTask["partners"] = $this->stringPartnersToPartners($partners);
                    return true;
                }
            }
        }
        array_push($this->usersTask, array("userName"=>$userUserName, "userRealName"=>$userRealName, "location"=>$location, "position"=>$this->stringPositionToPosition($position), "partners"=>$this->stringPartnersToPartners($partners)));
        print_r($this->usersTask);
        return false;
	}
    private function stringPositionToPosition($position) {
        $positionSplit = explode(TaskCalendar::SPLIT, $position);
        $positionArray = array("x"=>$positionSplit[0], "y"=>$positionSplit[1]);
        return $positionArray;
    }
    private function stringPartnersToPartners($partners) {
        print_r($partners);
        return explode(TaskCalendar::SPLIT, $partners);
    }

    public function pickUpTaskCalendar() {
        $data = "";
        foreach($this->usersTask as $userTask) {
            $data = $data . "-----> " . $userTask["userRealName"] . "\n" . "Location: " . $userTask["location"] . "\n" . "Position: " . $userTask["position"]["x"] . ", " . $userTask["position"]["y"] . "\n" . "Partners: " . $this->partnersToString($userTask["partners"]) . "\n";
        }
        return $data;
    }
    private function partnersToString($partners) {
        $usersString = "";
        foreach ($partners as $partner) {
            $usersString = $usersString . $partner . ", ";
        }
        //$usersString = rtrim($tasksString, ", ");
        return $usersString;
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