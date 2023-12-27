package user.service.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import user.service.userservice.DTOs.NotFoundExceptionDto;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<NotFoundExceptionDto> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(
                new NotFoundExceptionDto(HttpStatus.NOT_FOUND , notFoundException.getMessage()), HttpStatus.NOT_FOUND
        );
    }
}
