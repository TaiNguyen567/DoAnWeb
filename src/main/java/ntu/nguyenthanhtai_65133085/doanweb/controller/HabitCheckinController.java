package ntu.nguyenthanhtai_65133085.doanweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitCheckinService;

@RestController
@RequestMapping("/api/habits/{habitId}/checkins")
@RequiredArgsConstructor
public class HabitCheckinController {

	private final HabitCheckinService habitCheckinService;

	// Check-in cho một thói quen
	@PostMapping
	public ResponseEntity<HabitCheckinDTO> checkin(@PathVariable Long habitId,
			@Valid @RequestBody HabitCheckinRequestDTO requestDTO, Authentication authentication) {
		String username = authentication.getName();
		HabitCheckinDTO checkin = habitCheckinService.checkin(habitId, requestDTO, username);
		return new ResponseEntity<>(checkin, HttpStatus.CREATED);
	}

	// Lấy lịch sử check-in của một thói quen
	@GetMapping
	public ResponseEntity<List<HabitCheckinDTO>> getCheckins(@PathVariable Long habitId,
			Authentication authentication) {
		String username = authentication.getName();
		return ResponseEntity.ok(habitCheckinService.getCheckinsByHabit(habitId, username));
	}
}