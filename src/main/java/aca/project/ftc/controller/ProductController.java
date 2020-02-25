package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.product.ProductFilterRequestDto;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.service.ProductService;
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

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDto> editProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.editProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping(value = "/{id}/{offset}")
    public ResponseEntity<List<ProductResponseDto>> userProduct(@PathVariable Long id, Integer offset) {
        List<ProductResponseDto> userProductModel = productService.userProduct(id, offset);
        return ResponseEntity.ok(userProductModel);
    }

//    @PostMapping(value = "/filter")
//    public ResponseEntity<List<ProductResponseDto>> filterByName(@RequestBody ProductFilterRequestDto productFilterRequestDto) {
//        List<ProductResponseDto> userProductModel = productService.filter(productFilterRequestDto);
//        return ResponseEntity.ok(userProductModel);
//    }

}
