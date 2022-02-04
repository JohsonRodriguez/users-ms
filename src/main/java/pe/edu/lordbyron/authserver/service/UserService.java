package pe.edu.lordbyron.authserver.service;

import org.springframework.http.ResponseEntity;
import pe.edu.lordbyron.authserver.dto.UpdatePasswordDto;
import pe.edu.lordbyron.authserver.model.Role;
import pe.edu.lordbyron.authserver.model.Employee;

import java.util.List;

public interface UserService {
    ResponseEntity<?> saveUser(Employee employee);
    ResponseEntity<?> saveRole (Role role);
    void addRoleToUser(String username, String roleName);
    void removeRoleToUser(String username, String roleName);
    ResponseEntity<?> getUser(String username);
    List<Employee> getUsers();
    ResponseEntity<?> updateUser(Employee employee);
    ResponseEntity<?> changeUserStatus (String username, boolean isEnabled);
    ResponseEntity<?> changePassword(UpdatePasswordDto updatePassword);
}
