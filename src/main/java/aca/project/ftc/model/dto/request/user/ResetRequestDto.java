package aca.project.ftc.model.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetRequestDto {

    private String username;
    private String password;
    private String newPassword;

}
