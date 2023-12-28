package user.service.userservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import user.service.userservice.DTOs.UserDto;
import user.service.userservice.DTOs.ValidateRequestDto;
import user.service.userservice.DTOs.ValidateResponseDto;
import user.service.userservice.exceptions.NotFoundException;
import user.service.userservice.models.Role;
import user.service.userservice.models.Session;
import user.service.userservice.models.SessionStatus;
import user.service.userservice.models.User;
import user.service.userservice.respository.SessionRepository;
import user.service.userservice.respository.UserRepository;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

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

        MacAlgorithm alg = Jwts.SIG.HS256;
        SecretKey key = alg.key().build();

        Map<String , Object> jsonForJwt = new HashMap<>();
        jsonForJwt.put("email" , user.getEmail());
        jsonForJwt.put("roles" , user.getRoles());
        jsonForJwt.put("createAt" , new Date());
        jsonForJwt.put("expiryAt" , new Date(LocalDate.now().plusDays(3).toEpochDay()));

        token = Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key)
                .compact();

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

    public ResponseEntity<Void> logout(String token , Long userId){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token , userId);

        if(sessionOptional.isEmpty()){
            return null;
        }

        Session session = sessionOptional.get();

        session.setStatus(SessionStatus.ENDED);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public Optional<ValidateResponseDto> validateToken(String token , Long userId){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if (!session.getStatus().equals(SessionStatus.ACTIVE)) {
            return Optional.empty();
        }


//        Jws<Claims> claimsJws = Jwts.parser()
//                .build()
//                .parseSignedClaims(token);
//
//        String email = (String) claimsJws.getPayload().get("email");
//        List<Role> roles = (List<Role>) claimsJws.getPayload().get("roles");
//        Date expiryAt = (Date) claimsJws.getPayload().get("expiryAt");
//        Date createAt = (Date) claimsJws.getPayload().get("createAt");
//
//        if (expiryAt.before(new Date())) {
//            return Optional.empty();
//        }

        User user = userRepository.findById(userId).get();

        ValidateResponseDto validateResponseDto = new ValidateResponseDto();
        validateResponseDto.setEmail(user.getEmail());
        validateResponseDto.setUserId(userId);
//        validateResponseDto.setExpiryAt(expiryAt);
//        validateResponseDto.setCreatedAt(createAt);
        validateResponseDto.setRoles(user.getRoles());

        return Optional.of(validateResponseDto);
    }




    public UserDto convertUserIntoUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
