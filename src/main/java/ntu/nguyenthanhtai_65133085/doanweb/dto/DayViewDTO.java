package ntu.nguyenthanhtai_65133085.doanweb.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class DayViewDTO {
	private LocalDate date;
	private String dayName;
	private boolean isToday;
	private List<HabitDayStatusDTO> habitStatuses;
	private int completedCount;
	private double completionRate;
}