package aca.project.ftc.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDto {

    private UserResponseDto user;

    private String token;

}
