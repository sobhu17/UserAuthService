package user.service.userservice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import user.service.userservice.models.User;
import user.service.userservice.respository.UserRepository;

import java.util.Optional;

@Service
public class CustomSpringUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomSpringUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(username);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException("User with email " + username + "doesn't exist!!");
        }

        User user = userOptional.get();

        return new CustomSpringUserDetails(user);
    }
}
