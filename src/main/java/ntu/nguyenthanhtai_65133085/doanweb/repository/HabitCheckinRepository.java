package ntu.nguyenthanhtai_65133085.doanweb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.nguyenthanhtai_65133085.doanweb.entity.HabitCheckin;

@Repository
public interface HabitCheckinRepository extends JpaRepository<HabitCheckin, Long> {

	// Lấy tất cả checkin của 1 habit
	List<HabitCheckin> findByHabitId(Long habitId);

	// Lấy checkin sắp xếp theo ngày tăng dần (dùng để tính streak)
	List<HabitCheckin> findByHabitIdOrderByCheckinDateAsc(Long habitId);

	// Lấy checkin trong khoảng thời gian (dùng cho thống kê tuần/tháng)
	List<HabitCheckin> findByHabitIdAndCheckinDateBetween(Long habitId, LocalDate startDate, LocalDate endDate);
}