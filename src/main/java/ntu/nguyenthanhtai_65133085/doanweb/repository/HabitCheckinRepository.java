package ntu.nguyenthanhtai_65133085.doanweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ntu.nguyenthanhtai_65133085.doanweb.entity.HabitCheckin;

@Repository
public interface HabitCheckinRepository extends JpaRepository<HabitCheckin, Long> {

	List<HabitCheckin> findByHabitId(Long habitId);
}
