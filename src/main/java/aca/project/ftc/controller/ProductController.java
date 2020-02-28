package aca.project.ftc.controller;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.service.ProductService;
import aca.project.ftc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/product")
@CrossOrigin(value = "*", origins = "*", allowedHeaders = "*")
@PermitAll
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ProductResponseDto>> getUserProductList(@PathVariable Long id) {
        List<ProductResponseDto> userProductList = productService.getUserProductList(id);
        return ResponseEntity.ok(userProductList);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> userProductList = productService.getAllProducts();
        return ResponseEntity.ok(userProductList);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<List<ProductResponseDto>> editProduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        List<ProductResponseDto> productResponseDto = productService.editProduct(productRequestDto, id);
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
