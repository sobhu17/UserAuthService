package user.service.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.service.userservice.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
}
