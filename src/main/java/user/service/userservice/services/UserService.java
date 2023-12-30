package user.service.userservice.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import user.service.userservice.DTOs.SetUserRolesRequestDto;
import user.service.userservice.DTOs.UserDto;
import user.service.userservice.exceptions.NotFoundException;
import user.service.userservice.models.Role;
import user.service.userservice.models.User;
import user.service.userservice.respository.RoleRepository;
import user.service.userservice.respository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository , RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<UserDto> getUserDetails(Long id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new NotFoundException("User with id " + id + " isn't available!!");
        }

        User user = userOptional.get();

        UserDto userDto = convertUserIntoUserDto(user);

        return new ResponseEntity<>(userDto , HttpStatus.OK);
    }

    public ResponseEntity<UserDto> setUserRoles(Long id , List<Long> roleIds){
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        Set<Role> userRoles = new HashSet<>();
        for(Role role : roles){
            userRoles.add(role);
        }

        User user = userRepository.findById(id).get();

        user.setRoles(userRoles);

        UserDto userDto = convertUserIntoUserDto(user);
        userRepository.save(user);

        return new ResponseEntity<>(userDto , HttpStatus.OK);
    }

    public UserDto convertUserIntoUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }


}
