package ntu.nguyenthanhtai_65133085.doanweb.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HabitCheckinDTO {
	private Long id;
	private Long habitId;
	private String habitName;
	private LocalDate checkinDate;
	private String status;
	private String notes;
}