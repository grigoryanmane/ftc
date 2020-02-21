package aca.project.ftc.service;

import aca.project.ftc.auth.JwtHelper;
import aca.project.ftc.auth.JwtUserDetailsService;
import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.request.LoginRequest;
import aca.project.ftc.model.request.SignupRequest;
import aca.project.ftc.model.UserModel;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String signup(SignupRequest signupRequest) {
        UserModel user = new UserModel();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setLastName(signupRequest.getLastName());
        user.setFirstName(signupRequest.getFirstName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setBirthDate((signupRequest.getBirthDate()));
        user.setIsCompany(signupRequest.getIsCompany());
        user.setRegion(signupRequest.getRegion());
        user.setUsername(signupRequest.getEmail());
        user.setCompanyName(signupRequest.getCompanyName());
        user.setGender(signupRequest.getGender());
        userRepository.save(user);

        try {
            authenticate(user.getUsername(), signupRequest.getPassword());
        } catch (Exception e) {
            //TODO: Log the exception
            throw new UserNotFound();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtHelper.generateToken(userDetails);
    }

    public String login(LoginRequest loginRequest) {
        try {
            authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        } catch (Exception e) {
            //TODO Log the exception and throe correct exception
            throw new UserNotFound();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        return jwtHelper.generateToken(userDetails);
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


}
