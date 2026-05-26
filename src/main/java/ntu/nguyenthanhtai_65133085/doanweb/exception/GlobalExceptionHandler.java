package ntu.nguyenthanhtai_65133085.doanweb.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Lỗi validation (@Valid)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			fieldErrors.put(fieldName, errorMessage);
		});

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now().toString());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("errors", fieldErrors);

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	// Lỗi không tìm thấy resource
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Lỗi resource đã tồn tại
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<Map<String, Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
	}

	// Lỗi không có quyền
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Map<String, Object>> handleUnauthorized(UnauthorizedException ex) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
	}

	// Lỗi chung
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		return buildErrorResponse("Đã xảy ra lỗi: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now().toString());
		body.put("status", status.value());
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}
}