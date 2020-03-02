package aca.project.ftc.service;


import aca.project.ftc.controller.ProductController;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductListResponseDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.model.entity.ProductModel;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.entity.UserProductModel;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductServiceUnitTest {

    @Autowired
    private ProductService productService;

    @Mock
    private UserProductRepository userProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void whenValidProduct_thenProductShouldBeAdded() {
        // Given
        long productId = 2421;
        ProductModel product = new ProductModel();

        Integer page = 2;
        Integer size =5;
        Long filterProduct = 4L;
        Boolean isActive = true;

        UserProductModel userProductModel = new UserProductModel();

        long userId = 232434;
        UserModel user = new UserModel();

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setAmount(23.34);
        productRequestDto.setDescription("mock desc");
        productRequestDto.setUserId(userId);
        productRequestDto.setProductId(productId);

        doNothing().when(productService).validAddRequest(productRequestDto);
        doReturn(product).when(productRepository).findById(productId);
        doReturn(user).when(userRepository).findById(userId);
        doReturn(userProductModel).when(userProductRepository).save(userProductModel);

        // When
        ProductListResponseDto productListResponseDto = productService.addProduct(productRequestDto, page, size, filterProduct, isActive);

        // Then
        verify(productService).validAddRequest(productRequestDto);
        verify(productRepository).findById(productId);
        verify(userRepository).findById(userId);
        verify(userProductRepository).save(userProductModel);

//        assertThat();
    }
}
