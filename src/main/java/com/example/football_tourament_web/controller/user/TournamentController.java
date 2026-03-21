package com.example.football_tourament_web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/tournament")
public class TournamentController {

	@GetMapping({"", "/"})
	public String tournamentList() {
		return "user/tournament/Tournament_list";
	}

	@GetMapping("/match-schedule")
	public String matchSchedule() {
		return "user/tournament/Match_schedule";
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "user/tournament/Sign_up";
	}

	@GetMapping("/competing-teams")
	public String competingTeams() {
		return "user/tournament/Competing_Teams";
	}
}
