package aca.project.ftc.model.request;

import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserEditRequest {

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Region region;
}
