package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.product.ProductAddDto;
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
@RequestMapping(value = "/api/v1/product")
@CrossOrigin(value = "*", origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductAddDto productAddDto) {
        ProductResponseDto productResponseDto = productService.addProduct(productAddDto);
        return ResponseEntity.ok(productResponseDto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDto> editProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.editProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.deleteProduct(id);
        return ResponseEntity.ok(productResponseDto);
    }

//    @PostMapping(value = "/filter")
//    public ResponseEntity<List<ProductResponseDto>> filterByName(@RequestBody ProductFilterRequestDto productFilterRequestDto) {
//        List<ProductResponseDto> userProductModel = productService.filter(productFilterRequestDto);
//        return ResponseEntity.ok(userProductModel);
//    }

}
