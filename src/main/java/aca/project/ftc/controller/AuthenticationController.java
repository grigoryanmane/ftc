package aca.project.ftc.controller;

import aca.project.ftc.model.request.LoginRequest;
import aca.project.ftc.model.request.SignupRequest;
import aca.project.ftc.model.response.SignupResponseDto;
import aca.project.ftc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping(value = "api/v1/signup", method = RequestMethod.POST)
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequest signupRequest) {
        String token = authenticationService.signup(signupRequest);
        return ResponseEntity.ok(new SignupResponseDto(token));
    }

    @RequestMapping(value = "/api/v1/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.login(loginRequest);
        return ResponseEntity.ok(new SignupResponseDto(token));
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