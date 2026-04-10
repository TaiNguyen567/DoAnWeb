package ntu.nguyenthanhtai_65133085.doanweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {

	@NotBlank
	@Size(min = 4, max = 50)
	private String username;

	@NotBlank
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	@NotBlank
	@Email
	private String email;
}
