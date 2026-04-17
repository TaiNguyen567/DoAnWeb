package ntu.nguyenthanhtai_65133085.doanweb.service;

import java.util.List;

import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;

public interface HabitService {
	HabitDTO createHabit(HabitRequestDTO habitRequestDTO, String username);

	List<HabitDTO> getAllHabitsByUser(String username);
}