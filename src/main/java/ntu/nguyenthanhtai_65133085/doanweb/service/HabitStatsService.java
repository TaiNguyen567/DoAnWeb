package ntu.nguyenthanhtai_65133085.doanweb.service;

import java.util.List;

import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitStatsDTO;

public interface HabitStatsService {

	// Thống kê toàn bộ 1 habit
	HabitStatsDTO getStatsByHabit(Long habitId, String username);

	// Thống kê tất cả habit của user
	List<HabitStatsDTO> getAllStatsForUser(String username);
}