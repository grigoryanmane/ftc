package aca.project.ftc.controller;


import aca.project.ftc.model.dto.request.user.AuthenticationRequestDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.dto.request.user.UserEditRequest;
import aca.project.ftc.service.AuthenticationService;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin()
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> deleteUser(@RequestBody AuthenticationRequestDto authenticationRequestDto, @PathVariable Long id) {
        UserResponseDto userResponseDto = userService.deleteUser(authenticationRequestDto, id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> editUser(@RequestBody UserEditRequest userEditRequest, @PathVariable Long id) {
        UserModel user = userService.editUser(userEditRequest, id);
        UserResponseDto userResponseDto = userService.getUserResponseData(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getUserDara(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUserData(id);
        return ResponseEntity.ok(userResponseDto);
    }


}
