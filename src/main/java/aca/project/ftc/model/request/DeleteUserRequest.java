package aca.project.ftc.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {

    String username;

    String password;
}
