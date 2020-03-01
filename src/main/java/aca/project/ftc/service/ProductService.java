package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductListResponseDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.model.entity.UserProductModel;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.event.InternalFrameEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public ProductListResponseDto editProduct(ProductRequestDto productRequestDto, Long id, Integer page, Integer size, Long productId, Boolean isActive) {

        if (userProductRepository.existsById(id)) {
            UserProductModel userProductModel = userProductRepository.findById(id).get();
            if (productRequestDto.getAmount() != null) {
                userProductModel.setAmount(productRequestDto.getAmount());
            }
            if (productRequestDto.getQuantity() != null) {
                userProductModel.setQuantity(productRequestDto.getQuantity());
            }
            if (!productRequestDto.getDescription().equals("")) {
                userProductModel.setDescription(productRequestDto.getDescription());
            }
            userProductRepository.save(userProductModel);
            return getUserProductList(userProductModel.getUser().getId(), page, size, productId, isActive);
        }
        //TODO:: CHANGE THE EXCEPTION
        throw new UserNotFound("USER_NOT_FOUND");
    }

    public ProductListResponseDto getUserProductList(Long id, Integer page, Integer size, Long productId, Boolean isActive) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        ProductListResponseDto productListResponseDto = new ProductListResponseDto();
        Pageable pageable = PageRequest.of(page, size);
        Page<UserProductModel> userProductModel = filterUserProducts(id, productId, isActive, pageable);
        List<UserProductModel> userProductModelList = userProductModel.toList();
        if (!userProductModel.isEmpty()) {
            productListResponseDto.setProductResponseDtoList(getTheList(userProductModelList));
        } else {
            productListResponseDto.setProductResponseDtoList(Collections.emptyList());
        }
        productListResponseDto.setPageCount(userProductModel.getTotalPages());
        productListResponseDto.setTotalElements(userProductModel.getTotalElements());
        return productListResponseDto;
    }

    public ProductListResponseDto getAllProducts(Integer page, Integer size, Long productId) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

        ProductListResponseDto productListResponseDto = new ProductListResponseDto();
        Pageable pageable = PageRequest.of(page, size);
        Page<UserProductModel> userProductModel = filterAllProducts(productId, true, pageable);
        productListResponseDto.setTotalElements(userProductModel.getTotalElements());
        productListResponseDto.setPageCount(userProductModel.getTotalPages());
        List<UserProductModel> userProductModelList = userProductModel.toList();
        if (!userProductModel.isEmpty()) {
            productListResponseDto.setProductResponseDtoList(getTheList(userProductModelList));
        } else {
            productListResponseDto.setProductResponseDtoList(Collections.emptyList());
        }
        return productListResponseDto;
    }

    public List<ProductResponseDto> getTheList(List<UserProductModel> userProductModel) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        int i = 0;
        while (i < userProductModel.size()) {
            ProductResponseDto productResponseDto = setUserProductDto(userProductModel.get(i));
            productResponseDtoList.add(productResponseDto);
            i++;
        }
        return productResponseDtoList;
    }

    public ProductResponseDto deleteProduct(Long id) {

        if (userProductRepository.existsById(id)) {
            UserProductModel userProductModel = userProductRepository.findById(id).get();
            ProductResponseDto productResponseDto = setUserProductDto(userProductModel);
            userProductRepository.deleteById(id);
            return productResponseDto;
        }
        throw new UserNotFound("PRODUCT_NOT_FOUND");
    }

    public ProductResponseDto setUserProductDto(UserProductModel userProductModel) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(userProductModel.getId());
        productResponseDto.setUserId(userProductModel.getUser().getId());
        productResponseDto.setProductId(userProductModel.getProduct().getId());
        productResponseDto.setProductName(userProductModel.getProduct().getName());
        productResponseDto.setCompanyName(userProductModel.getUser().getCompanyName());
        productResponseDto.setAmount(userProductModel.getAmount());
        productResponseDto.setQuantity(userProductModel.getQuantity());
        productResponseDto.setDescription(userProductModel.getDescription());
        productResponseDto.setIsActive(userProductModel.getIsActive());
        return productResponseDto;
    }

    //TODO::MAKE NECESSARY CHECKS AND THROW EXCEPTION
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        UserProductModel userProductModel = new UserProductModel();
        if (!(userRepository.existsById(productRequestDto.getUserId())) && !(productRepository.existsById(productRequestDto.getProductId()))) {
            //TODO:: CHANGE THE EXCEPTION
            throw new UserNotFound("INVALID USER ID OR PRODUCT ID");
        }
        userProductModel.setProduct(productRepository.findById(productRequestDto.getProductId()).get());
        userProductModel.setUser(userRepository.findById(productRequestDto.getUserId()).get());
        userProductModel.setAmount(productRequestDto.getAmount());
        userProductModel.setQuantity(productRequestDto.getQuantity());
        userProductModel.setDescription(productRequestDto.getDescription());
        userProductRepository.save(userProductModel);
        return setUserProductDto(userProductModel);
    }

    public Page<UserProductModel> filterUserProducts(Long userId, Long productId, Boolean isActive, Pageable pageable) {
        if (productId != null && isActive != null) {
            return userProductRepository.findAllByUserIdAndProductIdAndIsActiveOrderByUpdatedAtDesc(userId, productId, isActive, pageable);
        }
        if (productId == null && isActive != null) {
            return userProductRepository.findAllByUserIdAndIsActiveOrderByUpdatedAtDesc(userId, isActive, pageable);
        }
        if (productId != null) {
            return userProductRepository.findAllByUserIdAndProductIdOrderByUpdatedAtDesc(userId, productId, pageable);
        }
        return userProductRepository.findByUserIdOrderByUpdatedAtDesc(userId, pageable);

    }

    public Page<UserProductModel> filterAllProducts(Long productId, Boolean isActive, Pageable pageable) {
        if (productId != null) {
            return userProductRepository.findAllByProductIdAndIsActiveOrderByUpdatedAtDesc(productId, isActive, pageable);
        }
        return userProductRepository.findAllByIsActiveOrderByUpdatedAtDesc(isActive, pageable);

    }


    //TODO:: ASK NANE ABOUT FILTERING
//    public List<ProductResponseDto> filter(ProductFilterRequestDto productFilterRequestDto) {
//        UserProductModel userProductModel;
//        if (productFilterRequestDto.getIsActive() != null) {
//            Optional<UserProductModel> activeFilter = userProductRepository.findAllByIsActive(productFilterRequestDto.getIsActive());
//        }
//    }
}
