package pe.edu.lordbyron.authserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdatePasswordDto {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
