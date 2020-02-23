package aca.project.ftc.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponseDto {

    private User user;

    private String token;

    public SignupResponseDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

}
