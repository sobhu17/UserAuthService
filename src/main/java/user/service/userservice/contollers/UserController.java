package user.service.userservice.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.service.userservice.DTOs.SetUserRolesRequestDto;
import user.service.userservice.DTOs.UserDto;
import user.service.userservice.exceptions.NotFoundException;
import user.service.userservice.services.UserService;

@RestController
@RequestMapping("/users/")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long id) throws NotFoundException {
        return userService.getUserDetails(id);
    }

    @PostMapping("{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long id , @RequestBody SetUserRolesRequestDto request){
        return userService.setUserRoles(id , request.getRoleIds());
    }

}
