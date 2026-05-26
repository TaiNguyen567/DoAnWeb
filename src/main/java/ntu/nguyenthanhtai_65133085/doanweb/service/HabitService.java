package ntu.nguyenthanhtai_65133085.doanweb.service;

import java.util.List;

import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;

public interface HabitService {
	HabitDTO createHabit(HabitRequestDTO habitRequestDTO, String username);

	List<HabitDTO> getAllHabitsByUser(String username);

	HabitDTO updateHabit(Long habitId, HabitRequestDTO habitRequestDTO, String username); // ← thêm

	void deleteHabit(Long habitId, String username); // ← thêm
}