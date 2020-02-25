package aca.project.ftc.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {

    String username;

    String password;
}
