package aca.project.ftc.model.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequestDto {

    String username;

    String password;
}

