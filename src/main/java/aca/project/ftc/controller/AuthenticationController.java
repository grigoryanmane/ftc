package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.LoginRequest;
import aca.project.ftc.model.dto.request.SignupRequest;
import aca.project.ftc.model.dto.response.AuthenticationResponseDto;
import aca.project.ftc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
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


    //TODO:: WHERE THIS NEEDS TO BE IN ORDER TO WORK PROPERLY, WHEN NO TOKEN IS AVAILABLE OR TOKEN IS EXPIRED
    @ExceptionHandler(BadCredentialsException.class)
    public String authError() {
        return "unauthorized";
    }

}
