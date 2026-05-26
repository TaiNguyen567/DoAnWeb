package ntu.nguyenthanhtai_65133085.doanweb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitCheckinRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.HabitRequestDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserRegisterDTO;
import ntu.nguyenthanhtai_65133085.doanweb.exception.ResourceAlreadyExistsException;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitCheckinService;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitService;
import ntu.nguyenthanhtai_65133085.doanweb.service.HabitStatsService;
import ntu.nguyenthanhtai_65133085.doanweb.service.UserService;

@Controller
@RequiredArgsConstructor
public class WebController {

	private final UserService userService;
	private final HabitService habitService;
	private final HabitCheckinService habitCheckinService;
	private final HabitStatsService habitStatsService;

	// ==================== AUTH ====================

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("registerDTO", new UserRegisterDTO());
		return "register";
	}

	@PostMapping("/register")
	public String handleRegister(@Valid @ModelAttribute("registerDTO") UserRegisterDTO dto, BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "register";
		}
		try {
			userService.registerUser(dto);
			redirectAttributes.addFlashAttribute("success", "Đăng ký thành công! Hãy đăng nhập.");
			return "redirect:/login";
		} catch (ResourceAlreadyExistsException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}

	// ==================== DASHBOARD ====================

	@GetMapping({ "/", "/dashboard" })
	public String dashboard(Model model, Authentication auth) {
		String username = auth.getName();
		model.addAttribute("habits", habitService.getAllHabitsByUser(username));
		model.addAttribute("habitRequest", new HabitRequestDTO());
		model.addAttribute("username", username);
		return "dashboard";
	}

	@PostMapping("/habits")
	public String createHabit(@Valid @ModelAttribute("habitRequest") HabitRequestDTO dto, BindingResult result,
			Authentication auth, Model model, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			model.addAttribute("habits", habitService.getAllHabitsByUser(auth.getName()));
			model.addAttribute("username", auth.getName());
			return "dashboard";
		}
		habitService.createHabit(dto, auth.getName());
		redirectAttributes.addFlashAttribute("success", "Tạo thói quen thành công!");
		return "redirect:/dashboard";
	}

	// ==================== HABIT DETAIL ====================

	@GetMapping("/habits/{id}")
	public String habitDetail(@PathVariable Long id, Model model, Authentication auth) {
		String username = auth.getName();
		model.addAttribute("habit", habitService.getAllHabitsByUser(username).stream().filter(h -> h.getId().equals(id))
				.findFirst().orElseThrow(() -> new RuntimeException("Không tìm thấy thói quen")));
		model.addAttribute("checkins", habitCheckinService.getCheckinsByHabit(id, username));
		model.addAttribute("checkinRequest", new HabitCheckinRequestDTO());
		model.addAttribute("stats", habitStatsService.getStatsByHabit(id, username));
		model.addAttribute("username", username);
		return "habit-detail";
	}

	@PostMapping("/habits/{id}/checkins")
	public String createCheckin(@PathVariable Long id,
			@Valid @ModelAttribute("checkinRequest") HabitCheckinRequestDTO dto, BindingResult result,
			Authentication auth, RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			habitCheckinService.checkin(id, dto, auth.getName());
			redirectAttributes.addFlashAttribute("success", "Check-in thành công!");
		}
		return "redirect:/habits/" + id;
	}

	@PostMapping("/habits/{id}/delete")
	public String deleteHabit(@PathVariable Long id, Authentication auth, RedirectAttributes redirectAttributes) {
		habitService.deleteHabit(id, auth.getName());
		redirectAttributes.addFlashAttribute("success", "Đã xóa thói quen.");
		return "redirect:/dashboard";
	}

	// ==================== STATS ====================

	@GetMapping("/stats")
	public String statsPage(Model model, Authentication auth) {
		String username = auth.getName();
		model.addAttribute("statsList", habitStatsService.getAllStatsForUser(username));
		model.addAttribute("username", username);
		return "stats";
	}
}