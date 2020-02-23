package aca.project.ftc.controller;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.model.response.UserEditResponseDto;
import aca.project.ftc.repository.UserRepository;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



}
