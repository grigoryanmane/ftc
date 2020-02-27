package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.dto.request.product.ProductAddDto;
import aca.project.ftc.model.dto.request.product.ProductFilterRequestDto;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.entity.UserProductModel;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    public ProductResponseDto editProduct(ProductRequestDto productRequestDto, Long id) {

        if (userProductRepository.existsById(id)) {
            UserProductModel userProductModel = userProductRepository.findById(id).get();
            if (productRequestDto.getAmount() != null) {
                userProductModel.setAmount(productRequestDto.getAmount());
            }
            if (productRequestDto.getQuantity() != null) {
                userProductModel.setQuantity(productRequestDto.getQuantity());
            }
            if (productRequestDto.getDescription() != null) {
                userProductModel.setDescription(productRequestDto.getDescription());
            }
            userProductRepository.save(userProductModel);
            return setUserProductDto(userProductModel);
        }
        //TODO:: CHANGE THE EXCEPTION
        throw new UserNotFound("USER_NOT_FOUND");
    }

    public List<ProductResponseDto> getUserProductList(Long id) {
        List<UserProductModel> userProductModel = userProductRepository.findByUserId(id);
        if (!userProductModel.isEmpty()) {
            return getTheList(userProductModel);
        } else {
            return Collections.emptyList();
        }
    }

    public List<ProductResponseDto> getAllProducts() {
        List<UserProductModel> userProductModel = (List<UserProductModel>) userProductRepository.findAll();
        if (!userProductModel.isEmpty()) {
            return getTheList(userProductModel);
        }
        return Collections.emptyList();
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


    //TODO:: ASK NANE ABOUT FILTERING
//    public List<ProductResponseDto> filter(ProductFilterRequestDto productFilterRequestDto) {
//        UserProductModel userProductModel;
//        if (productFilterRequestDto.getIsActive() != null) {
//            Optional<UserProductModel> activeFilter = userProductRepository.findAllByIsActive(productFilterRequestDto.getIsActive());
//        }
//    }
}
