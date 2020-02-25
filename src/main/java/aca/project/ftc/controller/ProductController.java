package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.UserProductRequest;
import aca.project.ftc.model.dto.response.User;
import aca.project.ftc.model.dto.response.UserProductResponseDto;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    @Autowired
    private UserService userService;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserProductResponseDto>> userProducts(@PathVariable Long id) {
        List<UserProductResponseDto> userProductModel = userService.userProducts(id);
        return ResponseEntity.ok(userProductModel);
    }


}
