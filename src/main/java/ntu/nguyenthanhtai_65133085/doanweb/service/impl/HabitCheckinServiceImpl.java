package ntu.nguyenthanhtai_65133085.doanweb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.entity.Habit;
import ntu.nguyenthanhtai_65133085.doanweb.entity.HabitCheckin;
import ntu.nguyenthanhtai_65133085.doanweb.exception.ResourceNotFoundException;
import ntu.nguyenthanhtai_65133085.doanweb.exception.UnauthorizedException;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitCheckinRepository;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitRepository;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitCheckinService;

@Service
@RequiredArgsConstructor
public class HabitCheckinServiceImpl implements HabitCheckinService {

	private final HabitCheckinRepository habitCheckinRepository;
	private final HabitRepository habitRepository;

	// Kiểm tra habit thuộc về user hiện tại
	private Habit getHabitAndVerifyOwner(Long habitId, String username) {
		Habit habit = habitRepository.findById(habitId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thói quen với id: " + habitId));

		if (!habit.getUser().getUsername().equals(username)) {
			throw new UnauthorizedException("Bạn không có quyền truy cập thói quen này");
		}
		return habit;
	}

	private HabitCheckinDTO toDTO(HabitCheckin checkin) {
		HabitCheckinDTO dto = new HabitCheckinDTO();
		dto.setId(checkin.getId());
		dto.setHabitId(checkin.getHabit().getId());
		dto.setHabitName(checkin.getHabit().getName());
		dto.setCheckinDate(checkin.getCheckinDate());
		dto.setStatus(checkin.getStatus());
		dto.setNotes(checkin.getNotes());
		return dto;
	}

	@Override
	public HabitCheckinDTO checkin(Long habitId, HabitCheckinRequestDTO requestDTO, String username) {
		Habit habit = getHabitAndVerifyOwner(habitId, username);

		HabitCheckin checkin = new HabitCheckin();
		checkin.setHabit(habit);
		checkin.setCheckinDate(requestDTO.getCheckinDate());
		checkin.setStatus(requestDTO.getStatus());
		checkin.setNotes(requestDTO.getNotes());

		return toDTO(habitCheckinRepository.save(checkin));
	}

	@Override
	public List<HabitCheckinDTO> getCheckinsByHabit(Long habitId, String username) {
		getHabitAndVerifyOwner(habitId, username); // xác minh quyền

		return habitCheckinRepository.findByHabitId(habitId).stream().map(this::toDTO).collect(Collectors.toList());
	}
}