package tw.com.cathaybk.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity userNotFoundException(UserNotFoundException e) {
        log.error("異常訊息: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("異常訊息: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("有鍵值重複情形發生!");
    }

    @ExceptionHandler
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("異常訊息: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("發生預期外之錯誤");
    }
}
