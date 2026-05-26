package ntu.nguyenthanhtai_65133085.doanweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.entity.Habit;
import ntu.nguyenthanhtai_65133085.doanweb.entity.User;
import ntu.nguyenthanhtai_65133085.doanweb.exception.ResourceNotFoundException;
import ntu.nguyenthanhtai_65133085.doanweb.exception.UnauthorizedException;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitRepository;
import ntu.nguyenthanhtai_65133085.doanweb.repository.UserRepository;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitService;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl implements HabitService {

	private final HabitRepository habitRepository;
	private final UserRepository userRepository;

	private User findUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng: " + username));
	}

	private HabitDTO toDTO(Habit habit) {
		HabitDTO dto = new HabitDTO();
		dto.setId(habit.getId());
		dto.setName(habit.getName());
		dto.setDescription(habit.getDescription());
		dto.setGoal(habit.getGoal());
		return dto;
	}

	@Override
	public HabitDTO createHabit(HabitRequestDTO habitRequestDTO, String username) {
		User user = findUserByUsername(username);
		Habit habit = new Habit();
		habit.setName(habitRequestDTO.getName());
		habit.setDescription(habitRequestDTO.getDescription());
		habit.setGoal(habitRequestDTO.getGoal());
		habit.setUser(user);
		return toDTO(habitRepository.save(habit));
	}

	@Override
	public List<HabitDTO> getAllHabitsByUser(String username) {
		User user = findUserByUsername(username);
		return habitRepository.findByUserId(user.getId()).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public HabitDTO updateHabit(Long habitId, HabitRequestDTO habitRequestDTO, String username) {
		Habit habit = habitRepository.findById(habitId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thói quen với id: " + habitId));
		if (!habit.getUser().getUsername().equals(username)) {
			throw new UnauthorizedException("Bạn không có quyền chỉnh sửa thói quen này");
		}
		habit.setName(habitRequestDTO.getName());
		habit.setDescription(habitRequestDTO.getDescription());
		habit.setGoal(habitRequestDTO.getGoal());
		return toDTO(habitRepository.save(habit));
	}

	@Override
	public void deleteHabit(Long habitId, String username) {
		Habit habit = habitRepository.findById(habitId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thói quen với id: " + habitId));
		if (!habit.getUser().getUsername().equals(username)) {
			throw new UnauthorizedException("Bạn không có quyền xóa thói quen này");
		}
		habitRepository.delete(habit);
	}
}