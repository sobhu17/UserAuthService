package user.service.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequestDto {
    private String token;
    private Long userId;
}
