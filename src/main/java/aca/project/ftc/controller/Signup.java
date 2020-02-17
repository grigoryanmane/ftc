package aca.project.ftc.controller;

import aca.project.ftc.model.SignupRequest;
import aca.project.ftc.model.User;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin

public class Signup {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "api/v1/signup", method = RequestMethod.POST)
    public User signup(@RequestBody SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setLastName(signupRequest.getLastName());
        user.setFirstName(signupRequest.getFirstName());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setAddress(signupRequest.getAddress());
        user.setBirthDate((signupRequest.getBirthDate()));
        user.setCity(signupRequest.getCity());
        user.setType(signupRequest.getType());
        user.setRegion(signupRequest.getRegion());
        user.setActive(true);
        user.setUsername(signupRequest.getUsername());
        user.setCompanyName(signupRequest.getCompanyName());
        user.setGender(signupRequest.getGender());
        userRepository.save(user);
        return user;
    }
}
