package pe.edu.lordbyron.authserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.edu.lordbyron.authserver.model.Employee;
import pe.edu.lordbyron.authserver.model.Role;
import pe.edu.lordbyron.authserver.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//
//			userService.saveUser(new Employee(null, "Johson Rodriguez", "jrodriguez", "asdf1234", new ArrayList<>(), true));
//			userService.saveUser(new Employee(null, "Luis Flores", "lflores", "asdf1234", new ArrayList<>(), true));
//
//			userService.addRoleToUser("jrodriguez", "ROLE_ADMIN");
//			userService.addRoleToUser("jrodriguez", "ROLE_USER");
//			userService.addRoleToUser("lflores", "ROLE_USER");
//		};
//	}
}
