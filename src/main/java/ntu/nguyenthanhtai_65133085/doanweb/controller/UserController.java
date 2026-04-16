package ntu.nguyenthanhtai_65133085.doanweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.AuthResponseDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserLoginDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserRegisterDTO;
import ntu.nguyenthanhtai_65133085.doanweb.security.JwtTokenProvider;
import ntu.nguyenthanhtai_65133085.doanweb.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterDTO registerDTO) {
		UserDTO createdUser = userService.registerUser(registerDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO loginDTO) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.generateToken(authentication.getName());

		return ResponseEntity.ok(new AuthResponseDTO(token));
	}
}