package tw.com.cathaybk.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tw.com.cathaybk.utils.Result;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public Result userNotFoundException(UserNotFoundException e) {
        log.error("異常訊息: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(Exception e) {
        log.error("異常訊息: {}", e.getMessage());
        return Result.error("發生預期外之錯誤");
    }
}
