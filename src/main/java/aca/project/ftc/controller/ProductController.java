package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.product.ProductRequestDto;
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
    public ResponseEntity<UserProductResponseDto> addUserProduct(@RequestBody ProductRequestDto productRequestDto) {
        UserProductResponseDto userProductResponseDto = userService.addUserProduct(productRequestDto);
        return ResponseEntity.ok(userProductResponseDto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<UserProductResponseDto> editUserProduct(@RequestBody ProductRequestDto productRequestDto, @Long id) {
        UserProductResponseDto userProductResponseDto = userService.editUserProduct(productRequestDto);
        return ResponseEntity.ok(userProductResponseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<UserProductResponseDto>> userProducts(@PathVariable Long id) {
        List<UserProductResponseDto> userProductModel = userService.userProducts(id);
        return ResponseEntity.ok(userProductModel);
    }


}
