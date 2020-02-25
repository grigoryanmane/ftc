package aca.project.ftc.service;

import aca.project.ftc.auth.JwtHelper;
import aca.project.ftc.auth.JwtUserDetailsService;
import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.dto.request.user.SignupRequest;
import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.dto.request.user.UserEditRequest;
import aca.project.ftc.model.dto.response.user.AuthenticationResponseDto;
import aca.project.ftc.repository.NotificationRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public AuthenticationResponseDto signup(SignupRequest signupRequest) {
        UserModel user = setUserData(signupRequest);
        try {
            authenticate(user.getUsername(), signupRequest.getPassword());
        } catch (Exception e) {
            //TODO: Log the exception
            throw new UserNotFound("INVALID_CREDENTIALS");
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
            throw new UserNotFound("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        Optional<UserModel> userModel = userRepository.findByUsername(authenticationRequestDto.getUsername());
        UserResponseDto userResponseDto = userService.getUserResponseData(userModel.get());
        return new AuthenticationResponseDto(userResponseDto, token);
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    public UserModel setUserData(SignupRequest signupRequest) {
        try {
            UserModel userModel = new UserModel();
            userModel.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userModel.setLastName(signupRequest.getLastName());
            userModel.setFirstName(signupRequest.getFirstName());
            userModel.setPhoneNumber(signupRequest.getPhoneNumber());
            userModel.setBirthDate((signupRequest.getBirthDate()));
            userModel.setIsCompany(signupRequest.getIsCompany());
            userModel.setRegion(signupRequest.getRegion());
            userModel.setUsername(signupRequest.getUsername());
            userModel.setCompanyName(signupRequest.getCompanyName());
            userModel.setGender(signupRequest.getGender());
            userRepository.save(userModel);
            return userModel;
        } catch (Exception e) {
            //TODO:: CHANGE THE EXCEPTION
            throw new UserNotFound("INVALID_REQUEST");
        }

    }


}
