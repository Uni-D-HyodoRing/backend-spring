package unid.hyodoring.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import unid.hyodoring.api.ApiResponse;
import unid.hyodoring.api.code.status.ErrorStatus;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(GeneralException.class)
    public ApiResponse<ErrorStatus> baseExceptionHandle(GeneralException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new ApiResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorStatus> exceptionHandle(Exception exception) {
        log.error("Exception has occurred. {}", exception);
        return new ApiResponse<>(ErrorStatus._INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errors = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> String.format("'%s': %s ", fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);
        log.warn("MethodArgumentNotValidException. error message: {}", errorMessage);

        ApiResponse<List<String>> response = ApiResponse.onFailure(
            ErrorStatus._BAD_REQUEST.getCode(),
            ErrorStatus._BAD_REQUEST.getMessage(),
            errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}