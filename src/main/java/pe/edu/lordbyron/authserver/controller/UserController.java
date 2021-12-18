package pe.edu.lordbyron.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pe.edu.lordbyron.authserver.dto.RoleToUserDto;
import pe.edu.lordbyron.authserver.model.Employee;
import pe.edu.lordbyron.authserver.model.Role;
import pe.edu.lordbyron.authserver.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<Employee>> getUsers () {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<Employee> saveUser (@RequestBody Employee employee) {
        var uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/user/save")
                .toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(employee));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveUser (@RequestBody Role role) {
        var uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/role/save")
                .toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> saveUser (@RequestBody RoleToUserDto roleToUserDto) {
        userService.addRoleToUser(roleToUserDto.getUsername(), roleToUserDto.getRoleName());
        return ResponseEntity.ok().build();

    }
}
