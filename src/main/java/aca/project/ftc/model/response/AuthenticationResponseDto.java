package aca.project.ftc.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDto {

    private User user;

    private String token;

    public AuthenticationResponseDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
