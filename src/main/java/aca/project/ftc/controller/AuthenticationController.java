package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.user.SignupRequest;
import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.response.user.AuthenticationResponseDto;
import aca.project.ftc.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin(value = "*", origins = "*")
@RequestMapping(value = "api/v1")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponseDto> signup(@RequestBody SignupRequest signupRequest) throws ParseException {
        AuthenticationResponseDto result = authenticationService.signup(signupRequest);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto result = authenticationService.login(authenticationRequestDto);
        return ResponseEntity.ok(result);
    }

    //TODO:: WHERE THIS NEEDS TO BE IN ORDER TO WORK PROPERLY, WHEN NO TOKEN IS AVAILABLE OR TOKEN IS EXPIRED
    @ExceptionHandler(BadCredentialsException.class)
    public String authError() {
        return "unauthorized";
    }

}
