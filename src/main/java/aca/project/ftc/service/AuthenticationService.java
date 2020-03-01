package aca.project.ftc.service;

import aca.project.ftc.auth.JwtHelper;
import aca.project.ftc.auth.JwtUserDetailsService;
import aca.project.ftc.exception.ExistingUserException;
import aca.project.ftc.exception.InvalidParameters;
import aca.project.ftc.exception.UnauthorizedRequest;
import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import aca.project.ftc.model.dto.request.user.SignupRequest;
import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.dto.response.user.AuthenticationResponseDto;
import aca.project.ftc.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.openmbean.InvalidKeyException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public AuthenticationResponseDto signup(SignupRequest signupRequest)  {
        UserModel user = setUserData(signupRequest);
        try {
            authenticate(user.getUsername(), signupRequest.getPassword());
        } catch (Exception e) {
            throw new UnauthorizedRequest("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        UserResponseDto userResponseDto = userService.getUserResponseData(user);
        return new AuthenticationResponseDto(userResponseDto, token);
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        try {
            authenticate(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword());
        } catch (Exception e) {
            //TODO Log the exception and throw correct exception
            throw new UnauthorizedRequest("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        Optional<UserModel> userModel = userRepository.findByUsername(authenticationRequestDto.getUsername());
        UserResponseDto userResponseDto = userService.getUserResponseData(userModel.get());
        return new AuthenticationResponseDto(userResponseDto, token);
    }

    public void authenticate(String username, String password) throws UnauthorizedRequest {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new UnauthorizedRequest("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedRequest("INVALID_CREDENTIALS", e);
        }
    }

    public UserModel setUserData(SignupRequest signupRequest) {
        isValidSignUp(signupRequest);
        UserModel userModel = new UserModel();
        userModel.setUsername(signupRequest.getUsername());
        userModel.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userModel.setLastName(signupRequest.getLastName());
        userModel.setFirstName(signupRequest.getFirstName());
        userModel.setPhoneNumber(signupRequest.getPhoneNumber());
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(signupRequest.getBirthDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userModel.setBirthDate(birthDate);
        userModel.setIsCompany(signupRequest.getIsCompany());
        userModel.setRegion(Region.valueOf(signupRequest.getRegion().toUpperCase()));
        userModel.setCompanyName(signupRequest.getCompanyName());
        userModel.setGender(Gender.valueOf(signupRequest.getGender().toUpperCase()));
        userRepository.save(userModel);
        return userModel;
    }

    public boolean isValidUsername(String username) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (userRepository.existsByUsername(username)) {
            throw new ExistingUserException("USER_EXISTS_WITH_THAT_EMAIL");
        }
        if (matcher.matches()) {
            return true;
        }
        throw new InvalidParameters("INVALID_USERNAME_PARAMETER");
    }

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches() && !password.toLowerCase().contains("password")
                && !password.toLowerCase().contains("password123")
                && !password.contains(" ")) {
            return true;
        }
        throw new InvalidParameters("INVALID_PASSWORD_PARAMETER");
    }

    public boolean isValidFirstName(String firstName) {
        String regex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,23}[a-zA-Z]{0,1})";
        Pattern pattern = Pattern.compile(regex);
        Matcher firstNameMatches = pattern.matcher(firstName);
        if ((!firstNameMatches.matches() || firstName.length() < 3)) {
            throw new InvalidParameters("INVALID_FIRSTNAME_PARAMETER");
        }
        return true;
    }

    public boolean isValidLastName(String lastName) {
        String regex = "([a-zA-z]{1}[a-zA-z_'-,.]{0,23}[a-zA-Z]{0,1})";
        Pattern pattern = Pattern.compile(regex);
        Matcher lastNameMatches = pattern.matcher(lastName);
        if ((!lastNameMatches.matches() || lastName.length() < 3)) {
            throw new InvalidParameters("INVALID_LASTNAME_PARAMETER");
        }
        return true;
    }
    public boolean isValidSignUp(SignupRequest signupRequest) {
        return isValidPassword(signupRequest.getPassword()) &&
                isValidUsername(signupRequest.getUsername()) &&
                isValidBirthDate(signupRequest.getBirthDate()) &&
                isValidFirstName(signupRequest.getFirstName()) &&
                isValidLastName(signupRequest.getLastName()) &&
                isValidPhoneNumber(signupRequest.getPhoneNumber()) &&
                isValidBirthDate(signupRequest.getBirthDate()) &&
                isValidGender(signupRequest.getGender()) &&
                isValidRegion(signupRequest.getRegion());
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "(\\+374)[0-9]{8}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new InvalidParameters("INVALID_PHONENUMBER_PARAMETER");
        }
        return true;
    }

    public boolean isValidBirthDate(String birthDate) {
        try {
            Date sdf = new SimpleDateFormat("yyyy-MM-dd").parse(birthDate);
            return true;
        } catch (ParseException e) {
            throw new InvalidParameters("INVALID_BIRTHDATE_PARAMETER");
        }
    }

    public boolean isValidRegion(String region) {
        if (!Arrays.stream(Region.values())
                .map(Region::name)
                .collect(Collectors.toSet())
                .contains(region.toUpperCase())) {
            throw new InvalidParameters("INVALID_REGION_PARAMETER");
        }
        return true;
    }

    public boolean isValidGender(String gender) {
        if (!Arrays.stream(Gender.values())
                .map(Gender::name)
                .collect(Collectors.toSet())
                .contains(gender.toUpperCase())) {
            throw new InvalidParameters("INVALID_GENDER_PARAMETER");
        }
        return true;
    }

}
