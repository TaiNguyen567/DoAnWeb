package ntu.nguyenthanhtai_65133085.doanweb.dto;

import lombok.Data;

@Data
public class HabitStatsDTO {
	private Long habitId;
	private String habitName;
	private int goal;

	private int totalCheckins; // Tổng số lần check-in
	private int completedCheckins; // Số lần COMPLETED
	private int skippedCheckins; // Số lần SKIPPED
	private double completionRate; // Tỉ lệ hoàn thành (%)

	private int currentStreak; // Streak hiện tại (ngày liên tiếp gần nhất)
	private int longestStreak; // Streak dài nhất từ trước đến nay
}