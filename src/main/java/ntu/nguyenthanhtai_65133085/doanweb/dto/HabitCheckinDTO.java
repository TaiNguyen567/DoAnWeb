package ntu.nguyenthanhtai_65133085.doanweb.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class HabitCheckinDTO {

	private Long id;
	private Long habitId;
	private String habitName;

	// Format ngày khi trả về JSON qua API: "2026-05-30"
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate checkinDate;

	private String status; // "COMPLETED" hoặc "SKIPPED"
	private String notes;

	// Tiện ích: hiển thị label trạng thái bằng tiếng Việt
	public String getStatusLabel() {
		if ("COMPLETED".equalsIgnoreCase(status))
			return "Hoàn thành";
		if ("SKIPPED".equalsIgnoreCase(status))
			return "Bỏ qua";
		return status;
	}

	// Tiện ích: kiểm tra đây có phải check-in hôm nay không
	public boolean isToday() {
		return checkinDate != null && checkinDate.equals(LocalDate.now());
	}
}