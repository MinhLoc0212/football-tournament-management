package com.example.football_tourament_web.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.football_tourament_web.model.dto.NewsItem;
import com.example.football_tourament_web.service.CbsSportsNewsService;

@Controller
public class HomeController {
	private final CbsSportsNewsService cbsSportsNewsService;

	public HomeController(CbsSportsNewsService cbsSportsNewsService) {
		this.cbsSportsNewsService = cbsSportsNewsService;
	}

	@GetMapping({"/", "/home"})
	public String home() {
		return "user/home/index";
	}

	@GetMapping({"/tin-tuc", "/tin-tuc.html"})
	public String news(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 6;
		List<NewsItem> allItems = cbsSportsNewsService.getHeadlines();

		int totalItems = allItems.size();
		int totalPages = Math.max(1, (int) Math.ceil(totalItems / (double) pageSize));
		int currentPage = Math.min(Math.max(page, 1), totalPages);

		int fromIndex = Math.min((currentPage - 1) * pageSize, totalItems);
		int toIndex = Math.min(fromIndex + pageSize, totalItems);
		List<NewsItem> pageItems = allItems.subList(fromIndex, toIndex);

		int startPage = Math.max(1, currentPage - 2);
		int endPage = Math.min(totalPages, currentPage + 2);
		if (endPage - startPage + 1 < 5) {
			endPage = Math.min(totalPages, startPage + 4);
			startPage = Math.max(1, endPage - 4);
		}

		model.addAttribute("newsItems", pageItems);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("hasNews", !allItems.isEmpty());
		return "user/home/news";
	}

	@GetMapping({"/gioi-thieu", "/gioi-thieu.html"})
	public String aboutUs() {
		return "user/home/about-us";
	}

	@GetMapping({"/lien-he", "/lien-he.html"})
	public String contact() {
		return "user/home/contact";
	}

	@GetMapping({"/dang-nhap", "/dang-nhap.html"})
	public String login() {
		return "user/auth/login";
	}

	@GetMapping({"/dang-ky", "/dang-ky.html"})
	public String register() {
		return "user/auth/register";
	}

	@GetMapping({"/ca-nhan", "/ca-nhan.html"})
	public String profile() {
		return "user/profile/profile";
	}

	@GetMapping({"/thong-tin-doi", "/thong-tin-doi.html"})
	public String teamInfo() {
		return "user/profile/team-info";
	}

	@GetMapping({"/lich-su-dang-ky", "/lich-su-dang-ky.html"})
	public String registrationHistory() {
		return "user/profile/registration-history";
	}

	@GetMapping({"/lich-su-giao-dich", "/lich-su-giao-dich.html"})
	public String transactionHistory() {
		return "user/profile/transaction-history";
	}

	@GetMapping({"/thanh-toan", "/thanh-toan.html"})
	public String payment() {
		return "user/profile/payment";
	}
}
