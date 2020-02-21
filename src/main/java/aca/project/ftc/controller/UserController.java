package aca.project.ftc.controller;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.model.response.UserEditResponseDto;
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

    @RequestMapping(value = "/api/v1/user/edit/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> editUser(@RequestBody UserEditRequest userEditRequest, @PathVariable Long id) {
        Optional<UserModel> user = userService.editUser(userEditRequest, id);
        if (user.isPresent()) {
            UserModel editUser = user.get();
            return ResponseEntity.ok(new UserEditResponseDto(editUser.getFirstName(), editUser.getLastName(), editUser.getPhoneNumber(),
                    editUser.getRegion()
            ));
        } else {
            throw new UserNotFound();
        }

    }

}