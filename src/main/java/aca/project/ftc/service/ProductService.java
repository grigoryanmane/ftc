package aca.project.ftc.service;


import aca.project.ftc.exception.*;
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
            if (productRequestDto.getAmount() != null ) {
                if(productRequestDto.getAmount() <=0){
                    throw new InvalidParameterException("AMOUNT_SHOULD_BE_ABOVE_ZERO");
                }
                userProductModel.setAmount(productRequestDto.getAmount());
            }
            if (productRequestDto.getQuantity() != null) {
                if(productRequestDto.getQuantity() <=0){
                    throw new InvalidParameterException("QUANTITY_SHOULD_BE_ABOVE_ZERO");
                }
                userProductModel.setQuantity(productRequestDto.getQuantity());
            }
            if (!productRequestDto.getDescription().equals("")) {
                userProductModel.setDescription(productRequestDto.getDescription());
            }
            userProductRepository.save(userProductModel);
            return getUserProductList(userProductModel.getUser().getId(), page, size, productId, isActive);
        }
        throw new UserNotFoundException("USER_NOT_FOUND");
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
        throw new ProductNotFoundException("PRODUCT_NOT_FOUND");
    }


    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        try{
            validAddRequest(productRequestDto);
            UserProductModel userProductModel = new UserProductModel();
            userProductModel.setProduct(productRepository.findById(productRequestDto.getProductId()).get());
            userProductModel.setUser(userRepository.findById(productRequestDto.getUserId()).get());
            userProductModel.setAmount(productRequestDto.getAmount());
            userProductModel.setQuantity(productRequestDto.getQuantity());
            userProductModel.setDescription(productRequestDto.getDescription());
            return setUserProductDto(userProductRepository.save(userProductModel));
        }catch (Exception e){
            throw  new CustomException("UNEXPECTED_EXCEPTION: " .concat( e.getMessage()), e.getCause());
        }
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


    public void validAddRequest(ProductRequestDto productRequestDto) {
        if (productRequestDto.getUserId() == null ) {
            throw new InvalidRequestException("USER_ID_CANNOT_BE_NULL");
        }
        if (productRequestDto.getProductId() == null) {
            throw new InvalidRequestException("PRODUCT_ID_CANNOT_BE_NULL");
        }
        if (productRequestDto.getAmount() == null) {
            throw new InvalidRequestException("AMOUNT_CANNOT_BE_NULL");
        }
        if (productRequestDto.getAmount() <= 0) {
            throw new InvalidRequestException("AMOUNT_SHOULD_BE_ABOVE_ZERO");
        }
        if (productRequestDto.getQuantity() == null) {
            throw new InvalidRequestException("QUANTITY_CANNOT_BE_NULL");
        }
        if (productRequestDto.getQuantity() <= 0) {
            throw new InvalidRequestException("QUANTITY_SHOULD_BE_ABOVE_ZERO");
        }
        if (productRequestDto.getDescription().equals("")) {
            throw new InvalidRequestException("DESCRIPTION_CANNOT_BE_NULL");
        }
        if (!(productRepository.existsById(productRequestDto.getProductId()))) {
            throw new ProductNotFoundException("INVALID_PRODUCT_ID_PRODUCT_NOT_FOUND");
        }
        if (!(userRepository.existsById(productRequestDto.getUserId()))) {
            throw new UserNotFoundException("INVALID_USER_ID_USER_NOT_FOUND");
        }
    }

}
