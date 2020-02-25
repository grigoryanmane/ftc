package aca.project.ftc.controller;


import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.dto.request.DeleteUserRequest;
import aca.project.ftc.model.dto.request.UserEditRequest;
import aca.project.ftc.model.dto.request.UserProductRequest;
import aca.project.ftc.model.dto.response.MessageResponseDto;
import aca.project.ftc.model.dto.response.User;
import aca.project.ftc.model.dto.response.UserEditResponseDto;
import aca.project.ftc.model.dto.response.UserProductResponseDto;
import aca.project.ftc.repository.UserRepository;
import aca.project.ftc.service.AuthenticationService;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<MessageResponseDto> deleteUser(@RequestBody DeleteUserRequest deleteUserRequest, @PathVariable Long id) {
        String response = authenticationService.deleteUser(deleteUserRequest, id);
        return ResponseEntity.ok(new MessageResponseDto(response));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest userEditRequest, @PathVariable Long id) {
        UserModel user = authenticationService.editUser(userEditRequest, id);
        return ResponseEntity.ok(new UserEditResponseDto(user.getFirstName(), user.getLastName(), user.getPhoneNumber(),
                user.getRegion()
        ));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserProductResponseDto>> userProducts(@PathVariable Long id) {
        List<UserProductResponseDto> userProductModel = userService.userProducts(id);
        return ResponseEntity.ok(userProductModel);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<UserProductResponseDto> addUserProduct(@RequestBody UserProductRequest userProductRequest) {
        UserProductResponseDto userProductResponseDto = userService.addUserProduct(userProductRequest);
        return ResponseEntity.ok(userProductResponseDto);
    }


    @DeleteMapping(value = "/api/v1/user/product")
    public ResponseEntity<UserProductResponseDto> editUserProduct(@RequestBody UserProductRequest userProductRequest) {
        UserProductResponseDto userProductResponseDto = userService.editUserProduct(userProductRequest);
        return ResponseEntity.ok(userProductResponseDto);
    }

    @RequestMapping(value = "/api/v1/user/{id}", method = RequestMethod.POST)
    public ResponseEntity<User> getUserDara(@PathVariable Long id) {
        User user = userService.getUserData(id);
        return ResponseEntity.ok(user);
    }


}
