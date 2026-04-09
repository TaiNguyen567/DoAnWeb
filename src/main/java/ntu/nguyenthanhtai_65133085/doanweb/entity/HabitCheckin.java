package ntu.nguyenthanhtai_65133085.doanweb.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habit_checkins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitCheckin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "habit_id", nullable = false)
	private Habit habit;

	@Column(name = "checkin_date", nullable = false)
	private LocalDate checkinDate;

	@Column(nullable = false, length = 20)
	private String status;

	@Column(columnDefinition = "TEXT")
	private String notes;
}