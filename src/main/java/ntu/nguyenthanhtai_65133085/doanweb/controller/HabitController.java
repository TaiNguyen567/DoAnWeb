package ntu.nguyenthanhtai_65133085.doanweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitService;

@RestController
@RequestMapping("/api/habits")
@RequiredArgsConstructor
public class HabitController {

	private final HabitService habitService;

	// API Tạo thói quen mới
	@PostMapping
	public ResponseEntity<HabitDTO> createHabit(@Valid @RequestBody HabitRequestDTO requestDTO,
			Authentication authentication) {
		// authentication.getName() sẽ tự động trích xuất username từ Token (JWT) mà
		// khách gửi lên
		String username = authentication.getName();
		HabitDTO createdHabit = habitService.createHabit(requestDTO, username);
		return new ResponseEntity<>(createdHabit, HttpStatus.CREATED);
	}

	// API Lấy danh sách thói quen của người đang đăng nhập
	@GetMapping
	public ResponseEntity<List<HabitDTO>> getAllHabits(Authentication authentication) {
		String username = authentication.getName();
		List<HabitDTO> habits = habitService.getAllHabitsByUser(username);
		return ResponseEntity.ok(habits);
	}
}