package user.service.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundExceptionDto {
    private HttpStatus status;
    private String message;

    public NotFoundExceptionDto(HttpStatus status , String message){
        this.status = status;
        this.message = message;
    }
}
