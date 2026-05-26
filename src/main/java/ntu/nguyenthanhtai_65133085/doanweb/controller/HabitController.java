package ntu.nguyenthanhtai_65133085.doanweb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// Tạo thói quen mới
	@PostMapping
	public ResponseEntity<HabitDTO> createHabit(@Valid @RequestBody HabitRequestDTO requestDTO,
			Authentication authentication) {
		String username = authentication.getName();
		HabitDTO created = habitService.createHabit(requestDTO, username);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// Lấy tất cả thói quen của user
	@GetMapping
	public ResponseEntity<List<HabitDTO>> getAllHabits(Authentication authentication) {
		String username = authentication.getName();
		return ResponseEntity.ok(habitService.getAllHabitsByUser(username));
	}

	// Cập nhật thói quen
	@PutMapping("/{habitId}")
	public ResponseEntity<HabitDTO> updateHabit(@PathVariable Long habitId,
			@Valid @RequestBody HabitRequestDTO requestDTO, Authentication authentication) {
		String username = authentication.getName();
		HabitDTO updated = habitService.updateHabit(habitId, requestDTO, username);
		return ResponseEntity.ok(updated);
	}

	// Xóa thói quen
	@DeleteMapping("/{habitId}")
	public ResponseEntity<Void> deleteHabit(@PathVariable Long habitId, Authentication authentication) {
		String username = authentication.getName();
		habitService.deleteHabit(habitId, username);
		return ResponseEntity.noContent().build();
	}
}