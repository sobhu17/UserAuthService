package user.service.userservice.DTOs;

import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import user.service.userservice.models.Role;
import user.service.userservice.models.User;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles;
}
