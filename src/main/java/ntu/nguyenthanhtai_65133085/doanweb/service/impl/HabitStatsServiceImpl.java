package ntu.nguyenthanhtai_65133085.doanweb.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitStatsDTO;
import ntu.nguyenthanhtai_65133085.doanweb.entity.Habit;
import ntu.nguyenthanhtai_65133085.doanweb.entity.HabitCheckin;
import ntu.nguyenthanhtai_65133085.doanweb.entity.User;
import ntu.nguyenthanhtai_65133085.doanweb.exception.ResourceNotFoundException;
import ntu.nguyenthanhtai_65133085.doanweb.exception.UnauthorizedException;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitCheckinRepository;
import ntu.nguyenthanhtai_65133085.doanweb.repository.HabitRepository;
import ntu.nguyenthanhtai_65133085.doanweb.repository.UserRepository;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitStatsService;

@Service
@RequiredArgsConstructor
public class HabitStatsServiceImpl implements HabitStatsService {

	private final HabitRepository habitRepository;
	private final HabitCheckinRepository habitCheckinRepository;
	private final UserRepository userRepository;

	@Override
	public HabitStatsDTO getStatsByHabit(Long habitId, String username) {
		Habit habit = habitRepository.findById(habitId)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thói quen với id: " + habitId));

		if (!habit.getUser().getUsername().equals(username)) {
			throw new UnauthorizedException("Bạn không có quyền xem thống kê thói quen này");
		}

		List<HabitCheckin> checkins = habitCheckinRepository.findByHabitIdOrderByCheckinDateAsc(habitId);
		return buildStats(habit, checkins);
	}

	@Override
	public List<HabitStatsDTO> getAllStatsForUser(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng: " + username));

		List<Habit> habits = habitRepository.findByUserId(user.getId());

		return habits.stream().map(habit -> {
			List<HabitCheckin> checkins = habitCheckinRepository.findByHabitIdOrderByCheckinDateAsc(habit.getId());
			return buildStats(habit, checkins);
		}).collect(Collectors.toList());
	}

	// ===================== LOGIC TÍNH THỐNG KÊ =====================

	private HabitStatsDTO buildStats(Habit habit, List<HabitCheckin> checkins) {
		HabitStatsDTO stats = new HabitStatsDTO();
		stats.setHabitId(habit.getId());
		stats.setHabitName(habit.getName());
		stats.setGoal(habit.getGoal());

		// Đếm tổng check-in
		stats.setTotalCheckins(checkins.size());

		// Đếm theo trạng thái
		long completed = checkins.stream().filter(c -> "COMPLETED".equalsIgnoreCase(c.getStatus())).count();
		long skipped = checkins.stream().filter(c -> "SKIPPED".equalsIgnoreCase(c.getStatus())).count();

		stats.setCompletedCheckins((int) completed);
		stats.setSkippedCheckins((int) skipped);

		// Tính tỉ lệ hoàn thành
		if (checkins.isEmpty()) {
			stats.setCompletionRate(0.0);
		} else {
			double rate = (double) completed / checkins.size() * 100;
			stats.setCompletionRate(Math.round(rate * 10.0) / 10.0); // làm tròn 1 chữ số
		}

		// Chỉ lấy các ngày COMPLETED để tính streak
		List<LocalDate> completedDates = checkins.stream().filter(c -> "COMPLETED".equalsIgnoreCase(c.getStatus()))
				.map(HabitCheckin::getCheckinDate).distinct().sorted().collect(Collectors.toList());

		stats.setCurrentStreak(calculateCurrentStreak(completedDates));
		stats.setLongestStreak(calculateLongestStreak(completedDates));

		return stats;
	}

	// Tính streak hiện tại: đếm ngược từ hôm nay
	private int calculateCurrentStreak(List<LocalDate> sortedDates) {
		if (sortedDates.isEmpty())
			return 0;

		LocalDate today = LocalDate.now();
		LocalDate checkDate = today;
		int streak = 0;

		// Cho phép streak hôm nay hoặc hôm qua (tránh reset streak nếu chưa check hôm
		// nay)
		if (!sortedDates.contains(today) && !sortedDates.contains(today.minusDays(1))) {
			return 0;
		}

		if (!sortedDates.contains(today)) {
			checkDate = today.minusDays(1);
		}

		while (sortedDates.contains(checkDate)) {
			streak++;
			checkDate = checkDate.minusDays(1);
		}

		return streak;
	}

	// Tính streak dài nhất từ trước đến nay
	private int calculateLongestStreak(List<LocalDate> sortedDates) {
		if (sortedDates.isEmpty())
			return 0;

		int longest = 1;
		int current = 1;

		for (int i = 1; i < sortedDates.size(); i++) {
			long diff = ChronoUnit.DAYS.between(sortedDates.get(i - 1), sortedDates.get(i));
			if (diff == 1) {
				current++;
				longest = Math.max(longest, current);
			} else {
				current = 1;
			}
		}

		return longest;
	}
}