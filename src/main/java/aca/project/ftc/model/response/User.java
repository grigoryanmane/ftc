package aca.project.ftc.model.response;

import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import lombok.Getter;
import lombok.Setter;

import java.time.Period;
import java.util.Date;

@Getter
@Setter
public class User {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private Boolean isCompany;
    private String companyName;
    private double rating;
    private int ratingCount;
    private String region;
    private String gender;


}
