package user.service.userservice.contollers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.service.userservice.DTOs.SetUserRolesRequestDto;
import user.service.userservice.DTOs.UserDto;

@RestController
@RequestMapping("/users/")
public class UserController {

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long id){
        return null;
    }

    @PostMapping("{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long id , @RequestBody SetUserRolesRequestDto request){
        return null;
    }

}
