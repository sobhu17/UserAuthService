package user.service.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.service.userservice.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session , Long> {
}
