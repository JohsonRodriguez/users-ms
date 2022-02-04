package pe.edu.lordbyron.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.lordbyron.authserver.dto.RoleToUserDto;
import pe.edu.lordbyron.authserver.dto.UpdatePasswordDto;
import pe.edu.lordbyron.authserver.exception.UserRepositoryException;
import pe.edu.lordbyron.authserver.model.Employee;
import pe.edu.lordbyron.authserver.model.Role;
import pe.edu.lordbyron.authserver.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/list")
    public ResponseEntity<List<Employee>> getUsers () {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser (@Valid @RequestBody Employee employee) {
        return userService.saveUser(employee);
    }

    @PostMapping("/roles")
    public ResponseEntity<?> saveRole (@Valid @RequestBody Role role) {
        return userService.saveRole(role);
    }

    @PostMapping("/roles/add-to-user")
    public ResponseEntity<?> addRoleToUser (@Valid @RequestBody RoleToUserDto roleToUserDto) {
        userService.addRoleToUser(roleToUserDto.getUsername(), roleToUserDto.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/remove-from-user")
    public ResponseEntity<?> removeRoleFromUser (@Valid @RequestBody RoleToUserDto roleToUserDto) {
        userService.removeRoleToUser(roleToUserDto.getUsername(), roleToUserDto.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/update")
    public ResponseEntity<?> updateUser (@RequestBody Employee employee) {
        return userService.updateUser(employee);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserByUserName(@RequestParam String username) {
        if (username == null || username.isBlank()) throw new UserRepositoryException("EL nombre de usuario es obligatorio!");
        return userService.getUser(username);
    }

    @PutMapping("/users/status")
    public ResponseEntity<?> changeUserStatus(@RequestParam String username,
                                              @RequestParam boolean isEnabled) {
        if (username == null || username.isBlank()) throw new UserRepositoryException("El nombre de usuario es obligatorio!");
        return userService.changeUserStatus(username, isEnabled);
    }

    @PostMapping("/users/change-password")
    public ResponseEntity<?> updateUser (@Valid @RequestBody UpdatePasswordDto updatePassword) {
        return userService.changePassword(updatePassword);
    }

}
