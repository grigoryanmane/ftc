package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.request.user.ResetRequestDto;
import aca.project.ftc.model.dto.request.user.UserEditRequest;
import aca.project.ftc.model.dto.request.user.UsernameCheckDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
        //TODO::CHANGE THE LOGIC IN HERE
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            try {
                authenticationService.authenticate(user.get().getUsername(), userEditRequest.getPassword());
            } catch (Exception e) {
                throw new UserNotFound("INVALID_CREDENTIALS");
            }
            return checkUserData(userEditRequest, user.get());
        }
        throw new UserNotFound("USER_NOT_FOUND");


    }

    public UserResponseDto resetPassword(ResetRequestDto resetRequestDto, Long id) {
        if (userRepository.existsById(id)) {
            try {
                authenticationService.authenticate(resetRequestDto.getUsername(), resetRequestDto.getPassword());
            } catch (Exception e) {
                throw new UserNotFound("INVALID_CREDENTIALS");
            }
            UserModel userModel = userRepository.findById(id).get();
            userModel.setPassword(passwordEncoder.encode(resetRequestDto.getNewPassword()));
            userRepository.save(userModel);
            return getUserResponseData(userModel);
        }
        throw new UserNotFound("INVALID_CREDENTIALS");
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
                throw new UserNotFound("INVALID_CREDENTIALS");
            }
            userResponseDto = getUserResponseData(user.get());
            userRepository.deleteById(id);
            return userResponseDto;
        } else {
            throw new UserNotFound("User Not Found");
        }
        //TODO::SHOULD I CHANGE THIS? Do not forget to remove token from front end and logout the user

    }

    //TODO:: Add three calls here: checkUsername,addNotification,checkNotification


    public UserResponseDto getUserData(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent()) {
            return getUserResponseData(userModel.get());
        }
        throw new UserNotFound("USER_NOT_FOUND");
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
