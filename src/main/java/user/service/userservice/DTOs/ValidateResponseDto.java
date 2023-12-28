package user.service.userservice.DTOs;

import lombok.Getter;
import lombok.Setter;
import user.service.userservice.models.Role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ValidateResponseDto {
    private String email;
    private Long userId;
    private Date expiryAt;
    private Date createdAt;
    private Set<Role> roles;

}
