package aca.project.ftc.service;


import aca.project.ftc.exception.UnauthorizedRequestException;
import aca.project.ftc.exception.UserNotFoundException;
import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.request.user.ResetRequestDto;
import aca.project.ftc.model.dto.request.user.UserEditRequest;
import aca.project.ftc.model.dto.request.user.UsernameCheckDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel editUser(UserEditRequest userEditRequest, Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            try {
                authenticationService.authenticate(user.get().getUsername(), userEditRequest.getPassword());
            } catch (Exception e) {
                throw new UnauthorizedRequestException("INVALID_CREDENTIALS");
            }
            return validateUserData(userEditRequest, user.get());
        }
        throw new UserNotFoundException("USER_NOT_FOUND");
    }

    public UserResponseDto resetPassword(ResetRequestDto resetRequestDto, Long id) {
        if (userRepository.existsById(id)) {
            try {
                authenticationService.authenticate(resetRequestDto.getUsername(), resetRequestDto.getPassword());
            } catch (Exception e) {
                throw new UserNotFoundException("INVALID_CREDENTIALS");
            }
            UserModel userModel = userRepository.findById(id).get();
            userModel.setPassword(passwordEncoder.encode(resetRequestDto.getNewPassword()));
            userRepository.save(userModel);
            return getUserResponseData(userModel);
        }
        throw new UserNotFoundException("INVALID_CREDENTIALS");
    }

    public Boolean checkUsername(UsernameCheckDto usernameCheckDto) {
        return userRepository.existsByUsername(usernameCheckDto.getUsername());
    }

    public UserResponseDto deleteUser(AuthenticationRequestDto authenticationRequestDto, Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        UserResponseDto userResponseDto;
        if (user.isPresent() && user.get().getUsername().equals(authenticationRequestDto.getUsername())) {
            try {
                authenticationService.authenticate(user.get().getUsername(), authenticationRequestDto.getPassword());
            } catch (Exception e) {
                throw new UnauthorizedRequestException("INVALID_CREDENTIALS");
            }
            userResponseDto = getUserResponseData(user.get());
            userRepository.deleteById(id);
            return userResponseDto;
        }
        throw new UserNotFoundException("USER_NOT_FOUND_WITH_ID_AND_USERNAME");

    }

    public UserResponseDto getUserData(UserDetails userDetails) {
        Optional<UserModel> userModel = userRepository.findByUsername(userDetails.getUsername());
        if (userModel.isPresent()) {
            return getUserResponseData(userModel.get());
        }
        throw new UserNotFoundException("USER_NOT_FOUND_WITH_USERNAME");
    }


    public UserModel validateUserData(UserEditRequest userEditRequest, UserModel userModel) {

        if (!userEditRequest.getFirstName().isEmpty()) {
            authenticationService.isValidFirstName(userEditRequest.getFirstName());
            userModel.setFirstName(userEditRequest.getFirstName());
        }
        if (!userEditRequest.getLastName().isEmpty()) {
            authenticationService.isValidLastName(userEditRequest.getLastName());
            userModel.setLastName(userEditRequest.getLastName());
        }
        if (!userEditRequest.getPhoneNumber().isEmpty()) {
            authenticationService.isValidPhoneNumber(userEditRequest.getPhoneNumber());
            userModel.setPhoneNumber(userEditRequest.getPhoneNumber());
        }
        if (!(userEditRequest.getRegion() == null)) {
            authenticationService.isValidRegion(userEditRequest.getRegion());
            userModel.setRegion(Region.valueOf(userEditRequest.getRegion().toUpperCase()));
        }
        if (!(userEditRequest.getGender() == null)) {
            authenticationService.isValidGender(userEditRequest.getGender());
            userModel.setGender(Gender.valueOf(userEditRequest.getGender().toUpperCase()));
        }
        if (!(userEditRequest.getBirthDate() == null)) {
            authenticationService.isValidBirthDate(userEditRequest.getBirthDate());
            Date birthDate = null;
            try {
                birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(userEditRequest.getBirthDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        userRepository.save(userModel);
        return userModel;

    }


    public UserResponseDto getUserResponseData(UserModel userModel) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(userModel.getId());
        userResponseDto.setUsername(userModel.getUsername());
        userResponseDto.setFirstName(userModel.getFirstName());
        userResponseDto.setLastName(userModel.getLastName());
        userResponseDto.setBirthDate(userModel.getBirthDate());
        userResponseDto.setPhoneNumber(userModel.getPhoneNumber());
        userResponseDto.setIsCompany(userModel.getIsCompany());
        userResponseDto.setCompanyName(userModel.getCompanyName());
        userResponseDto.setRating(userModel.getRating());
        userResponseDto.setRatingCount(userModel.getRatingCount());
        userResponseDto.setGender(userModel.getGender().getKey());
        userResponseDto.setRegion(userModel.getRegion().getKey());
        return userResponseDto;
    }


}
