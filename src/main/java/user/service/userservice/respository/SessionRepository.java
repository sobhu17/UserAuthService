package user.service.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.service.userservice.models.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session , Long> {

    Optional<Session> findByTokenAndUser_Id(String token , Long userId);
}
