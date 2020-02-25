package aca.project.ftc.model.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class UserResponseDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    private String phoneNumber;
    private Boolean isCompany;
    private String companyName;
    private double rating;
    private int ratingCount;
    private String region;
    private String gender;
}
