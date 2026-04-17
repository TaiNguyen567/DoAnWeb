package ntu.nguyenthanhtai_65133085.doanweb.dto;

import lombok.Data;

@Data
public class HabitDTO {
	private Long id;
	private String name;
	private String description;
	private int goal;
}