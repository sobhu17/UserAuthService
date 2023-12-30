package user.service.userservice.contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.userservice.DTOs.*;
import user.service.userservice.exceptions.NotFoundException;
import user.service.userservice.models.Session;
import user.service.userservice.models.SessionStatus;
import user.service.userservice.models.User;
import user.service.userservice.respository.SessionRepository;
import user.service.userservice.services.AuthService;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto request){
        return authService.signup(request.getEmail() , request.getPassword());

    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LogInRequestDto request) throws NotFoundException{
        return authService.login(request.getEmail() , request.getPassword());

    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestDto request){
        return authService.logout(request.getToken() , request.getUserId());
    }
    @PostMapping("/validate")
    public ResponseEntity<ValidateResponseDto> validate(@RequestBody ValidateRequestDto request){
        Optional<ValidateResponseDto> validateResponseDtoOptional = authService.validateToken(request.getToken() , request.getUserId());

        ValidateResponseDto validateResponseDto = validateResponseDtoOptional.get();

        if(validateResponseDtoOptional.isEmpty()){
            return new ResponseEntity<>(validateResponseDto , HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(validateResponseDto , HttpStatus.OK);
    }

}
