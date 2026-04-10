package ntu.nguyenthanhtai_65133085.doanweb.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserRegisterDTO;
import ntu.nguyenthanhtai_65133085.doanweb.entity.User;
import ntu.nguyenthanhtai_65133085.doanweb.repository.UserRepository;
import ntu.nguyenthanhtai_65133085.doanweb.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDTO registerUser(UserRegisterDTO registerDTO) {
		if (userRepository.existsByUsername(registerDTO.getUsername())) {
			throw new RuntimeException("Username is already taken");
		}
		if (userRepository.existsByEmail(registerDTO.getEmail())) {
			throw new RuntimeException("Email is already taken");
		}

		User user = new User();
		user.setUsername(registerDTO.getUsername());
		user.setEmail(registerDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

		User savedUser = userRepository.save(user);

		UserDTO userDTO = new UserDTO();
		userDTO.setId(savedUser.getId());
		userDTO.setUsername(savedUser.getUsername());
		userDTO.setEmail(savedUser.getEmail());

		return userDTO;
	}
}
