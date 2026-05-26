package ntu.nguyenthanhtai_65133085.doanweb.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HabitCheckinRequestDTO {

	@NotNull(message = "Ngày check-in không được để trống")
	private LocalDate checkinDate;

	// COMPLETED hoặc SKIPPED
	@NotBlank(message = "Trạng thái không được để trống")
	private String status;

	private String notes;
}