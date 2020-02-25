package aca.project.ftc.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponseDto {

    private User user;

    private String token;

//    public AuthenticationResponseDto(String token, User user) {
//
//    }

}
