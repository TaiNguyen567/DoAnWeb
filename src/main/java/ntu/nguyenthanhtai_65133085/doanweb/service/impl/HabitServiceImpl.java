package ntu.nguyenthanhtai_65133085.doanweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.entity.Habit;
import ntu.nguyenthanhtai_65133085.doanweb.entity.User;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitRepository;
import ntu.nguyenthanhtai_65133085.doanweb.repository.UserRepository;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitService;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

	private final HabitRepository habitRepository;
	private final UserRepository userRepository;

	@Override
	public HabitDTO createHabit(HabitRequestDTO requestDTO, String username) {
		// Tìm User từ Database dựa vào username lấy được từ Token
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		// Tạo thói quen mới và gắn vào User đó
		Habit habit = new Habit();
		habit.setName(requestDTO.getName());
		habit.setDescription(requestDTO.getDescription());
		habit.setGoal(requestDTO.getGoal());
		habit.setUser(user);

		Habit savedHabit = habitRepository.save(habit);
		return mapToDTO(savedHabit);
	}

	@Override
	public List<HabitDTO> getAllHabitsByUser(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		// Gọi hàm thủ kho đã tạo sẵn để lấy list thói quen
		List<Habit> habits = habitRepository.findByUserId(user.getId());

		// Chuyển đổi từ Entity sang DTO để trả ra ngoài
		return habits.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	// Hàm phụ trợ chuyển đổi Entity -> DTO
	private HabitDTO mapToDTO(Habit habit) {
		HabitDTO dto = new HabitDTO();
		dto.setId(habit.getId());
		dto.setName(habit.getName());
		dto.setDescription(habit.getDescription());
		dto.setGoal(habit.getGoal());
		return dto;
	}
}