package com.example.football_tourament_web.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.football_tourament_web.model.dto.NewsItem;
import com.example.football_tourament_web.service.CbsSportsNewsService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HomeController {
	private final CbsSportsNewsService cbsSportsNewsService;
	private final HttpClient httpClient;
	private static final Pattern LAT_PATTERN = Pattern.compile("\"lat\"\\s*:\\s*\"([^\"]+)\"");
	private static final Pattern LON_PATTERN = Pattern.compile("\"lon\"\\s*:\\s*\"([^\"]+)\"");
	private static final Pattern DISPLAY_NAME_PATTERN = Pattern.compile("\"display_name\"\\s*:\\s*\"([^\"]+)\"");

	public HomeController(CbsSportsNewsService cbsSportsNewsService) {
		this.cbsSportsNewsService = cbsSportsNewsService;
		this.httpClient = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.NORMAL)
				.connectTimeout(Duration.ofSeconds(10))
				.build();
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

	@GetMapping("/api/geocode")
	@ResponseBody
	public ResponseEntity<GeocodeResult> geocode(@RequestParam(name = "q") String query) {
		if (query == null || query.isBlank()) {
			return ResponseEntity.badRequest().build();
		}

		try {
			String url = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q="
					+ java.net.URLEncoder.encode(query, StandardCharsets.UTF_8);

			HttpRequest request = HttpRequest.newBuilder(URI.create(url))
					.timeout(Duration.ofSeconds(10))
					.header("Accept", "application/json")
					.header("User-Agent", "football-tourament-web/1.0 (contact page map)")
					.GET()
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			if (response.statusCode() < 200 || response.statusCode() >= 300) {
				return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
			}

			String body = response.body();

			Matcher latMatcher = LAT_PATTERN.matcher(body);
			Matcher lonMatcher = LON_PATTERN.matcher(body);
			if (!latMatcher.find() || !lonMatcher.find()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			double lat = Double.parseDouble(latMatcher.group(1));
			double lon = Double.parseDouble(lonMatcher.group(1));

			Matcher displayNameMatcher = DISPLAY_NAME_PATTERN.matcher(body);
			String displayName = displayNameMatcher.find() ? decodeJsonString(displayNameMatcher.group(1)) : null;

			return ResponseEntity.ok(new GeocodeResult(lat, lon, displayName));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
		}
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

	private record GeocodeResult(double lat, double lon, String displayName) {
	}

	private static String decodeJsonString(String input) {
		if (input == null) {
			return null;
		}
		return input
				.replace("\\\"", "\"")
				.replace("\\\\", "\\")
				.replace("\\/", "/")
				.replace("\\n", " ")
				.replace("\\r", " ")
				.replace("\\t", " ");
	}
}
