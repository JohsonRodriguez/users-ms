package pe.edu.lordbyron.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.lordbyron.authserver.model.Employee;

public interface UserRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);
}
