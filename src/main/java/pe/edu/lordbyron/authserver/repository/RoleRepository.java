package pe.edu.lordbyron.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.lordbyron.authserver.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
