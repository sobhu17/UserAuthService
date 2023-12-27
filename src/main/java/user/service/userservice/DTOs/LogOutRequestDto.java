package user.service.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequestDto {
    private String token;
    private Long userid;
}
