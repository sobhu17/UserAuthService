package user.service.userservice.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import user.service.userservice.models.Role;
import user.service.userservice.respository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<Role> createRole(String name){
        Role role = new Role();
        role.setRole(name);

        roleRepository.save(role);

        return new ResponseEntity<>(role , HttpStatus.OK);
    }
}
