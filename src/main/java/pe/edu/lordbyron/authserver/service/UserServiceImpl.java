package pe.edu.lordbyron.authserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.lordbyron.authserver.exception.UserRepositoryException;
import pe.edu.lordbyron.authserver.model.Role;
import pe.edu.lordbyron.authserver.model.Employee;
import pe.edu.lordbyron.authserver.repository.RoleRepository;
import pe.edu.lordbyron.authserver.repository.UserRepository;


import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> saveUser(Employee user) {
        log.info("Saving a new user {}", user.getName());
        var employee = userRepository.findByUsername(user.getUsername());
        if (employee != null) throw new UserRepositoryException("Nombre de usuario ya registrado");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Employee newUser = null;
        try {
            newUser = userRepository.save(user);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Usuario creado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> saveRole(Role role) {
        log.info("Saving a new role {}", role.getName());
        var r = roleRepository.findByName(role.getName());
        if (r != null) throw new UserRepositoryException("Rol ya registrado");
        Role newRole = null;
        try {
            newRole = roleRepository.save(role);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Rol creado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public void removeRoleToUser(String username, String roleName) {
        log.info("Removing role {} to user {}", roleName, username);
        try {
            var employee = userRepository.findByUsername(username);
            var role = roleRepository.findByName(roleName);
            employee.getRoles().remove(role);
        } catch (Exception e) {
            throw new UserRepositoryException("Usuario o role no registrado: " + e.getMessage());
        }
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        try {
            var employee = userRepository.findByUsername(username);
            var role = roleRepository.findByName(roleName);
            employee.getRoles().add(role);
        } catch (Exception e){
            throw new UserRepositoryException("Usuario o role no registrado: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getUser(String username) {
        log.info("Fetching user {}", username);
        try {
            var employee = userRepository.findByUsername(username);
            if (employee == null) throw new UserRepositoryException("Usuario no registrado");
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            throw new UserRepositoryException("Ocurrio un problema al buscar el usuario : " + e.getMessage());
        }
    }

    @Override
    public List<Employee> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> updateUser(Employee user) {
        var employee = userRepository.findByUsername(user.getUsername());
        if (employee == null) throw new UserRepositoryException("No se encontró un usuario con el username: " + user.getUsername());
        employee.setName(user.getName());
        employee.setUsername(user.getUsername());
        try {
            userRepository.save(employee);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Usuario actualizado con exito!");
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<?> changeUserStatus(String username, boolean isEnabled) {
        var employee = userRepository.findByUsername(username);
        if (employee == null) throw new UserRepositoryException("No se encontró un usuario con el username: " + username);
        employee.setEnabled(isEnabled);
        try {
            userRepository.save(employee);
        } catch (Exception e) {
            throw new UserRepositoryException(e.getMessage());
        }
        var body = new HashMap<>();
        body.put("message", "Estado del usuario modificado con exito!");
        return ResponseEntity.ok(body);
    }
}
