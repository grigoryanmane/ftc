package aca.project.ftc.service;

import aca.project.ftc.auth.JwtHelper;
import aca.project.ftc.auth.JwtUserDetailsService;
import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.dto.request.DeleteUserRequest;
import aca.project.ftc.model.dto.request.LoginRequest;
import aca.project.ftc.model.dto.request.SignupRequest;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.dto.request.UserEditRequest;
import aca.project.ftc.model.dto.response.AuthenticationResponseDto;
import aca.project.ftc.model.dto.response.User;
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
    private NotificationRepository notificationRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    public AuthenticationResponseDto signup(SignupRequest signupRequest) {
        UserModel user = new UserModel();
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setLastName(signupRequest.getLastName());
        user.setFirstName(signupRequest.getFirstName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setBirthDate((signupRequest.getBirthDate()));
        user.setIsCompany(signupRequest.getIsCompany());
        user.setRegion(signupRequest.getRegion());
        user.setUsername(signupRequest.getUsername());
        user.setCompanyName(signupRequest.getCompanyName());
        user.setGender(signupRequest.getGender());
        userRepository.save(user);

        try {
            authenticate(user.getUsername(), signupRequest.getPassword());
        } catch (Exception e) {
            //TODO: Log the exception
            throw new UserNotFound("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        User userData = getUserResponseData(user);
        return new AuthenticationResponseDto(userData, token);
    }

    public AuthenticationResponseDto login(LoginRequest loginRequest) {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            //TODO Log the exception and throw correct exception
            throw new UserNotFound("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        Optional<UserModel> userModel = userRepository.findByUsername(loginRequest.getUsername());
        User userData = getUserResponseData(userModel.get());
        return new AuthenticationResponseDto(userData, token);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public UserModel editUser(UserEditRequest userEditRequest, Long id) {
        //TODO::CHANGE THE LOGIC IN HERE
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            try {
                authenticate(user.get().getUsername(), userEditRequest.getPassword());
            } catch (Exception e) {
                throw new UserNotFound("INVALID_CREDENTIALS");
            }
            return checkUserData(userEditRequest, user.get());
        } else {
            throw new UserNotFound("USER_NOT_FOUND");
        }

    }

    public String deleteUser(DeleteUserRequest deleteUserRequest, Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent() && user.get().getUsername().equals(deleteUserRequest.getUsername())) {
            try {
                authenticate(user.get().getUsername(), deleteUserRequest.getPassword());
            } catch (Exception e) {
                throw new UserNotFound("INVALID_CREDENTIALS");
            }
            notificationRepository.deleteAllByReceiverId(id);
            notificationRepository.deleteAllBySenderId(id);
            userProductRepository.deleteAllByUserId(id);
            userRepository.deleteById(id);

        } else {
            throw new UserNotFound("User Not Found");
        }
        //TODO::SHOULD I CHANGE THIS? Do not forget to remove token from front end and logout the user
        return "User Successfully Deleted";

    }

    public User getUserResponseData(UserModel userModel) {
        User user = new User();
        user.setId(userModel.getId());
        user.setUsername(userModel.getUsername());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setBirthDate(userModel.getBirthDate());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setIsCompany(userModel.getIsCompany());
        user.setCompanyName(userModel.getCompanyName());
        user.setRating(userModel.getRating());
        user.setRatingCount(userModel.getRatingCount());
        user.setGender(userModel.getGender().getKey());
        user.setRegion(userModel.getRegion().getKey());
        return user;
    }

    public UserModel checkUserData(UserEditRequest userEditRequest, UserModel userModel) {
        if (!userEditRequest.getFirstName().isEmpty()) {
            userModel.setFirstName(userEditRequest.getFirstName());
        }
        if (!userEditRequest.getLastName().isEmpty()) {
            userModel.setLastName(userEditRequest.getLastName());
        }
        if (!userEditRequest.getPhoneNumber().isEmpty()) {
            userModel.setPhoneNumber(userEditRequest.getPhoneNumber());
        }
        if (!(userEditRequest.getRegion() == null)) {
            userModel.setRegion(userEditRequest.getRegion());
        }
        if (!(userEditRequest.getGender() == null)) {
            userModel.setGender(userEditRequest.getGender());
        }
        if (!(userEditRequest.getBirthDate() == null)) {
            userModel.setBirthDate(userEditRequest.getBirthDate());
        }
        userRepository.save(userModel);
        return userModel;

    }


}
