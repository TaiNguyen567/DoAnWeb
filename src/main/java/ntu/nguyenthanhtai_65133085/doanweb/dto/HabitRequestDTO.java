package ntu.nguyenthanhtai_65133085.doanweb.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HabitRequestDTO {

	@NotBlank(message = "Tên thói quen không được để trống")
	private String name;

	private String description;

	@Min(value = 1, message = "Mục tiêu ít nhất phải là 1")
	private int goal;

	private List<String> daysOfWeek; // Nhận List từ các checkbox trên HTML
	private String timeFrame;
}