package aca.project.ftc.controller;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.UserProductModel;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.model.request.UserProductRequest;
import aca.project.ftc.model.response.AuthenticationResponseDto;
import aca.project.ftc.model.response.UserEditResponseDto;
import aca.project.ftc.model.response.UserProductResponseDto;
import aca.project.ftc.repository.UserRepository;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import aca.project.ftc.model.response.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
//@CrossOrigin(origins = "*",exposedHeaders= "Access-Control-Expose-Headers", allowedHeaders="*" )
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/v1/user/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<UserProductResponseDto>> userProducts(@PathVariable Long id) {
        List<UserProductResponseDto> userProductModel = userService.userProducts(id);
        return ResponseEntity.ok(userProductModel);
    }

    @RequestMapping(value = "/api/v1/user/product/add", method = RequestMethod.POST)
    public ResponseEntity<UserProductResponseDto> addUserProduct(@RequestBody UserProductRequest userProductRequest) {
        UserProductResponseDto userProductResponseDto = userService.addUserProduct(userProductRequest);
        return ResponseEntity.ok(userProductResponseDto);
    }

    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> getUserDara(@PathVariable Long id) {
        User user = userService.getUserData(id);
        return ResponseEntity.ok(user);
    }


}
