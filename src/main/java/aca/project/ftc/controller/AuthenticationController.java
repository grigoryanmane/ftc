package aca.project.ftc.controller;

import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.request.DeleteUserRequest;
import aca.project.ftc.model.request.LoginRequest;
import aca.project.ftc.model.request.SignupRequest;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.model.response.AuthenticationResponseDto;
import aca.project.ftc.model.response.MessageResponseDto;
import aca.project.ftc.model.response.UserEditResponseDto;
import aca.project.ftc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "api/v1/signup", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponseDto> signup(@RequestBody SignupRequest signupRequest) {
        AuthenticationResponseDto result = authenticationService.signup(signupRequest);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody LoginRequest loginRequest) {
        AuthenticationResponseDto result = authenticationService.login(loginRequest);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/api/v1/user/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<MessageResponseDto> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest, @PathVariable Long id) {
        String response = authenticationService.deleteUser(deleteUserRequest, id);
        return ResponseEntity.ok(new MessageResponseDto(response));
    }

    @RequestMapping(value = "/api/v1/user/edit/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest userEditRequest, @PathVariable Long id) {
        Optional<UserModel> user = authenticationService.editUser(userEditRequest, id);
        UserModel editUser = user.get();
        return ResponseEntity.ok(new UserEditResponseDto(editUser.getFirstName(), editUser.getLastName(), editUser.getPhoneNumber(),
                editUser.getRegion()
        ));
    }

    //TODO:: REMOVE THIS IN THE FUTURE, THIS IS HERE FOR TESTING PURPOSES ONLY
    @RequestMapping(value = "/api/v1/test", method = RequestMethod.GET)
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("hello");
    }

    //TODO:: WHERE THIS NEEDS TO BE IN ORDER TO WORK PROPERLY, WHEN NO TOKEN IS AVAILABLE OR TOKEN IS EXPIRED
    @ExceptionHandler(BadCredentialsException.class)
    public String authError() {
        return "unauthorized";
    }

}
