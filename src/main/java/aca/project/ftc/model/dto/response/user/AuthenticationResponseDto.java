package aca.project.ftc.model.dto.response.user;

import aca.project.ftc.model.dto.response.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDto {

    private User user;

    private String token;

}
