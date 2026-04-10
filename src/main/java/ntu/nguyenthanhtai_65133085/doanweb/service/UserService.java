package ntu.nguyenthanhtai_65133085.doanweb.service;

import ntu.nguyenthanhtai_65133085.doanweb.dto.UserDTO;
import ntu.nguyenthanhtai_65133085.doanweb.dto.UserRegisterDTO;

public interface UserService {

	UserDTO registerUser(UserRegisterDTO registerDTO);
}
