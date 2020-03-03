package aca.project.ftc.service;


import aca.project.ftc.exception.InvalidParameterException;
import aca.project.ftc.exception.InvalidRequestException;
import aca.project.ftc.exception.ProductNotFoundException;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceUnitTest {

    @Mock
    private UserProductRepository userProductRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private ProductService productService;

    @Test
    public void whenValidProductThenProductShouldBeAdded() {
        // Given
        long productId = 2421;
        ProductModel product = new ProductModel();

        ProductListResponseDto productListExpectedDto = new ProductListResponseDto();
        Integer page = 2;
        Integer size = 5;
        Long filterProduct = 4L;
        Boolean isActive = true;

        UserProductModel userProductModel = new UserProductModel();

        long userId = 232434;
        UserModel user = new UserModel();
        user.setId(userId);

        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setAmount(23.34);
        productRequestDto.setDescription("mock desc");
        productRequestDto.setUserId(userId);
        productRequestDto.setProductId(productId);

        doNothing().when(productService).validAddRequest(productRequestDto);
        doReturn(Optional.of(product)).when(productRepository).findById(productId);
        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(userProductModel).when(userProductRepository).save(userProductModel);
        doReturn(productListExpectedDto).when(productService)
                .getUserProductList(productRequestDto.getUserId(), page, size, filterProduct, isActive);

        ArgumentCaptor<UserProductModel> argumentCaptor = ArgumentCaptor.forClass(UserProductModel.class);
        // When
        ProductListResponseDto productListActualDto = productService
                .addProduct(productRequestDto, page, size, filterProduct, isActive);

        // Then
        verify(productService).validAddRequest(productRequestDto);
        verify(productRepository).findById(productId);
        verify(userRepository).findById(userId);
        verify(userProductRepository).save(argumentCaptor.capture());
        verify(productService)
                .getUserProductList(productRequestDto.getUserId(), page, size, filterProduct, isActive);

        UserProductModel value = argumentCaptor.getValue();
        assertThat(value.getAmount()).isEqualTo(productRequestDto.getAmount());
        assertThat(productListActualDto).isEqualTo(productListExpectedDto);
    }


    @Test
    public void validateRequestWhenNullUserId() {
        // Given
        ProductRequestDto productRequestDto = new ProductRequestDto();

        // When
        Throwable t = catchThrowable(() -> productService.validAddRequest(productRequestDto));

        // Then
        assertThat(t).isNotNull();
        assertThat(t instanceof InvalidRequestException).isTrue();
    }

    @Test
    public void validateRequestWhenNullProductId() {
        // Given
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setUserId(3453L);

        // When
        Throwable t = catchThrowable(() -> productService.validAddRequest(productRequestDto));

        // Then
        assertThat(t).isNotNull();
        assertThat(t instanceof InvalidRequestException).isTrue();
    }

    @Test
    public void validateRequestWhenInvalidAmount() {
        // Given
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setUserId(3453L);
        productRequestDto.setProductId(33L);
        productRequestDto.setAmount(-4.7);

        // When
        Throwable t = catchThrowable(() -> productService.validAddRequest(productRequestDto));

        // Then
        assertThat(t).isNotNull();
        assertThat(t instanceof InvalidParameterException).isTrue();
    }

    @Test
    public void validateRequestWhenInvalidQuantity() {
        // Given
        ProductRequestDto productRequestDto = new ProductRequestDto();
        productRequestDto.setUserId(3453L);
        productRequestDto.setProductId(33L);
        productRequestDto.setAmount(60D);
        productRequestDto.setQuantity(-3D);

        // When
        Throwable t = catchThrowable(() -> productService.validAddRequest(productRequestDto));

        // Then
        assertThat(t).isNotNull();
        assertThat(t instanceof InvalidParameterException).isTrue();
    }

    @Test
    public void validateFilterAllWhenProductIdIsNull() {
        //Given
        Boolean isActive = true;
        Pageable pageable = PageRequest.of(0, 3);

        //When
        Page<UserProductModel> userProductModelActual = productService.filterAllProducts(null, isActive, pageable);

        //Then
        verify(userProductRepository, never()).findAllByProductIdAndIsActiveOrderByUpdatedAtDesc(any(), anyBoolean(), any());
        verify(userProductRepository).findAllByIsActiveOrderByUpdatedAtDesc(isActive, pageable);

        //TODO:: RESPONSE TYPE ASSERTION CANNOT BE DONE :(
    }


    @Test
    public void validateFilterAllWhenProductIdIsNotNull() {
        //Given
        Boolean isActive = true;
        Pageable pageable = PageRequest.of(0, 3);
        Long productId = 39L;

        //When
        Page<UserProductModel> userProductModelActual = productService.filterAllProducts(productId, isActive, pageable);

        //Then
        verify(userProductRepository).findAllByProductIdAndIsActiveOrderByUpdatedAtDesc(productId, isActive, pageable);
        verify(userProductRepository, never()).findAllByIsActiveOrderByUpdatedAtDesc(any(), any());

        //TODO:: RESPONSE TYPE ASSERTION CANNOT BE DONE :(
    }

    @Test
    public void validateDeleteWhenUserIdIsNotFound() {
        //Given
        Long productId = 2L;

        //When
        Throwable t = catchThrowable(() -> productService.deleteProduct(productId));

        // Then
        assertThat(t).isNotNull();
        verify(userProductRepository, never()).deleteById(any());
        verify(userProductRepository, never()).findById(any());
        verify(productService, never()).setUserProductDto(any());
        assertThat(t instanceof ProductNotFoundException).isTrue();
    }

//    @Test
//    public void validateDeleteWhenUserIdIsFound() {
//        //Given
//        ProductModel productModel = new ProductModel();
//        UserModel userModel = new UserModel();
//        Long productId = 2L;
//        UserProductModel userProductModel = new UserProductModel();
//        userProductModel.setId(productId);
//        userProductModel.setIsActive(true);
//        userProductModel.setProduct(productModel);
//        userProductModel.setUser(userModel);
//        userProductModel.setAmount(22.5);
//        userProductModel.setQuantity(55.5);
//        userProductModel.setDescription("something");
//        userProductModel.setCreatedAt(new Date());
//        userProductModel.setUpdatedAt(new Date());
//        ProductResponseDto productResponseExpectedDto = productService.setUserProductDto(userProductModel);
//
//
//        ProductResponseDto productResponseActualDto = new ProductResponseDto();
//        //When
//        doReturn(productResponseActualDto).when(productService)
//                .deleteProduct(productId);
//
//        // Then
//        assertThat(productResponseExpectedDto.getProductId()).isEqualTo(productResponseActualDto.getProductId());
//        assertThat(productResponseExpectedDto.getId()).isEqualTo(productResponseActualDto.getId());
//        assertThat(productResponseExpectedDto.getAmount()).isEqualTo(productResponseActualDto.getAmount());
//        assertThat(productResponseExpectedDto.getCompanyName()).isEqualTo(productResponseActualDto.getCompanyName());
//        assertThat(productResponseExpectedDto.getDescription()).isEqualTo(productResponseActualDto.getDescription());
//        assertThat(productResponseExpectedDto.getIsActive()).isEqualTo(productResponseActualDto.getIsActive());
//        assertThat(productResponseExpectedDto.getQuantity()).isEqualTo(productResponseActualDto.getQuantity());
//        assertThat(productResponseExpectedDto.getUserId()).isEqualTo(productResponseActualDto.getUserId());
//        assertThat(productResponseExpectedDto.getPageCount()).isEqualTo(productResponseActualDto.getPageCount());
//        assertThat(productResponseExpectedDto.getTotalElement()).isEqualTo(productResponseActualDto.getTotalElement());
//    }
}
