package user.service.userservice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import user.service.userservice.DTOs.UserDto;
import user.service.userservice.exceptions.NotFoundException;
import user.service.userservice.models.Session;
import user.service.userservice.models.SessionStatus;
import user.service.userservice.models.User;
import user.service.userservice.respository.SessionRepository;
import user.service.userservice.respository.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository , BCryptPasswordEncoder bCryptPasswordEncoder,
                       SessionRepository sessionRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;{
            this.userRepository = userRepository;
        }
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDto> login(String email , String password) throws NotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if(userOptional.isEmpty()){
            return this.signup(email , password);
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password , user.getPassword())){
            throw new NotFoundException("User with email " + email + " doesn't exist");
        }

        String token = RandomStringUtils.randomAlphanumeric(30);

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        UserDto userDto = convertUserIntoUserDto(user);

        return new ResponseEntity<>(userDto , headers , HttpStatus.OK);
    }

    public ResponseEntity<UserDto> signup(String email , String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);
        UserDto userDto = convertUserIntoUserDto(user);

        return new ResponseEntity<>(userDto , HttpStatus.OK);
    }




    public UserDto convertUserIntoUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
