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
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.addProduct(productRequestDto);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping(value = "/{id}", params = {"page", "size"})
    public ResponseEntity<ProductListResponseDto> getUserProductList(@PathVariable Long id,
                                                                     @RequestParam("page") Integer page,
                                                                     @RequestParam("size") Integer size) {
        ProductListResponseDto userProductList = productService.getUserProductList(id, page, size);
        return ResponseEntity.ok(userProductList);
    }

    @GetMapping(value = "", params = {"page", "size"})
    public ResponseEntity<ProductListResponseDto> getAllProducts(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        ProductListResponseDto productListResponseDto = productService.getAllProducts(page, size);
        return ResponseEntity.ok(productListResponseDto);
    }


    @PutMapping(value = "/{id}", params = {"page", "size"})
    public ResponseEntity<ProductListResponseDto> editProduct(@RequestBody ProductRequestDto productRequestDto,
                                                              @PathVariable Long id,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        ProductListResponseDto productListResponseDto = productService.editProduct(productRequestDto, id, page, size);
        return ResponseEntity.ok(productListResponseDto);
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
