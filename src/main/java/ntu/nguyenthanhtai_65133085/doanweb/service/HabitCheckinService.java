package ntu.nguyenthanhtai_65133085.doanweb.service;

import java.util.List;

import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinRequestDTO;

public interface HabitCheckinService {
	HabitCheckinDTO checkin(Long habitId, HabitCheckinRequestDTO requestDTO, String username);

	List<HabitCheckinDTO> getCheckinsByHabit(Long habitId, String username);
}