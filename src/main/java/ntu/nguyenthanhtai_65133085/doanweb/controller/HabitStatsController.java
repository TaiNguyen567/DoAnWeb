package ntu.nguyenthanhtai_65133085.doanweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitStatsDTO;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitStatsService;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class HabitStatsController {

	private final HabitStatsService habitStatsService;

	// Thống kê 1 habit cụ thể
	@GetMapping("/habits/{habitId}")
	public ResponseEntity<HabitStatsDTO> getHabitStats(@PathVariable Long habitId, Authentication authentication) {
		String username = authentication.getName();
		return ResponseEntity.ok(habitStatsService.getStatsByHabit(habitId, username));
	}

	// Thống kê tất cả habit của user
	@GetMapping("/habits")
	public ResponseEntity<List<HabitStatsDTO>> getAllStats(Authentication authentication) {
		String username = authentication.getName();
		return ResponseEntity.ok(habitStatsService.getAllStatsForUser(username));
	}
}