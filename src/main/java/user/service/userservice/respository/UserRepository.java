package user.service.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.service.userservice.DTOs.LogInRequestDto;
import user.service.userservice.DTOs.UserDto;
import user.service.userservice.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserByEmail(String email);
}
