package com.example.football_tourament_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/tournament")
public class TournamentController {

	@GetMapping({"", "/"})
	public String tournamentList() {
		return "/user/tournament/Tournament_list";
	}

	@GetMapping("/match-schedule")
	public String matchSchedule() {
		return "/user/tournament/Match_schedule";
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "/user/tournament/Sign_up";
	}

	@GetMapping("/competing-teams")
	public String competingTeams() {
		return "/user/tournament/Competing_Teams";
	}

	@GetMapping("/standing-fragment")
	public String standingFragment() {
		return "/user/tournament/fragments/standing :: standing";
	}

	@GetMapping("/match-schedule-fragment")
	public String matchScheduleFragment() {
		return "/user/tournament/Match_schedule :: match-schedule";
	}

	@GetMapping("/statistics-fragment")
	public String statisticsFragment() {
		return "/user/tournament/fragments/statistics :: statistics";
	}

	@GetMapping("/charts-fragment")
	public String chartsFragment() {
		return "/user/tournament/fragments/charts :: charts";
	}

	@GetMapping("/bracket-fragment")
	public String bracketFragment() {
		return "/user/tournament/fragments/bracket :: bracket";
	}

	@GetMapping("/teams-fragment")
	public String teamsFragment() {
		return "/user/tournament/fragments/teams :: teams";
	}

	@GetMapping({"/tournament", "/tournament/"})
	public String tournamentListAlias() {
		return "redirect:/user/tournament";
	}

	@GetMapping("/tournament/match-schedule")
	public String matchScheduleAlias() {
		return "redirect:/user/tournament/match-schedule";
	}

	@GetMapping("/tournament/sign-up")
	public String signUpAlias() {
		return "redirect:/user/tournament/sign-up";
	}

	@GetMapping("/tournament/competing-teams")
	public String competingTeamsAlias() {
		return "redirect:/user/tournament/competing-teams";
	}
}
