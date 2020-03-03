package aca.project.ftc.controller;

import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductListResponseDto;
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
    public ResponseEntity<ProductListResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto,
                                                             @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "") Integer size,
                                                             @RequestParam(value = "productId", required = false, defaultValue = "") Long productId,
                                                             @RequestParam(value = "isActive", required = false, defaultValue = "") Boolean isActive) {
        ProductListResponseDto productListResponseDto = productService.addProduct(productRequestDto, page, size, productId, isActive);
        return ResponseEntity.ok(productListResponseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductListResponseDto> getUserProductList(@PathVariable Long id,
                                                                     @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "") Integer size,
                                                                     @RequestParam(value = "productId", required = false, defaultValue = "") Long productId,
                                                                     @RequestParam(value = "isActive", required = false, defaultValue = "") Boolean isActive) {
        ProductListResponseDto userProductList = productService.getUserProductList(id, page, size, productId, isActive);
        return ResponseEntity.ok(userProductList);
    }

    @GetMapping(value = "")
    public ResponseEntity<ProductListResponseDto> getAllProducts(
            @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "") Integer size,
            @RequestParam(value = "productId", required = false, defaultValue = "") Long productId) {
        ProductListResponseDto productListResponseDto = productService.getAllProducts(page, size, productId);
        return ResponseEntity.ok(productListResponseDto);
    }


    @PutMapping(value = "/{id}", params = {"page", "size", "productId", "isActive"})
    public ResponseEntity<ProductListResponseDto> editProduct(@RequestBody ProductRequestDto productRequestDto,
                                                              @PathVariable Long id,
                                                              @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
                                                              @RequestParam(value = "size", required = false, defaultValue = "") Integer size,
                                                              @RequestParam(value = "productId", required = false, defaultValue = "") Long productId,
                                                              @RequestParam(value = "isActive", required = false, defaultValue = "") Boolean isActive) {
        ProductListResponseDto productListResponseDto = productService.editProduct(productRequestDto, id, page, size, productId, isActive);
        return ResponseEntity.ok(productListResponseDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.deleteProduct(id);
        return ResponseEntity.ok(productResponseDto);
    }


}
