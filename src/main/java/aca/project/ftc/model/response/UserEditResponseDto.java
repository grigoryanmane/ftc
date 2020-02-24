package aca.project.ftc.model.response;

import aca.project.ftc.model.constants.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserEditResponseDto {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Region region;

}
