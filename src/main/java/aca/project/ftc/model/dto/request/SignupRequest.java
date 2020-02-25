package aca.project.ftc.model.dto.request;

import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class SignupRequest {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Boolean isCompany;

    private String companyName;

    private String phoneNumber;

    private Gender gender;

    private Date birthDate;

    private Region region;

}
