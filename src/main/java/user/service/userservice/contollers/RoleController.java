package user.service.userservice.contollers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.userservice.DTOs.CreateRoleRequestDto;
import user.service.userservice.models.Role;
import user.service.userservice.services.RoleService;

@RestController
@RequestMapping("/roles/")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDto request){
        return roleService.createRole(request.getName());
    }

}
