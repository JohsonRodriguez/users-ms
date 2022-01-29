package pe.edu.lordbyron.authserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleToUserDto {

    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Role name is mandatory")
    private String roleName;
}
